package com.github.johnlayton

import com.github.johnlayton.KibanaPlugin.Util.open
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.extensions.cUrlString
import com.github.kittinunf.fuel.jackson.responseObject
import org.gradle.api.*
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.container
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register
import org.gradle.tooling.BuildException
import java.io.File
import java.net.URL
import javax.inject.Inject

data class SavedObjectMeta(
    val searchSourceJSON: String = "{}"
)

data class DashboardAttributes(
    val title: String,
    val description: String,
    val hits: Long = 0,
    val version: Long = 0,
    val timeRestore: Boolean = true,
    val panelsJSON: String = "[]",
    val optionsJSON: String = "{}",
    val kibanaSavedObjectMeta: SavedObjectMeta = SavedObjectMeta()
)

data class CreateDashboard(
    val attributes: DashboardAttributes
)

data class SavedDashboard(
    val id: String,
    val type: String,
    val version: Long,
    val attributes: DashboardAttributes
)

data class SavedDashboards(
    val page: Long,
    @field:JsonProperty("per_page")
    val count: Long,
    val total: Long,
    @field:JsonProperty("saved_objects")
    val dashboards: List<SavedDashboard>
)

class KibanaPlugin : Plugin<Project> {

  companion object {
    const val EXTENSION_NAME = "kibana"
    const val DEVELOPMENT_URL = "..."
    const val PRODUCTION_URL = "..."
  }

  open class KibanaDashboard
  @Inject constructor(objects: ObjectFactory, private val name: String) : Named {

    override fun getName(): String {
      return name
    }

    val title = objects.property<String>()
    val file = objects.property<File>()
  }

  open class KibanaConfiguration
  @Inject constructor(objects: ObjectFactory) {

    val url = objects.property<URL>().convention(URL(DEVELOPMENT_URL))
  }

  open class KibanaExtension(
      val configuration: KibanaConfiguration,
      val dashboards: NamedDomainObjectContainer<KibanaDashboard>
  )

  open class ListDashboardsTask : DefaultTask() {

    init {
      group = EXTENSION_NAME
      description = "List current dashboards"
    }

    @Input
    val url = project.objects.property<URL>().convention(URL(DEVELOPMENT_URL))

    private val mapper = ObjectMapper().apply {
      registerModule(KotlinModule(nullisSameAsDefault = true))
      configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
      setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    @TaskAction
    fun action() {
      project.run {
        val (request, response, result) = Fuel.get(
            "${url.get()}/_plugin/kibana/api/saved_objects/_find?type=dashboard")
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .also {
              logger.debug("===============================================================")
              logger.debug("Kibana Url: ${it.cUrlString()}")
              logger.debug("===============================================================")
            }
            .responseObject<SavedDashboards>(mapper)

        val dashboards = result.fold(
            { success -> success },
            { failure ->
              logger.lifecycle("Get dashboards failed")
              logger.lifecycle(failure.message)
              logger.lifecycle(failure.exception.message)
              throw BuildException(failure.message, failure.exception)
            }
        )

        logger.lifecycle("===============================================================")
        logger.lifecycle("Dashboards:")
        dashboards.dashboards.forEach {
          logger.lifecycle("\t${it.id} - ${it.attributes.title}")
        }
        logger.lifecycle("===============================================================")
      }
    }
  }

  open class OpenDashboardTask : DefaultTask() {
    init {
      group = EXTENSION_NAME
      description = "Create dashboard"
    }

    @Input
    val url = project.objects.property<URL>().convention(URL(DEVELOPMENT_URL))

    @Input
    val id = project.objects.property<String>()

    @Input
    val title = project.objects.property<String>()

    @TaskAction
    fun action() {
      project.run {
        logger.lifecycle("================================")
        logger.lifecycle("Open dashboard: ${title.get()}")
        logger.lifecycle("https://kibana.npe.integration.cld/_plugin/kibana/app/kibana#/dashboard/${id.get()}")
        logger.lifecycle("================================")
        ProcessBuilder(open, "https://kibana.npe.integration.cld/_plugin/kibana/app/kibana#/dashboard/${id.get()}").start()
      }
    }
  }

  open class CreateDashboardTask : DefaultTask() {

    init {
      group = EXTENSION_NAME
      description = "Create dashboard"
    }

    @Input
    val url = project.objects.property<URL>().convention(URL(DEVELOPMENT_URL))

    @Input
    val id = project.objects.property<String>()

    @Input
    val title = project.objects.property<String>()

    private val mapper = ObjectMapper().apply {
      registerModule(KotlinModule(nullisSameAsDefault = true))
      configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
      setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    @TaskAction
    fun action() {
      project.run {
        logger.info("===============================================================")
        logger.info("Create Dashboard: ${title.get()}")
        logger.info(mapper.writeValueAsString(CreateDashboard(
            attributes = DashboardAttributes(
                title = title.get(),
                description = "${title.get()} created by plugin"
            )
        )))
        logger.info("===============================================================")

        val (request, response, result) = Fuel.post(
            "${url.get()}/_plugin/kibana/api/saved_objects/dashboard/${id.get()}")
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("kbn-xsrf", "kibana")
            .body(mapper.writeValueAsString(CreateDashboard(
                attributes = DashboardAttributes(
                    title = title.get(),
                    description = "${title.get()} created by plugin"
                )
            )))
            .also {
              logger.debug("===============================================================")
              logger.debug("Kibana Url: ${it.cUrlString()}")
              logger.debug("===============================================================")
            }
            .responseObject<SavedDashboard>(mapper)

        val dashboard = result.fold(
            { success -> success },
            { failure ->
              logger.lifecycle("Create dashboards failed")
              throw BuildException(failure.response.body().asString(null), failure.exception)
            }
        )

        logger.lifecycle("===============================================================")
        logger.lifecycle("Dashboard Created")
        logger.lifecycle("\tid: ${dashboard.id}")
        logger.lifecycle("\ttitle: ${dashboard.attributes.title}")
        logger.lifecycle("\tdescription: ${dashboard.attributes.title}")
        logger.lifecycle("===============================================================")
      }
    }
  }

  open class SaveDashboardTask : DefaultTask() {

    init {
      group = EXTENSION_NAME
      description = "Save dashboard"
    }

    @Input
    val url = project.objects.property<URL>().convention(URL(DEVELOPMENT_URL))

    @Input
    val id = project.objects.property<String>()

    @Input
    val title = project.objects.property<String>()

    @Input
    val file = project.objects.property<File>()

    private val mapper = ObjectMapper().apply {
      registerModule(KotlinModule(nullisSameAsDefault = true))
      configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
      setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    @TaskAction
    fun action() {
      project.run {
        logger.info("===============================================================")
        logger.info("Save Dashboard: ${title.get()}")
        logger.info("===============================================================")

        val (request, response, result) = Fuel.get(
            "${url.get()}/_plugin/kibana/api/saved_objects/dashboard/${id.get()}")
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("kbn-xsrf", "kibana")
            .also {
              logger.debug("===============================================================")
              logger.debug("Kibana Url: ${it.cUrlString()}")
              logger.debug("===============================================================")
            }
            .responseString()

        val dashboard = result.fold(
            { success -> success },
            { failure ->
              logger.lifecycle("Create dashboards failed")
              throw BuildException(failure.response.body().asString(null), failure.exception)
            }
        )

        if (file.get().createNewFile()) {
          file.get().writeText(dashboard)
        }

        logger.lifecycle("===============================================================")
        logger.lifecycle("Dashboard Saved")
        logger.lifecycle(dashboard)
        logger.lifecycle("===============================================================")
      }
    }
  }

  open class LoadDashboardTask : DefaultTask() {

    init {
      group = EXTENSION_NAME
      description = "Save dashboard"
    }

    @Input
    val url = project.objects.property<URL>().convention(URL(DEVELOPMENT_URL))

    @Input
    val id = project.objects.property<String>()

    @Input
    val title = project.objects.property<String>()

    @Input
    val file = project.objects.property<File>()

    private val mapper = ObjectMapper().apply {
      registerModule(KotlinModule(nullisSameAsDefault = true))
      configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
      configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
      setSerializationInclusion(JsonInclude.Include.NON_NULL)
    }

    @TaskAction
    fun action() {
      project.run {
        logger.info("===============================================================")
        logger.info("Load Dashboard: ${title.get()}")
        logger.info("===============================================================")

        val (request, response, result) = Fuel.put(
            "${url.get()}/_plugin/kibana/api/saved_objects/dashboard/${id.get()}")
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("kbn-xsrf", "kibana")
            .body(file.get().readText())
            .also {
              logger.debug("===============================================================")
              logger.debug("Kibana Url: ${it.cUrlString()}")
              logger.debug("===============================================================")
            }
            .responseObject<SavedDashboard>(mapper)

        val dashboard = result.fold(
            { success -> success },
            { failure ->
              logger.lifecycle("Load dashboards failed")
              throw BuildException(failure.response.body().asString(null), failure.exception)
            }
        )

        logger.lifecycle("===============================================================")
        logger.lifecycle("Dashboard Loaded")
        logger.lifecycle("\tid: ${dashboard.id}")
        logger.lifecycle("\ttitle: ${dashboard.attributes.title}")
        logger.lifecycle("\tdescription: ${dashboard.attributes.title}")
        logger.lifecycle("===============================================================")
      }
    }
  }

  override fun apply(project: Project): Unit = project.run {

    val kibanaExtension =
        extensions.create(EXTENSION_NAME, KibanaExtension::class,
            extensions.create("configuration", KibanaConfiguration::class, project.objects),
            container(KibanaDashboard::class))

    tasks.register<ListDashboardsTask>("${EXTENSION_NAME}ListDashboards") {
      url.set(kibanaExtension.configuration.url)
    }

    kibanaExtension.dashboards.all(object : Action<KibanaDashboard> {
      override fun execute(dashboard: KibanaDashboard) {
        val name = "${EXTENSION_NAME}Dashboard${dashboard.name.capitalize()}"
        tasks.register<CreateDashboardTask>("${name}Create") {
          url.set(kibanaExtension.configuration.url)
          id.set(dashboard.name)
          title.set(dashboard.title)
        }
        tasks.register<SaveDashboardTask>("${name}Save") {
          url.set(kibanaExtension.configuration.url)
          id.set(dashboard.name)
          title.set(dashboard.title)
          file.set(dashboard.file)
        }
        tasks.register<LoadDashboardTask>("${name}Load") {
          url.set(kibanaExtension.configuration.url)
          id.set(dashboard.name)
          title.set(dashboard.title)
          file.set(dashboard.file)
        }
        tasks.register<OpenDashboardTask>("${name}Open") {
          url.set(kibanaExtension.configuration.url)
          id.set(dashboard.name)
          title.set(dashboard.title)
        }
      }
    })
  }

  object Util {
    val open: String
      get() {
        val operSys = System.getProperty("os.name").toLowerCase()
        return if (operSys.contains("win")) {
          "start"
        } else if (operSys.contains("mac")) {
          "open"
        } else if (operSys.contains("nix") ||
            operSys.contains("nux") ||
            operSys.contains("aix")) {
          ""
        } else if (operSys.contains("sunos")) {
          ""
        } else {
          ""
        }
      }
  }
}

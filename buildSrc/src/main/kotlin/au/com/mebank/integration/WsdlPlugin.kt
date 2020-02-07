package au.com.mebank.integration

import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.Named
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.file.FileCollection
import org.gradle.api.logging.LogLevel
import org.gradle.api.logging.Logger
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.Delete
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.container
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.listProperty
import org.gradle.kotlin.dsl.mapProperty
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named
import org.gradle.kotlin.dsl.property
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import java.io.File
import java.nio.charset.Charset
import java.util.UUID
import javax.inject.Inject


class WsdlPlugin : Plugin<Project> {

  companion object {
    const val EXTENSION_NAME = "wsdlToJava"

    private const val WSDL_LOCATION_PLACEHOLDER = "@WSDL_LOCATION@"
    private const val WRAPPER_STYLE_PLACEHOLDER = "@ENABLE_WRAPPER_STYLE@"
    private const val ASYNC_MAPPING_PLACEHOLDER = "@ENABLE_ASYNC_MAPPING@"
  }

//  open class WsdlPackage
//  @Inject
//  constructor(objects: ObjectFactory) {
//
//    val ns = objects.property<String>().convention("")
//    val pkg = objects.property<String>().convention("")
//  }

  open class WsdlExtension
  @Inject
  constructor(objects: ObjectFactory, private val name: String) : Named {

    override fun getName(): String {
      return name
    }

    val wsdl = objects.property<File>()
    val bind = objects.property<File>()

    val pkgs = objects.mapProperty<String, String>().empty()//<WsdlPackage>().convention(emptyList())

    val enableWrapperStyle = objects.property<Boolean>().convention(false)
    val enableAsyncMapping = objects.property<Boolean>().convention(true)

    val enableExsh = objects.property<Boolean>().convention(true)
    val enableDex = objects.property<Boolean>().convention(false)
    val enableDns = objects.property<Boolean>().convention(false)

    val validate = objects.property<Boolean>().convention(false)
  }

  open class WsdlToJavaExtension(val wsdls: NamedDomainObjectContainer<WsdlExtension>) {

    var apacheCXFVersion = "3.2.12"
    var javaxXmlVersion = "2.3.1"
    var javaxActivationVersion = "1.1.1"
    var javaxJwsVersion = "1.0-MR1"
    var sunXmlVersion = "2.3.0.1"

  }

  open class TemplateTask : DefaultTask() {

    init {
      group = "WsdlToJava"
      description = "Generate the bindings template file"
    }

    @OutputFile
    val template = project.objects.property<File>()
        .convention(project.file("${project.buildDir}/tmp/${EXTENSION_NAME}/binding.xml"))

    @TaskAction
    fun action() {
      project.run {
        template.get().writeText(project.buildscript.classLoader.getResource("binding.xml")
            .readText(Charset.defaultCharset())
        )
        logger.info("================================")
        logger.info("Created template ${template.get().absolutePath}")
        logger.info("================================")
        logger.info(template.get().readText(Charset.defaultCharset()))
        logger.info("================================")
      }
    }
  }

  open class BindingsTask : DefaultTask() {

    init {
      group = "WsdlToJava"
      description = "Generate the bindings file"
    }

    @OutputFile
    val bindings = project.objects.property<File>()
        .convention(project.file("${project.buildDir}/tmp/${EXTENSION_NAME}/${UUID.randomUUID().toString()}.xml"))

    @InputFile
    val template = project.objects.property<File>()
        .convention(project.file("${project.buildDir}/tmp/${EXTENSION_NAME}/binding.xml"))

    @InputFile
    val wsdl = project.objects.property<File>()

    @Input
    val enableWrapperStyle = project.objects.property<Boolean>()
        .convention(false)

    @Input
    val enableAsyncMapping = project.objects.property<Boolean>()
        .convention(true)

    @TaskAction
    fun action() {

      project.run {
        bindings.get()
            .writeText(template.get().readText(Charset.defaultCharset())
                .replace(WSDL_LOCATION_PLACEHOLDER, wsdl.get().absolutePath)
                .replace(WRAPPER_STYLE_PLACEHOLDER, enableWrapperStyle.get().toString())
                .replace(ASYNC_MAPPING_PLACEHOLDER, enableAsyncMapping.get().toString())
            )
        logger.info("================================")
        logger.info("Created bindings to ${bindings.get().absolutePath}")
        logger.info("================================")
        logger.info(bindings.get().readText(Charset.defaultCharset()))
        logger.info("================================")
      }

    }
  }

  open class GenerateTask : DefaultTask() {

    init {
      group = "WsdlToJava"
      description = "Generate the Java source"
    }

    @OutputDirectory
    val dest = project.objects.property<File>()

    @InputFile
    val bind = project.objects.property<File>()

    @InputFile
    val wsdl = project.objects.property<File>()

    @Input
    val clspath = project.objects.property<FileCollection>()

    @Input
    val pkgs = project.objects.mapProperty<String, String>().convention(emptyMap())

    @Input
    val exsh = project.objects.property<Boolean>().convention(true)

    @Input
    val dex = project.objects.property<Boolean>().convention(false)

    @Input
    val dns = project.objects.property<Boolean>().convention(false)

    @Input
    val validate = project.objects.property<Boolean>().convention(false)

    @TaskAction
    fun action() {
      project.logger.info("================================================")
      project.logger.info(" WsdlPlugin : Generate Java from Wsdl")
      project.logger.info(" WsdlPlugin : Dest : ${dest.get()}")
      project.logger.info(" WsdlPlugin : Bind : ${bind.get()}")
      project.logger.info(" WsdlPlugin : Wsdl : ${wsdl.get()}")
      project.logger.info(" WsdlPlugin : Exsh : ${exsh.get()}")
      project.logger.info(" WsdlPlugin : Dex : ${dex.get()}")
      project.logger.info(" WsdlPlugin : Dns : ${dns.get()}")
      project.logger.info(" WsdlPlugin : Validate : ${validate.get()}")

      project.logging.captureStandardOutput(LogLevel.INFO)
      project.logging.captureStandardError(LogLevel.ERROR)

      try {

        val arguments = listOf<String>(
            "-d", dest.get().absolutePath,
            "-b", bind.get().absolutePath,
/*
            "-p", "au.com.mebank.integration",
*/
            "-exsh", exsh.get().toString(),
            "-dns", dns.get().toString(),
            "-dex", dex.get().toString(),
//            "-client", "-server",
//            "-suppress-generated-date",
            "-autoNameResolution",
            "-mark-generated"
        ) + packages() + validate() + verbose(project.logger) + listOf(wsdl.get().absolutePath)
        project.logger.info(" WsdlPlugin : Cmd : wsdl2java ${arguments.joinToString(" ")}")

        logger.info("===============================================")
        logger.info("==  Classpath files;")
        clspath.get().files.forEach {
          logger.info("==\t${it.absolutePath}")
        }
        logger.info("===============================================")

        project.javaexec {
          main = "org.apache.cxf.tools.wsdlto.WSDLToJava"
          args = arguments
          classpath = clspath.get()
        }
/*
        WSDLToJava(args).run(ToolContext(), System.out)
*/
      } catch (exception: Exception) {
        project.logger.error(exception.message, exception)
      }

      project.logger.info("================================================")
    }

    private fun packages(): List<String> = pkgs.getOrElse(emptyMap()).flatMap {
      listOf("-p",  "${it.key}=${it.value}")
    }

    private fun validate(): Array<String> = if (validate.getOrElse(false)) {
      arrayOf("-validate")
    } else {
      emptyArray()
    }

    private fun verbose(logger: Logger): Array<String> = if (logger.isDebugEnabled || logger.isInfoEnabled) {
      arrayOf("-verbose")
    } else {
      emptyArray()
    }
  }

  override fun apply(project: Project): Unit = project.run {

    if (!plugins.hasPlugin("java")) {
      plugins.apply("java")
    }

    val templateTask = tasks.register<TemplateTask>("${EXTENSION_NAME}Template") {
      logger.info("===============================================")
      logger.info("==  Configure Template Bindings Task")
      logger.info("===============================================")
/*
      template.set(file("${buildDir}/tmp/${EXTENSION_NAME}"))
*/
    }

    val wsdlToJavaExtension =
        extensions.create(EXTENSION_NAME, WsdlToJavaExtension::class, container(WsdlExtension::class))

    val wsdlToJavaToolConfiguration = configurations.create(EXTENSION_NAME + "Tools", {
      defaultDependencies(object : Action<DependencySet> {
        override fun execute(dependencies: DependencySet) {
          dependencies.add(project.dependencies.create("org.apache.cxf:cxf-tools-wsdlto-core:${wsdlToJavaExtension.apacheCXFVersion}"))
          dependencies.add(project.dependencies.create("org.apache.cxf:cxf-tools-wsdlto-frontend-jaxws:${wsdlToJavaExtension.apacheCXFVersion}"))
          dependencies.add(project.dependencies.create("org.apache.cxf:cxf-tools-wsdlto-databinding-jaxb:${wsdlToJavaExtension.apacheCXFVersion}"))
          dependencies.add(project.dependencies.create("javax.xml.bind:jaxb-api:${wsdlToJavaExtension.javaxXmlVersion}"))
          dependencies.add(project.dependencies.create("javax.xml.ws:jaxws-api:${wsdlToJavaExtension.javaxXmlVersion}"))
          dependencies.add(project.dependencies.create("javax.jws:jsr181-api:${wsdlToJavaExtension.javaxJwsVersion}"))
        }
      })
    })

    val wsdlToJavaConfiguration = configurations.create(EXTENSION_NAME + "Implementation", {
      defaultDependencies(object : Action<DependencySet> {
        override fun execute(dependencySet: DependencySet) {
          dependencySet.add(project.dependencies.create("org.apache.cxf:cxf-spring-boot-starter-jaxws:${wsdlToJavaExtension.apacheCXFVersion}"))
          dependencySet.add(project.dependencies.create("org.apache.cxf:cxf-rt-frontend-jaxws:${wsdlToJavaExtension.apacheCXFVersion}"))
          dependencySet.add(project.dependencies.create("org.apache.cxf:cxf-rt-transports-http:${wsdlToJavaExtension.apacheCXFVersion}"))
          dependencySet.add(project.dependencies.create("org.apache.cxf:cxf-rt-features-logging:${wsdlToJavaExtension.apacheCXFVersion}"))
          dependencySet.add(project.dependencies.create("javax.xml.bind:jaxb-api:${wsdlToJavaExtension.javaxXmlVersion}"))
          dependencySet.add(project.dependencies.create("javax.xml.ws:jaxws-api:${wsdlToJavaExtension.javaxXmlVersion}"))
          dependencySet.add(project.dependencies.create("com.sun.xml.bind:jaxb-core:${wsdlToJavaExtension.sunXmlVersion}"))
          dependencySet.add(project.dependencies.create("com.sun.xml.bind:jaxb-impl:${wsdlToJavaExtension.sunXmlVersion}"))
          dependencySet.add(project.dependencies.create("javax.activation:activation:${wsdlToJavaExtension.javaxActivationVersion}"))
          dependencySet.add(project.dependencies.create("javax.jws:jsr181-api:${wsdlToJavaExtension.javaxJwsVersion}"))
        }
      })
    })

/*
    val test = sourceSets.getByName("test")
*/

    val sourceSets = the<SourceSetContainer>()
    val main = sourceSets.getByName("main")

    configurations.named(main.apiConfigurationName) {
      extendsFrom(wsdlToJavaConfiguration)
    }

    wsdlToJavaExtension.wsdls.all(object : Action<WsdlExtension> {
      override fun execute(wsdlExtension: WsdlExtension) {

        val taskName = wsdlExtension.name.capitalize()

        main.java.srcDirs(file("${projectDir}/src/${wsdlExtension.name}/java"))

//        logger.lifecycle("Add ${file("${projectDir}/src/${wsdlExtension.name}/java")} to main SourceSet")
//        logger.lifecycle(" ... ${main.java.srcDirs}")

//        main.compileClasspath += wsdlToJavaConfiguration.dependencies
//        main.runtimeClasspath += wsdlToJavaConfiguration.dependencies

/*
        val wsdlSourceSet = sourceSets.create(wsdlExtension.name) {
          compileClasspath += wsdlToJavaConfiguration
          runtimeClasspath += wsdlToJavaConfiguration
        }

        main.compileClasspath += wsdlSourceSet.output + wsdlToJavaConfiguration
        main.runtimeClasspath += wsdlSourceSet.output + wsdlToJavaConfiguration

        test.compileClasspath += wsdlSourceSet.output + wsdlToJavaConfiguration
        test.runtimeClasspath += wsdlSourceSet.output + wsdlToJavaConfiguration
*/

        val bindTask = tasks.register<BindingsTask>("${EXTENSION_NAME}Bind${taskName}") {

          description = "Generate the ${taskName} binding file"

          logger.info("===============================================")
          logger.info("==  Configure ${taskName} bindings file")
          logger.info("===============================================")

          template.set(wsdlExtension.bind.orElse(templateTask.get().template))
          bindings.set(file("${buildDir}/tmp/${EXTENSION_NAME}/${wsdlExtension.name}.xml"))
          wsdl.set(wsdlExtension.wsdl)
          enableAsyncMapping.set(wsdlExtension.enableAsyncMapping)
          enableWrapperStyle.set(wsdlExtension.enableWrapperStyle)
        }

        val generateTask = tasks.register<GenerateTask>("${EXTENSION_NAME}Generate${taskName}") {

          description = "Generate the ${taskName} Java source"

          logger.info("===============================================")
          logger.info("==  Configure ${taskName} Generate Task")
          logger.info("===============================================")

          dependsOn(bindTask)

          dest.set(file("${projectDir}/src/${wsdlExtension.name}/java"))
          clspath.set(wsdlToJavaToolConfiguration)
          pkgs.set(wsdlExtension.pkgs)
          exsh.set(wsdlExtension.enableExsh)
          dex.set(wsdlExtension.enableDex)
          dns.set(wsdlExtension.enableDns)
          validate.set(wsdlExtension.validate)
          wsdl.set(wsdlExtension.wsdl)
          bind.set(bindTask.get().bindings)
        }

        tasks.named<Delete>("clean") {
          delete(file("${projectDir}/src/${wsdlExtension.name}/java"))
        }

        tasks.named("compileJava") {
          dependsOn(generateTask)
        }


/*
        val compileJava = tasks.named("compile${taskName}Java") {
          dependsOn(generateTask)
        }

        val compileKotlin = tasks.named("compile${taskName}Kotlin") {
          dependsOn(generateTask)
        }
*/

/*
        tasks.named<Jar>("jar") {
          dependsOn(compileJava, compileKotlin)
          from(wsdlSourceSet.output)
        }
*/
      }
    })
  }
}

internal fun Project.wsdlToJava(): NamedDomainObjectContainer<WsdlPlugin.WsdlExtension> =
    @Suppress("UNCHECKED_CAST")
    extensions.getByName(WsdlPlugin.EXTENSION_NAME) as? NamedDomainObjectContainer<WsdlPlugin.WsdlExtension>
        ?: throw IllegalStateException("${WsdlPlugin.EXTENSION_NAME} is not of the correct type")


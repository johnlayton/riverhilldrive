package com.github.johnlayton

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.extensions.cUrlString
import com.github.kittinunf.fuel.jackson.responseObject
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.wrapper.Wrapper
import org.gradle.tooling.BuildException
import org.gradle.util.GradleVersion

class GradlePlugin : Plugin<Project> {

  companion object {
    const val EXTENSION_NAME = "gradle"
  }

  data class Distribution(
      val version: String,
      val downloadUrl: String
  )

  private val mapper = ObjectMapper().apply {
    registerModule(KotlinModule(nullisSameAsDefault = true))
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
  }

  override fun apply(project: Project): Unit = project.run {

    logger.debug("=====================================")
    logger.debug("== Apply the upgrade gradle plugin ==")
    logger.debug("=====================================")

    val (_, _, result) =
        Fuel.get("https://services.gradle.org/versions/current")
            .header(Headers.CONTENT_TYPE, "application/json")
            .header(Headers.ACCEPT, "application/json")
            .also {
              logger.debug("===============================================================")
              logger.debug("Gradle Current Distribution Url: ${it.cUrlString()}")
              logger.debug("===============================================================")
            }
            .responseObject<Distribution>(mapper)

    val current = GradleVersion.current()
    result.fold({ success ->
      logger.info("=======================================")
      logger.info("== Gradle Distribution;")
      logger.info("==      Latest: ${success.version}")
      logger.info("==     Current: ${current.version}")
      logger.info("=======================================")
      tasks.create("upgradeGradle", Wrapper::class.java).apply {
        group = EXTENSION_NAME
        gradleVersion = success.version
        distributionUrl = success.downloadUrl
        outputs.upToDateWhen {
          gradleVersion == current.version
        }
      }
    }, { failure: FuelError ->
      throw BuildException(failure.message, failure.exception)
    })
  }
}
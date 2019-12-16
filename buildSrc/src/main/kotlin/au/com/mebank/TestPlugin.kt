package au.com.mebank

import org.gradle.api.Plugin
import org.gradle.api.Project
//import org.gradle.api.artifacts.Configuration
//import org.gradle.internal.impldep.org.codehaus.plexus.ContainerConfiguration
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get

class TestPlugin : Plugin<Project> {
  override fun apply(project: Project) {

    // Add Configuration
    val contractTestImplementation = project.configurations.create("contractTestImplementation") {
      extendsFrom(project.configurations["testImplementation"])
    }

    project.dependencies {
      contractTestImplementation("org.testcontainers:testcontainers:1.10.6")
      contractTestImplementation("org.testcontainers:junit-jupiter:1.10.6")
      contractTestImplementation("org.testcontainers:selenium:1.10.6")
    }

    // Add SourceSets



    // Add Task

    // Add Task Dependencies

//    if (project.pluginManager.hasPlugin("kotlin")) {
//
//    }

    project.task("itest") {
      group = "verification"
      description = "run the contract tests"
      doLast {
        println("Hello from the GreetingPlugin")
      }
    }
  }
}
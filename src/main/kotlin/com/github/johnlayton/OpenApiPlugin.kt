package com.github.johnlayton

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

class OpenApiPlugin : Plugin<Project> {

  companion object {
    const val EXTENSION_NAME = "openapi"

//        private const val WSDL_LOCATION_PLACEHOLDER = "@WSDL_LOCATION@"
//        private const val WRAPPER_STYLE_PLACEHOLDER = "@ENABLE_WRAPPER_STYLE@"
//        private const val ASYNC_MAPPING_PLACEHOLDER = "@ENABLE_ASYNC_MAPPING@"
  }


  open class TestingExtension {

//        var testContainersVersion = "1.10.6"

  }

  open class GenerateTask : DefaultTask() {

    init {
      group = "OpenApi"
      description = "Show the project version"
    }

    @TaskAction
    fun action() {
      project.run {
        logger.lifecycle("========================================================================")
        logger.lifecycle("Project Version : ${project.version}")
        logger.lifecycle("Project Snapshot : ${project.isSnapshot}")
        logger.lifecycle("========================================================================")


//        org.openapitools.codegen.DefaultGenerator().

      }
    }
  }

  override fun apply(project: Project): Unit = project.run {
    //        val testingExtension =
//                extensions.create<TestingExtension>(TestingPlugin.EXTENSION_NAME)
//
//        val testingConfiguration = configurations.create(TestingPlugin.EXTENSION_NAME + "Tools", {
//            setTransitive(true)
//            setVisible(true)
//            defaultDependencies(object : Action<DependencySet> {
//                override fun execute(dependencies: DependencySet) {
//                    dependencies.add(project.dependencies.create("org.testcontainers:testcontainers:${testingExtension.testContainersVersion}"))
//                    dependencies.add(project.dependencies.create("org.testcontainers:junit-jupiter:${testingExtension.testContainersVersion}"))
//                    dependencies.add(project.dependencies.create("org.testcontainers:selenium:${testingExtension.testContainersVersion}"))
//                }
//            })
//        })
//
////        val sourceSets = project.the(SourceSetContainer::class)
//
//
//        val sourceSets = the<SourceSetContainer>()
////        val main = sourceSets.getByName("main")
//
////        configurations.named(main.apiConfigurationName) {
////            extendsFrom(wsdlToJavaConfiguration)
////        }
//
//        sourceSets.create("integration-test") {
//            compileClasspath += sourceSets["test"].output + sourceSets["test"].compileClasspath + testingConfiguration
//            runtimeClasspath += sourceSets["test"].output + sourceSets["test"].runtimeClasspath + testingConfiguration
//        }
      tasks.register<GenerateTask>("openapiGenerate")
  }
}
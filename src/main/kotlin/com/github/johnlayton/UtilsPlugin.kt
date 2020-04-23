package com.github.johnlayton

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

class UtilsPlugin : Plugin<Project> {

  open class ShowJarContents : DefaultTask() {

    init {
      group = "Utils"
      description = "Show the contents of the packaged jar"
    }

    @TaskAction
    fun action() {
      project.exec {
        commandLine("zipinfo", "${project.buildDir}/libs/${project.name}-${project.version}.jar")
      }
    }
  }

  open class ShowConfigurations : DefaultTask() {

    init {
      group = "Utils"
      description = "Show the configurations"
    }

    @TaskAction
    fun action() {
      project.run {
        logger.lifecycle("========================================================================")
        configurations.all {
          logger.lifecycle("Configuration : ${name} : ${description}")
          allDependencies.forEach {
            logger.lifecycle("          Dep : ${it.group}:${it.name}:${it.version}")
          }
          logger.lifecycle("========================================================================")
        }
      }

//      logger.lifecycle("Found task graph: ${this}")
//      project.run {
//        project.gradle.taskGraph.whenReady {
//          logger.lifecycle("Found task graph: ${this}")
//          logger.lifecycle("Found ${allTasks.size} tasks.")
//          allTasks.forEach { task ->
//            println(task)
//            task.dependsOn.forEach { dep ->
//              logger.lifecycle("\t${dep}")
//            }
//          }
//        }
//      }
    }
  }

  override fun apply(project: Project): Unit = project.run {
    tasks.register<ShowJarContents>("showJarContents") {
      dependsOn("build")
    }
    tasks.register<ShowConfigurations>("showConfigurations") {
      dependsOn("build")
    }
  }
}


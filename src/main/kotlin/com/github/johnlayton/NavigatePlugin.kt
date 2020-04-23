package com.github.johnlayton

import com.github.johnlayton.NavigatePlugin.Util.open
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

class NavigatePlugin : Plugin<Project> {

  companion object {
    const val EXTENSION_NAME = "navigate"
  }

  open class ToBuildTask : DefaultTask() {

    init {
      group = "Navigate"
      description = "Navigate to project build"
    }

    @TaskAction
    fun action() {
      project.run {
        logger.lifecycle("================================")
        logger.lifecycle("Open build for project ${name}")
        logger.lifecycle("================================")
        ProcessBuilder(open, "https://github.com/johnlayton/${name}/actions").start()
        ProcessBuilder(open, "https://jitpack.io/#johnlayton/${name}").start()
      }
    }
  }

  open class ToRepositoryTask : DefaultTask() {

    init {
      group = "Navigate"
      description = "Navigate to project repository(ies)"
    }

    @TaskAction
    fun action() {
      project.run {
        logger.lifecycle("================================")
        logger.lifecycle("Open repository for project ${name}")
        logger.lifecycle("================================")
        ProcessBuilder(open, "https://github.com/johnlayton/${name}").start()
      }
    }
  }

  override fun apply(project: Project): Unit = project.run {

    tasks.register<ToBuildTask>("${EXTENSION_NAME}ToBuild")
    tasks.register<ToRepositoryTask>("${EXTENSION_NAME}ToRepository")

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
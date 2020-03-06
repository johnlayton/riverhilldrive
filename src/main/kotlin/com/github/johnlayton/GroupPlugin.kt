package com.github.johnlayton

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

class GroupPlugin : Plugin<Project> {

/*
  companion object {
    const val EXTENSION_NAME = "group"
  }
*/

/*
  open class GroupExtension() {
    var lastPart = ""
  }
*/

  open class ShowGroup : DefaultTask() {

    init {
      group = "Group"
      description = "Show the project group"
    }

    @TaskAction
    fun action() {
      project.run {
        logger.lifecycle("========================================================================")
        logger.lifecycle("Project Group : ${project.group}")
        logger.lifecycle("========================================================================")
      }
    }
  }

  override fun apply(project: Project): Unit = project.run {

/*
    val groupExtension = extensions.create(EXTENSION_NAME, GroupExtension::class)
*/
    project.setGroup("com.github.johnlayton" + suffix(this))
    tasks.register<ShowGroup>("showGroup")

  }

  private fun suffix(project: Project, suffix: String = ""): String = project.parent?.let { parent ->
    suffix(parent, ".${parent.name}${suffix}")
  } ?: suffix

}

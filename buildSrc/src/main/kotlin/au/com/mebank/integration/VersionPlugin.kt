package au.com.mebank.integration

import org.eclipse.jgit.api.Git
import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

class VersionPlugin : Plugin<Project> {

  open class ShowVersion : DefaultTask() {

    init {
      group = "Version"
      description = "Show the project version"
    }

    @TaskAction
    fun action() {
      project.run {
        logger.lifecycle("========================================================================")
        logger.lifecycle("Project Version : ${project.version}")
        logger.lifecycle("Project Snapshot : ${project.isSnapshot}")
        logger.lifecycle("========================================================================")
      }
    }
  }

  override fun apply(project: Project): Unit = project.run {

    version = if (project.hasProperty("VERSION")) {
      property("VERSION") as String
    } else {
      val git = Git.open(project.rootDir)
      try {
        git.describe().setAlways(true).call()
      } finally {
        git.close()
      }
    }

    tasks.register<ShowVersion>("showVersion")
  }
}

val Project.isSnapshot
  get() = version.toString().endsWith("-SNAPSHOT")


package au.com.mebank.integration

import org.eclipse.jgit.api.Git
import org.gradle.api.DefaultTask
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.register

class GroupPlugin : Plugin<Project> {

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

    project.group = "au.com.mebank.integration"
    tasks.register<ShowGroup>("showGroup")

  }
}

//internal fun Project.is(): NamedDomainObjectContainer<WsdlPlugin.WsdlExtension> =
//    @Suppress("UNCHECKED_CAST")
//    extensions.getByName(WsdlPlugin.EXTENSION_NAME) as? NamedDomainObjectContainer<WsdlPlugin.WsdlExtension>
//        ?: throw IllegalStateException("${WsdlPlugin.EXTENSION_NAME} is not of the correct type")


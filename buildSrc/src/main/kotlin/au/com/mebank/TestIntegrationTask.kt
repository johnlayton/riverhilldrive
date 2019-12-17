package au.com.mebank

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.the

open class TestIntegrationTask : DefaultTask() {

  init {
    group = "verification"
    description = "Prints a description of ${project.name}."
  }

  @TaskAction
  fun run() {

//    project.sourceSets
//    project.logger.info("===========================================")
//    project.logger.info(project.extensions.findByType(HelloExtension::class)!!.message.getOrElse("missing"))
//    project.logger.info("===========================================")

//    val sourceSets = project.the(SourceSetContainer::class)
//    sourceSets.forEach {
//      project.logger.info("${it}")
//    }
    project.logger.info("hello from a test integration task")
  }
}

//fun Project.withHelloTask() =
//    tasks.register("hello", HelloTask::class)

import org.gradle.api.*
import org.gradle.api.tasks.*
import org.gradle.kotlin.dsl.*
import au.com.mebank.HelloExtension

open class HelloTask : DefaultTask() {

  init {
    group = "My"
    description = "Prints a description of ${project.name}."
  }

  @TaskAction
  fun run() {

    project.logger.info("===========================================")
    project.logger.info(project.extensions.findByType(HelloExtension::class)!!.message.getOrElse("missing"))
    project.logger.info("===========================================")

    println("hello from a task")
  }
}

//fun Project.withHelloTask() =
//    tasks.register("hello", HelloTask::class)

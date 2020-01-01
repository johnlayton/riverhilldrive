package au.com.mebank

//import org.gradle.api.artifacts.Configuration
//import org.gradle.internal.impldep.org.codehaus.plexus.ContainerConfiguration
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaBasePlugin
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.gradle.plugins.ide.idea.IdeaPlugin
import org.gradle.plugins.ide.idea.model.IdeaModel

//getimport org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper
//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class TestPlugin : Plugin<Project> {
  override fun apply(project: Project) {

    // Add Configuration
//    project.configure(ConfigurationContainer::class) {
//
//    }

    val contractTestImplementation = project.configurations.create("contractTestImplementation")
//    {
////      extendsFrom(project.configurations["testImplementation"])
//    }

    project.dependencies {
      contractTestImplementation("org.testcontainers:testcontainers:1.10.6")
      contractTestImplementation("org.testcontainers:junit-jupiter:1.10.6")
      contractTestImplementation("org.testcontainers:selenium:1.10.6")
    }

    val sourceSets = project.the(SourceSetContainer::class)

    sourceSets.create("integration-test") {
      compileClasspath += sourceSets["test"].output + sourceSets["test"].compileClasspath + contractTestImplementation
      runtimeClasspath += sourceSets["test"].output + sourceSets["test"].runtimeClasspath + contractTestImplementation
    }

//    project.logger.error("==================")
//    project.logger.error("${sourceSets["integration-test"].allSource.srcDirTrees}")
//    project.logger.error("${sourceSets["test"].allSource.srcDirTrees}")
//    project.logger.error("${sourceSets["main"].allSource.srcDirTrees}")
//    project.logger.error("==================")

    // Add Task
    project.tasks.register("integrationTest", Test::class) {
      group = JavaBasePlugin.VERIFICATION_GROUP
      description = "Runs the integration tests"
      reports.html.destination = project.file("${project.buildDir}/reports/integration-test")
      reports.junitXml.destination = project.file("${project.buildDir}/integration-test-results")
      testClassesDirs = sourceSets["integration-test"].output.classesDirs
      classpath = sourceSets["integration-test"].runtimeClasspath
      mustRunAfter(project.tasks.named("test"))
    }

    if (project.plugins.hasPlugin(IdeaPlugin::class.java)) {

      val model = project.the(IdeaModel::class)

//      }

//      project.plugins.withType<IdeaPlugin> {

        println("===================================")
//        println("=========== here ==================")
//        println("${project.name}")

//        println(sourceSets["integration-test"].allJava)

//        println(model.module.testSourceDirs)
        sourceSets["integration-test"].allSource.srcDirs.forEach {
          println("Add === ${it}")
          model.module.testSourceDirs = model.module.testSourceDirs + it
          println("Result == " + model.module.testSourceDirs)
        }
//        println(model.module.testSourceDirs)
//        println(model.module.testResourceDirs)
        println("===================================")
//      }
    }

//          .each { srcDir ->
//        module.testSourceDirs += srcDir
//      }

//      model.module.testSourceDirs.addAll(sourceSets["integration-test"].allSource.srcDirs)
//      model.module.scopes

/*
      println("=========== here ==================")
      println(sourceSets["integration-test"].allSource.sourceDirectories.files)
      println(sourceSets["integration-test"].allSource.srcDirTrees)
*/
//      this
//    }

//    testSourceSet.allSource.srcDirs.each { srcDir ->
//      module.testSourceDirs += srcDir
//    }
//
//    module.scopes.TEST.plus += [getConfiguration(testSourceSet.compileConfigurationName), getConfiguration(testSourceSet.runtimeConfigurationName)]

//    project.plugins.withType<IdeaPlugin> {
//
//    }

//      project.idea { IdeaModel model ->
//        c(model.module)
//      }
//    }

//    project.plugins.withType(JavaPlugin) {
//      IdePluginConfigurer idePluginConfigurer = new IdeaPluginConfigurer(project)
//      idePluginConfigurer.configure(sourceSet, facet)
//
//      IdePluginConfigurer eclipsePluginConfigurer = new EclipsePluginConfigurer(project)
//      eclipsePluginConfigurer.configure(sourceSet, facet)
//    }

    // Add Task Dependencies

//    project.task("contractTest") {
//      group = "verification"
//      description = "run the contract tests"
//      doLast {
//        println("Hello from the GreetingPlugin")
//      }
//    }
  }
}

//TaskProvider<Test> createTestTask(String testName, SourceSet sourceSet) {
//  TaskProvider<Test> testTask = project.tasks.register(testName, Test)
//  testTask.configure(new Action<Test>() {
//    @Override
//    void execute(Test test) {
//      test.setGroup(JavaBasePlugin.VERIFICATION_GROUP)
//      test.setDescription("Runs the ${sourceSet.name} tests")
//      test.reports.html.setDestination(new File("${project.buildDir}/reports/${sourceSet.name}"))
//      test.reports.junitXml.setDestination(new File("${project.buildDir}/${sourceSet.name}-results"))
//      test.testClassesDirs = sourceSet.output.classesDirs
//      test.classpath = sourceSet.runtimeClasspath
//      test.mustRunAfter(project.tasks.named('test'))
//    }
//  })
//
//  testTask
//}
package au.com.mebank

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.testing.Test
//import org.gradle.api.artifacts.Configuration
//import org.gradle.internal.impldep.org.codehaus.plexus.ContainerConfiguration
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.the
import java.lang.module.Configuration

//getimport org.jetbrains.kotlin.gradle.plugin.KotlinBasePluginWrapper
//import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class TestPlugin : Plugin<Project> {
  override fun apply(project: Project) {

    // Add Configuration
//    project.configure(ConfigurationContainer::class) {
//
//    }

    val contractTestImplementation = project.configurations.create("contractTestImplementation") {
//      extendsFrom(project.configurations["testImplementation"])
    }

//    project.dependencies {
//      contractTestImplementation("org.testcontainers:testcontainers:1.10.6")
//      contractTestImplementation("org.testcontainers:junit-jupiter:1.10.6")
//      contractTestImplementation("org.testcontainers:selenium:1.10.6")
//    }

//    val sourceSets = project.the(SourceSetContainer::class)

//    sourceSets.create("test-integration") {
////      compileClasspath += sourceSets["main"]
////      compileClasspath += sourceSets["kotlin"].output
//    }

//    project.the(SourceSetContainer::class).create("test-integration") {
//        compileClasspath += sourceSets.main.get().output
//        runtimeClasspath += sourceSets.main.get().output
//
//    }

//    project.
//
//    project.sourceSets
//    // Add SourceSet
//    project.properties["sourceSetContainer"]<SourceSetContainer> {
//      val main by getting
//      val test by getting
//      val tlib by creating {
//        java {
//          srcDir("src/tlib/kotlin")
//          compileClasspath += main.output + test.output
//          runtimeClasspath += main.output + test.output
//        }
//      }
//    }
//
//    project.sourceSets.create("test-integration") {
//        compileClasspath += sourceSets.main.get().output
//        runtimeClasspath += sourceSets.main.get().output
//    }

//    if (project.file("src/itegration-test").exists()) {
//      project.apply plugin: 'nebula.facet'
//      project.facets {
//        integrationTest {
//          parentSourceSet = 'test'
//          name = 'integration-test'
//          testTaskName = 'integrationTest'
//          includeInCheckLifecycle = true
//        }
//      }
//      project.integrationTest {
//        reports {
//          html.enabled = !System.getenv().containsKey("CI")
//        }
//      }
//    }

//    // Add SourceSets
//SourceSets    project.plugins.withType<org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper> {
////
//    }

    // Add Task
    project.tasks.register("testIntegration", TestIntegrationTask::class)
    // Add Task Dependencies
//    val integrationTest = project.task<Test>("testIntegration") {
//      _ini
//      description = "Runs integration tests."
//      group = "verification"
//
//      testClassesDirs = sourceSets["intTest"].output.classesDirs
//      classpath = sourceSets["intTest"].runtimeClasspath
//      shouldRunAfter("test")
//    }
//
//    tasks.check { dependsOn(integrationTest) }

//    if (project.pluginManager.hasPlugin("kotlin")) {
//
//    }

//    project.task("contractTest") {
//      group = "verification"
//      description = "run the contract tests"
//      doLast {
//        println("Hello from the GreetingPlugin")
//      }
//    }
  }
}
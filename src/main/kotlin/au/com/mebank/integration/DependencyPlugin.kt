package au.com.mebank.integration

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.typeOf

//import kotlin.reflect.typeOf

open class DependencyPlugin : Plugin<Project> {

  override fun apply(project: Project): Unit = project.run {
    if (!plugins.hasPlugin("java")) {
      plugins.apply("java")
    }

    val reactorTest = fun() =
        project.dependencies.create(group = "io.projectreactor", name = "reactor-test", version = "3.3.0.RELEASE")

    val junitJupiter = fun(name: String) =
        project.dependencies.create(group = "org.junit.jupiter", name = name, version = "5.5.2")

    val junitJupiterAll = fun(): List<ExternalModuleDependency> {
      return listOf(
          project.dependencies.create(group = "org.junit.jupiter", name = "junit-jupiter-api", version = "5.5.2"),
          project.dependencies.create(group = "org.junit.jupiter", name = "junit-jupiter-engine", version = "5.5.2"),
          project.dependencies.create(group = "org.junit.jupiter", name = "junit-jupiter-params", version = "5.5.2")
      )
    }

//    dependencies.add()

//    fun DependencyHandlerScope.addJupiterTestLibs(dependencies: List<ExternalModuleDependency>) {
//      junitJupiterAll().forEach {
//        add("testImplementation", it)
//      }
//    }

//    fun DependencyHandler.ktorDependencies() {
////      listOf(
////          "io.ktor:ktor-server-core:$ktorVersion}",
////          "io.ktor:ktor-client-core:$ktorVersion",
////          "io.ktor:ktor-client-apache:$ktorVersion",
////          "io.ktor:ktor-gson:$ktorVersion",
////          "io.ktor:ktor-server-netty:$ktorVersion",
////          "org.koin:koin-ktor:$koinVersion",
////          "ch.qos.logback:logback-classic:$logbackVersion"
////      ).forEach {
////        add("implementation", it)
////      }
////
////      add("testImplementation","io.ktor:ktor-server-tests:$ktorVersion")
////    }

//    fun DependencyHandler.implementation(dependencies : List<ExternalModuleDependency>) {
//      dependencies.forEach {
//        add("implementation", it)
//      }
//    }
//
//    fun DependencyHandler.testImplementation(dependencies : List<ExternalModuleDependency>) {
//      dependencies.forEach {
//        add("testImplementation", it)
//      }
//    }

//    val junitJupiter = fun(name: String, version: String) : ExternalModuleDependency =
//        project.dependencies.create(group = "org.junit.jupiter", name = name, version = version)

    dependencies.localGroovy()

//    (project.dependencies as ExtensionAware).extensions
    dependencies.extensions
        .add(typeOf<(String) -> ExternalModuleDependency>(), "junitJupiter", junitJupiter)
    dependencies.extensions
        .add(typeOf<() -> ExternalModuleDependency>(), "reactorTest", reactorTest)
    dependencies.extensions
        .add(typeOf<() -> List<ExternalModuleDependency>>(), "junitJupiterAll", junitJupiterAll)
  }
}



//fun DependencyHandler.testImplementation(dependencies: List<ExternalModuleDependency>) {
//  dependencies.forEach {
//    add("testImplementation", it)
//  }
//}
//internal fun Project.wsdlToJava(): NamedDomainObjectContainer<WsdlPlugin.WsdlExtension> =
//    @Suppress("UNCHECKED_CAST")
//    extensions.getByName(WsdlPlugin.EXTENSION_NAME) as? NamedDomainObjectContainer<WsdlPlugin.WsdlExtension>
//        ?: throw IllegalStateException("${WsdlPlugin.EXTENSION_NAME} is not of the correct type")

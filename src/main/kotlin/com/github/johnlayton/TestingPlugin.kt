package com.github.johnlayton

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.the

class TestingPlugin : Plugin<Project> {

    companion object {
        const val EXTENSION_NAME = "testing"

//        private const val WSDL_LOCATION_PLACEHOLDER = "@WSDL_LOCATION@"
//        private const val WRAPPER_STYLE_PLACEHOLDER = "@ENABLE_WRAPPER_STYLE@"
//        private const val ASYNC_MAPPING_PLACEHOLDER = "@ENABLE_ASYNC_MAPPING@"
    }


    open class TestingExtension {

        var testContainersVersion = "1.10.6"

    }

    override fun apply(project: Project): Unit = project.run {
        val testingExtension =
                extensions.create<TestingExtension>(
                    EXTENSION_NAME)

        val testingConfiguration = configurations.create(
            EXTENSION_NAME + "Tools", {
            setTransitive(true)
            setVisible(true)
            defaultDependencies(object : Action<DependencySet> {
                override fun execute(dependencies: DependencySet) {
                    dependencies.add(project.dependencies.create("org.testcontainers:testcontainers:${testingExtension.testContainersVersion}"))
                    dependencies.add(project.dependencies.create("org.testcontainers:junit-jupiter:${testingExtension.testContainersVersion}"))
                    dependencies.add(project.dependencies.create("org.testcontainers:selenium:${testingExtension.testContainersVersion}"))
                }
            })
        })

//        val sourceSets = project.the(SourceSetContainer::class)


        val sourceSets = the<SourceSetContainer>()
//        val main = sourceSets.getByName("main")

//        configurations.named(main.apiConfigurationName) {
//            extendsFrom(wsdlToJavaConfiguration)
//        }

        sourceSets.create("test-integration") {
            compileClasspath += sourceSets["test"].output + sourceSets["test"].compileClasspath + testingConfiguration
            runtimeClasspath += sourceSets["test"].output + sourceSets["test"].runtimeClasspath + testingConfiguration
        }


    }
}
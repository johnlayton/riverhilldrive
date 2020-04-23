package com.github.johnlayton

import org.gradle.api.Plugin
import org.gradle.api.Project

class GrpcPlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
//        if (!plugins.hasPlugin("com.fkorotkov.libraries")) {
//            plugins.apply("com.fkorotkov.libraries")
//        }
    }
}
package com.github.johnlayton

import org.gradle.api.Plugin
import org.gradle.api.Project

class UpgradePlugin : Plugin<Project> {

    override fun apply(project: Project): Unit = project.run {
        if (!plugins.hasPlugin("com.github.ben-manes.versions")) {
            plugins.apply("com.github.ben-manes.versions")
        }

        if (!plugins.hasPlugin("com.dorkbox.GradleUtils")) {
            plugins.apply("com.dorkbox.GradleUtils")
        }

        if (!plugins.hasPlugin("se.patrikerdes.use-latest-versions")) {
            plugins.apply("se.patrikerdes.use-latest-versions")
        }
    }
}
package com.github.johnlayton

import io.kotest.core.spec.style.WordSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.eclipse.jgit.api.Git
import org.gradle.testfixtures.ProjectBuilder

class VersionPluginTest : WordSpec({

  "Using the Plugin ID" should {
    "Apply the Plugin" {
      val project = ProjectBuilder.builder().build()

      // Create Readme
      project.file("README.md").writeText("## Test Project")

      // Init git and add readme with tag
      val git = Git.init()
          .setDirectory(project.projectDir)
          .call()
      git.add().addFilepattern("README.md").call()
      git.commit().setMessage("Initial commit").call()
      git.tag().setName("0.0.0").call()

      // Set up the project
      project.pluginManager.apply(VersionPlugin::class.java)
      project.plugins.getPlugin(VersionPlugin::class.java) shouldNotBe null

      // Check the configuration
      project.isSnapshot shouldBe false
      project.version shouldBe "0.0.0"
    }
  }

})
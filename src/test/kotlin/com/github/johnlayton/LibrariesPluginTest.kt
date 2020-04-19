package com.github.johnlayton

import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.WordSpec
import org.eclipse.jgit.api.Git
import org.gradle.internal.impldep.org.junit.runner.RunWith
import org.gradle.testfixtures.ProjectBuilder

//@RunWith(TestJUnitRunner::class)
class LibrariesPluginTest : WordSpec({

  "Using the Plugin ID" should {
    "Apply the Plugin to Family" {
      val project = ProjectBuilder.builder().build()

//      // Create Readme
//      project.file("README.md").writeText("## Test Project")
//
//      // Init git and add readme with tag
//      val git = Git.init()
//              .setDirectory(project.projectDir)
//              .call()
//      git.add().addFilepattern("README.md").call()
//      git.commit().setMessage("Initial commit").call()
//      git.tag().setName("0.0.0").call()

      // Set up the project
      project.pluginManager.apply(LibrariesPlugin::class.java)
      project.plugins.getPlugin(LibrariesPlugin::class.java) shouldNotBe null

//      // Check the configuration
//      project.isSnapshot shouldBe false
//      project.version shouldBe "0.0.0"
    }
  }
})
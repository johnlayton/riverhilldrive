package com.github.johnlayton

import io.kotlintest.shouldNotBe
import io.kotlintest.specs.WordSpec
import org.gradle.testfixtures.ProjectBuilder

class WsdlPluginTest : WordSpec({

  "Using the Plugin ID" should {
    "Apply the Plugin" {
      val project = ProjectBuilder.builder().build()
      project.pluginManager.apply(WsdlPlugin::class.java)
      project.plugins.getPlugin(WsdlPlugin::class.java) shouldNotBe null
    }
  }

  "Applying the Plugin" should {
    "Register the 'wsdlToJava' extension" {
      val project = ProjectBuilder.builder().build()
      project.pluginManager.apply(WsdlPlugin::class.java)
      project.wsdlToJava() shouldNotBe null
    }
  }
})
package com.github.johnlayton

import io.kotlintest.shouldBe
import io.kotlintest.specs.WordSpec
import org.gradle.testfixtures.ProjectBuilder

class GroupPluginTest : WordSpec({

  "Using the Plugin ID" should {
    "Apply the Plugin to Family" {
      val ancestor = ProjectBuilder.builder().withName("ancestor").build()
      val parent = ProjectBuilder.builder().withName("parent").withParent(ancestor).build()
      val child = ProjectBuilder.builder().withName("parent").withParent(parent).build()

      child.pluginManager.apply(GroupPlugin::class.java)
      parent.pluginManager.apply(GroupPlugin::class.java)
      ancestor.pluginManager.apply(GroupPlugin::class.java)

      child.group shouldBe "com.github.johnlayton.ancestor.parent"
      parent.group shouldBe "com.github.johnlayton.ancestor"
      ancestor.group shouldBe "com.github.johnlayton"
    }

    "Apply the Plugin to Independent" {
      val ancestor = ProjectBuilder.builder().withName("ancestor").build()

      ancestor.pluginManager.apply(GroupPlugin::class.java)

      ancestor.group shouldBe "com.github.johnlayton"
    }
  }
})
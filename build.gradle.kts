import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
buildscript {
}
*/

plugins {
  base
  java
  publishing

  `maven-publish`
  `kotlin-dsl`

  kotlin("jvm") version "1.3.61" apply false
  kotlin("kapt") version "1.3.61" apply false

  kotlin("plugin.spring") version "1.3.61" apply false

  id("io.spring.dependency-management") version "1.0.8.RELEASE" apply false
  id("org.springframework.boot") version "2.2.0.RELEASE" apply false

  id("com.google.cloud.tools.jib") version "1.3.0" apply false

  // Local plugins
  id("plugin-version")
  id("plugin-group")
}

allprojects {

  apply(plugin = "base")
  apply(plugin = "java")

  repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
  }

  dependencies {
    implementation(kotlin("stdlib-jdk8", "1.3.61"))
  }

  tasks.withType<Test> {
    useJUnitPlatform()
  }

  java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
  }

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      freeCompilerArgs = listOf("-Xjsr305=strict")
      jvmTarget = "11" // JavaVersion.VERSION_11
    }
  }
}

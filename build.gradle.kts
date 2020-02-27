import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  dependencies {
    classpath("com.google.guava:guava:28.1-jre")
  }
  configurations {
    classpath {
      resolutionStrategy {
        cacheChangingModulesFor(0, "seconds")
        eachDependency {
          when (Pair(requested.group, requested.name)) {
            Pair("com.google.guava" , "guava") -> {
              logger.lifecycle("============================")
              logger.lifecycle("= Found guava ${requested}")
              logger.lifecycle("============================")
              useVersion("28.1-jre")
            }
          }
//      when (requested.group) {
//        "org.jetbrains.kotlin" -> useVersion(kotlinVersion)
//      }
        }
//        force("com.google.guava:guava:28.2-jre")
      }
    }
  }
}

//configurations.all {
//  resolutionStrategy {
//    activateDependencyLocking()
//    failOnVersionConflict()
//    eachDependency {
//      when (requested.group) {
//        "com.google.guava" -> {
//          logger.lifecycle("============================")
//          logger.lifecycle("= Found guava")
//          logger.lifecycle("============================")
//          useVersion("23.6-jre")
//        }
//      }
////      when (requested.group) {
////        "org.jetbrains.kotlin" -> useVersion(kotlinVersion)
////      }
//    }
//  }
//}

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

  id("com.google.cloud.tools.jib") version "2.0.0" apply false

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

//eachDependency { details ->
//  if (details.requested.group == 'org.apache.cxf') {
//    println("Force version for ${details.requested}")
//    details.useVersion '3.2.12'
//  }
//}


//configurations.all {
//  resolutionStrategy {
//    force("com.google.guava:guava:28.0")
//    eachDependency {
//      //    if (requested.version == "default") {
////    }
//    }
//    cacheChangingModulesFor(10, "seconds")
//    cacheDynamicVersionsFor(10, "seconds")
//  }
//}

//configurations.all {
//  resolutionStrategy {
//    force 'com.google.guava:guava:23.6.1-jre'
//  }
//}

val gradleWrapperVersion: String by project
tasks {
  wrapper {
    gradleVersion = gradleWrapperVersion
    distributionType = Wrapper.DistributionType.ALL
  }
}

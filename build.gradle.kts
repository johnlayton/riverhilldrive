plugins {
    base
//    kotlin("jvm")
    kotlin("jvm") version "1.3.50" apply false
}

allprojects {

    group = "au.com.mebank.demo"

    version = "1.0"

    repositories {
//        jcenter()
        mavenLocal()
        mavenCentral()
//        gradlePluginPortal()
////        maven("https://plugins.gradle.org/m2/")
    }
}

//dependencies {
//    // Make the root project archives configuration depend on every subproject
//    subprojects.forEach {
//        archives(it)
//    }
//}

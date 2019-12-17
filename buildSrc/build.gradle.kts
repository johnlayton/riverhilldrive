plugins {
//    kotlin("jvm")
//    id("org.gradle.kotlin.kotlin-dsl") version "1.2.9"
    `kotlin-dsl`
//    id("kotlin.kotlin-dsl")
//    id("org.gradle.kotlin.kotlin-dsl")
//    id("org.gradle.kotlin.kotlin-dsl") //version "1.3.3"
//    kotlin("jvm") version "1.3.60"
}

repositories {
    jcenter()
//    gradlePluginPortal()
}

//val db by configurations.creating
//val integTestImplementation by configurations.creating {
//    extendsFrom(configurations["testImplementation"])
//}
//
//dependencies {
//    db("org.postgresql:postgresql")
//    integTestImplementation("com.example:integ-test-support:1.3")
//}
//dependencies {
////    compileOnly(gradleApi())
////    compileOnly(kotlin("jvm"))
//    implementation(kotlin("gradle-plugin"))
//
////    compile("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.60")
//}

gradlePlugin {
    plugins {
        create("test") {
            id = "test"
            implementationClass = "au.com.mebank.TestPlugin"
        }
    }
}
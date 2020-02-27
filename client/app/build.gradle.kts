import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//buildscript {
//    configurations {
//        classpath {
//            resolutionStrategy {
//                cacheChangingModulesFor(0, "seconds")
////                force("com.google.guava:guava:28.2-jre")
//                eachDependency {
//                    when (Pair(requested.group, requested.name)) {
//                        Pair("com.google.guava" , "guava") -> {
//                            logger.lifecycle("============================")
//                            logger.lifecycle("= Found guava ${requested.module}")
//                            logger.lifecycle("============================")
//                            useVersion("21-jre")
//                        }
//                    }
//                    //specifying a fixed version for all libraries with 'org.gradle' group
////                    if (dep.requested.group == 'com.google.guava') {
////                        dep.useVersion '28.2-jre'
////                    }
//                }
//            }
//        }
//    }
//}

/*
plugins {
    id("java")

    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring")

    id("org.springframework.boot")
    id("io.spring.dependency-management")

    id("com.google.cloud.tools.jib")

    id("publishing")
    id("maven-publish")

    id("plugin-testing")
    id("plugin-github")
    id("plugin-dependency")
}
*/

plugins {
    base
    java
    publishing

    `maven-publish`
    `kotlin-dsl`

    kotlin("jvm") version "1.3.61"
    kotlin("kapt") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"

    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    id("org.springframework.boot") version "2.2.0.RELEASE"

    id("com.google.cloud.tools.jib") version "2.0.0"

//    id("publishing")
//    id("maven-publish")
}

repositories {
    jcenter()
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {

    gradleApi()

    kapt("org.springframework.boot:spring-boot-configuration-processor:+")

/*
    implementation(project(path = ":soap-service:sal"))
    implementation(project(path = ":rest-service:sal"))
*/

    implementation("au.com.mebank.integration:soap-service-sal:+")
/*
    implementation(project("au.com.mebank.integration:reast-service-sal:+"))
*/

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("io.micrometer:micrometer-registry-prometheus")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    testImplementation("com.github.JensPiegsa:wiremock-extension:0.4.0")

//    testImplementation(
//        "org.junit.jupiter:junit-jupiter-api:5.5.2",
//        "org.junit.jupiter:junit-jupiter-api:5.5.2"
//    )

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.5.2")
/*
    testImplementation(junitJupiter("junit-jupiter-api"))
    testImplementation(junitJupiter("junit-jupiter-engine"))
    testImplementation(junitJupiter("junit-jupiter-params"))
*/
/*
    testImplementation(junitJupiterAll)
*/

    testImplementation("io.projectreactor:reactor-test")
/*
    testImplementation(reactorTest())
*/
    testImplementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.0.1.RELEASE")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
}

//tasks.getByName<Jar>("jar") {
//    enabled = true
//}

//publishing {
//    publications {
//        create<MavenPublication>("bootJava") {
//            artifact(tasks.getByName("bootJar"))
//        }
//    }
//}

jib {
    to {
        image = "johnlayton/riverhilldrive-client"
        credHelper = "osxkeychain"
    }
}

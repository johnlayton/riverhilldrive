import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    publishing
    id("maven-publish")
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring") version "1.3.61"
//    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
//    kotlin("plugin.spring") version "1.3.61"
//    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

group = "au.com.mebank.demo.service"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
//    implementation("au.com.mebank.demo.service:model:+")

    compile(project(":soap-service:model"))
    implementation(kotlin("stdlib"))

//    optional ("org.springframework.boot:spring-boot-configuration-processor:+")
//    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor:+")
    kapt("org.springframework.boot:spring-boot-configuration-processor:+")
//    compileOnly ("org.springframework.boot:spring-boot-configuration-processor:+")
//    implementation("org.springframework.boot:spring-boot-configuration-processor:+")

    implementation("org.springframework.boot:spring-boot-starter-webflux:+")
//    implementation("org.projectreactor:reactor-spring")

//    implementation("org.springframework.boot:spring-boot-webflux")
//    implementation("org.jetbrains.kotlin:kotlin-reflect")
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

kapt {
//    sources = "build/kapt/sources"
//    generateStubs = true
    showProcessorTimings = true
}

//idea {
//    module {
//        def kaptMain = file("${project.buildDir}/generated/source/kapt/main")
//        sourceDirs += kaptMain
//        generatedSourceDirs += kaptMain
//
//        outputDir file("${project.buildDir}/classes/main")
//        testOutputDir file("${project.buildDir}/classes/test")
//    }
//}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
}
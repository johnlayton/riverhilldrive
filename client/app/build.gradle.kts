import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring") version "1.3.61"
    id("org.springframework.boot") version "2.2.0.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
//    id("integration")
//    id("au.com.mebank.integration")
//    `au.com.mebank.integration`
//    id("integration")
    id("test")
}

//project {
//    name =
//}

//name "app"

group = "au.com.mebank.client"
version = "0.0.1"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {

    implementation(project(":soap-service:sal"))
    implementation(project(":soap-service:model"))
//    implementation(project(":rest-service:sal"))
//    implementation(project(":rest-service:model"))
//    implementation(project(":grpc-service:sal"))
//    implementation(project(":grpc-service:model"))

//    implementation("au.com.mebank.demo.service:sal:+")
//    implementation("au.com.mebank.demo.service:model:+")

//    compileOnly ("org.springframework.boot:spring-boot-configuration-processor")
    kapt ("org.springframework.boot:spring-boot-configuration-processor:+")
    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor:+")
    implementation ("org.springframework.boot:spring-boot-configuration-processor:+")

//    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

//    implementation("io.projectreactor:reactor-core:3.2.11.RELEASE")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    testImplementation("com.github.JensPiegsa:wiremock-extension:0.4.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.5.2")
    testImplementation("io.projectreactor:reactor-test")

//    testImplementation("org.testcontainers:testcontainers:1.10.6")
//    testImplementation("org.testcontainers:junit-jupiter:1.10.6")
//    testImplementation("org.testcontainers:selenium:1.10.6")
//
//    contractTestImplementation("org.testcontainers:testcontainers:1.10.6")
//    contractTestImplementation("org.testcontainers:junit-jupiter:1.10.6")
//    contractTestImplementation("org.testcontainers:selenium:1.10.6")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

//sourceSets
//hello {
//    message.set("i go find the wsdl here ....")
//}

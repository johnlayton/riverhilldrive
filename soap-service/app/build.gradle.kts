import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
}


val apacheCXFVersion: String by project
val javaxXmlVersion: String by project
val sunXmlVersion: String by project
val javaxActivation: String by project

dependencies {
//    implementation(project(":soap-service:api"))
    implementation(project(":soap-service:model"))
//    implementation("au.com.mebank.demo.service:model:+")

//    compileOnly ("org.springframework.boot:spring-boot-configuration-processor")
    kapt ("org.springframework.boot:spring-boot-configuration-processor:+")
//    annotationProcessor ("org.springframework.boot:spring-boot-configuration-processor:+")
//    implementation ("org.springframework.boot:spring-boot-configuration-processor:+")
//    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
//    implementation("io.projectreactor:reactor-core:3.2.11.RELEASE")
//    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")


//    implementation("org.apache.cxf:cxf-spring-boot-starter-jaxws:3.3.4")
    implementation("org.apache.cxf:cxf-spring-boot-starter-jaxws:${apacheCXFVersion}")
    implementation("org.springframework.ws:spring-ws-core")
    implementation("javax.xml.ws:jaxws-api:2.3.1")
    implementation("javax.jws:jsr181-api:1.0-MR1")

    implementation ("org.apache.cxf:cxf-rt-frontend-jaxws:${apacheCXFVersion}")
    implementation ("org.apache.cxf:cxf-rt-features-logging:${apacheCXFVersion}")

    implementation("javax.xml.bind:jaxb-api:${javaxXmlVersion}")
    implementation("javax.xml.ws:jaxws-api:${javaxXmlVersion}")
    implementation("com.sun.xml.bind:jaxb-core:${sunXmlVersion}")
    implementation("com.sun.xml.bind:jaxb-impl:${sunXmlVersion}")
    implementation("javax.activation:activation:${javaxActivation}")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }

    testImplementation("com.github.JensPiegsa:wiremock-extension:0.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.5.2")
    testImplementation("io.projectreactor:reactor-test")

    testImplementation("org.testcontainers:testcontainers:1.10.6")
    testImplementation("org.testcontainers:junit-jupiter:1.10.6")
    testImplementation("org.testcontainers:selenium:1.10.6")
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

publishing {
    publications {
        create<MavenPublication>("bootJava") {
            artifact(tasks.getByName("bootJar"))
        }
    }
}

/*
java.sourceSets.create("test-integration").java {
    srcDir("src/test-integration/kotlin")
}
*/

jib {
    to {
        image = "johnlayton/riverhilldrive-service"
        credHelper = "osxkeychain"
    }
}
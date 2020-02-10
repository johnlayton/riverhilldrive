plugins {
  java
  publishing
  id("maven-publish")
  kotlin("jvm")
  kotlin("kapt")
  kotlin("plugin.spring")

  id("io.spring.dependency-management")

  id("plugin-group")
  id("plugin-version")
}

dependencies {
  kapt("org.springframework.boot:spring-boot-configuration-processor:+")
  api(project(path = ":soap-service:api"))
//  api("au.com.mebank.integration:api:+")
  implementation("org.springframework.boot:spring-boot-starter-webflux:+")
}

kapt {
  showProcessorTimings = true
}

publishing {
  publications {
    create<MavenPublication>("default") {
      from(components["java"])
    }
  }
}

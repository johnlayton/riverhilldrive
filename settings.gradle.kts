pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    // The resolutionStrategy only needs to be configured for
    // local plugin development, specifically when using the
    // mavenLocal repository.
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.google.protobuf") {
                useModule("com.google.protobuf:protobuf-gradle-plugin:${requested.version}")
            }
        }
    }
}

//rootProject.name = "riverhilldrive"

include(
    "soap-service:model",
    "soap-service:api",
    "soap-service:sal",
    "soap-service:app",

    "rest-service:model",
    "rest-service:sal",
    "rest-service:app",

    "grpc-service:model",
    "grpc-service:sal",
    "grpc-service:app",

    "client:app"
)
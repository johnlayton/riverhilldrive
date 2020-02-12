pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenLocal()
        mavenCentral()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
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
    "soap-service:app",
    "soap-service:api",
    "soap-service:sal",

    "rest-service:model",
    "rest-service:app",
    "rest-service:api",
    "rest-service:sal",
//
//    "grpc-service:model",
//    "grpc-service:sal",
//    "grpc-service:app",

    "client:app"
)
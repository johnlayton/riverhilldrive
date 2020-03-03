import org.eclipse.jgit.api.Git

plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    jcenter()
}

setGroup("au.com.mebank.integration")
version = Git.open(project.rootDir).describe().setAlways(true).call()

gradlePlugin {
    plugins {
        create("test") {
            id = "test"
            implementationClass = "au.com.mebank.TestPlugin"
        }
        register("plugin-wsdl") {
            id = "plugin-wsdl"
            implementationClass = "au.com.mebank.integration.WsdlPlugin"
        }
        register("plugin-openapi") {
            id = "plugin-openapi"
            implementationClass = "au.com.mebank.integration.OpenApiPlugin"
        }
        register("plugin-version") {
            id = "plugin-version"
            implementationClass = "au.com.mebank.integration.VersionPlugin"
        }
        register("plugin-group") {
            id = "plugin-group"
            implementationClass = "au.com.mebank.integration.GroupPlugin"
        }
        register("plugin-utils") {
            id = "plugin-utils"
            implementationClass = "au.com.mebank.integration.UtilsPlugin"
        }
        register("plugin-testing") {
            id = "plugin-testing"
            implementationClass = "au.com.mebank.integration.TestingPlugin"
        }
        register("plugin-bitbucket") {
            id = "plugin-bitbucket"
            implementationClass = "au.com.mebank.integration.BitbucketPlugin"
        }
        register("plugin-github") {
            id = "plugin-github"
            implementationClass = "au.com.mebank.integration.GithubPlugin"
        }
        register("plugin-dependency") {
            id = "plugin-dependency"
            implementationClass = "au.com.mebank.integration.DependencyPlugin"
        }
    }
}

val testVersion : String by project
val jgitVersion : String by project
val jacksonVersion : String by project
val fuelVersion : String by project
val githubVersion : String by project
val openapiVersion : String by project
dependencies {
    // Version Plugin
    implementation("org.eclipse.jgit:org.eclipse.jgit:${jgitVersion}")

    // Bitbucket Plugin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-databind:${jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-core:${jacksonVersion}")
    implementation("com.fasterxml.jackson.core:jackson-annotations:${jacksonVersion}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:${jacksonVersion}")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:${jacksonVersion}")
    implementation("com.github.kittinunf.fuel:fuel:${fuelVersion}")
    implementation("com.github.kittinunf.fuel:fuel-jackson:${fuelVersion}")

    // Github Plugin
    implementation("org.kohsuke:github-api:${githubVersion}")

    // OpenApi
    implementation("org.openapitools:openapi-generator:${openapiVersion}")

    testImplementation(gradleApi())
    testImplementation(gradleTestKit())
    testImplementation("io.kotlintest:kotlintest-runner-junit5:${testVersion}")
}

publishing {
    repositories {
        mavenLocal()
    }
}

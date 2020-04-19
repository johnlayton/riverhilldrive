import org.eclipse.jgit.api.Git

plugins {
    `kotlin-dsl`
    `maven-publish`
}

repositories {
    jcenter()
    gradlePluginPortal()
}

setGroup("com.github.johnlayton")
version = Git.open(project.rootDir).describe().setAlways(true).call()

gradlePlugin {
    plugins {
        register("plugin-wsdl") {
            id = "plugin-wsdl"
            implementationClass = "com.github.johnlayton.WsdlPlugin"
        }
        register("plugin-openapi") {
            id = "plugin-openapi"
            implementationClass = "com.github.johnlayton.OpenApiPlugin"
        }
        register("plugin-version") {
            id = "plugin-version"
            implementationClass = "com.github.johnlayton.VersionPlugin"
        }
        register("plugin-group") {
            id = "plugin-group"
            implementationClass = "com.github.johnlayton.GroupPlugin"
        }
        register("plugin-utils") {
            id = "plugin-utils"
            implementationClass = "com.github.johnlayton.UtilsPlugin"
        }
        register("plugin-testing") {
            id = "plugin-testing"
            implementationClass = "com.github.johnlayton.TestingPlugin"
        }
        register("plugin-bitbucket") {
            id = "plugin-bitbucket"
            implementationClass = "com.github.johnlayton.BitbucketPlugin"
        }
        register("plugin-github") {
            id = "plugin-github"
            implementationClass = "com.github.johnlayton.GithubPlugin"
        }
        register("plugin-dependency") {
            id = "plugin-dependency"
            implementationClass = "com.github.johnlayton.DependencyPlugin"
        }
        register("plugin-upgrade") {
            id = "plugin-upgrade"
            implementationClass = "com.github.johnlayton.UpgradePlugin"
        }
        register("plugin-libraries") {
            id = "plugin-libraries"
            implementationClass = "com.github.johnlayton.LibrariesPlugin"
        }
    }
}

val kotlinTestVersion : String by project
val jgitVersion : String by project
val jacksonVersion : String by project
val fuelVersion : String by project
val githubVersion : String by project
val openapiVersion : String by project
val gradleUtilsVersion : String by project
val useLatestVersion : String by project
val gradleLibrariesVersion : String by project
dependencies {
    // Libraries Plugin
    implementation("com.fkorotkov", "gradle-libraries-plugin", gradleLibrariesVersion)

    // Upgrade Plugin
    implementation("gradle.plugin.com.dorkbox", "GradleUtils", gradleUtilsVersion)
    implementation("se.patrikerdes", "gradle-use-latest-versions-plugin", useLatestVersion)

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

    testImplementation("io.kotest:kotest-runner-junit5-jvm:${kotlinTestVersion}")
    testImplementation("io.kotest:kotest-assertions-core-jvm:${kotlinTestVersion}")
    testImplementation("io.kotest:kotest-property-jvm:${kotlinTestVersion}")
}

publishing {
    repositories {
        mavenLocal()
    }
}

tasks {
    register("showVersion") {
        project.run {
            logger.lifecycle("========================================================================")
            logger.lifecycle("Project Version : ${project.version}")
            logger.lifecycle("========================================================================")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val gradleWrapperVersion: String by project
tasks {
    wrapper {
        gradleVersion = gradleWrapperVersion
        distributionType = Wrapper.DistributionType.ALL
    }
}
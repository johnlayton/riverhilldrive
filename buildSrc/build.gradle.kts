plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}

gradlePlugin {
    plugins {
        create("test") {
            id = "test"
            implementationClass = "au.com.mebank.TestPlugin"
        }
    }
    plugins {
        register("plugin-wsdl") {
            id = "plugin-wsdl"
            implementationClass = "au.com.mebank.integration.WsdlPlugin"
        }
    }
    plugins {
        register("plugin-version") {
            id = "plugin-version"
            implementationClass = "au.com.mebank.integration.VersionPlugin"
        }
    }
    plugins {
        register("plugin-group") {
            id = "plugin-group"
            implementationClass = "au.com.mebank.integration.GroupPlugin"
        }
    }
    plugins {
        register("plugin-utils") {
            id = "plugin-utils"
            implementationClass = "au.com.mebank.integration.UtilsPlugin"
        }
    }
}

val testVersion : String by project
val jgitVersion : String by project
dependencies {
    implementation("org.eclipse.jgit:org.eclipse.jgit:${jgitVersion}")

    testImplementation(gradleApi())
    testImplementation(gradleTestKit())
    testImplementation("io.kotlintest:kotlintest-runner-junit5:${testVersion}")
}


/*
publishing {
    repositories {
        mavenLocal()
    }
}
*/

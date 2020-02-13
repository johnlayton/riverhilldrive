import au.com.mebank.integration.WsdlPlugin.WsdlToJavaExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")

    kotlin("jvm")

    id("publishing")
    id("maven-publish")

    // Local plugins
//    id("integration")
    id("plugin-group")
    id("plugin-version")
//    id("plugin-wsdl")
    id("plugin-utils")


    id("org.openapi.generator") version "4.2.3"
}

//classpath("org.openapitools:openapi-generator-gradle-plugin:4.1.1")

/*
configure<WsdlToJavaExtension> {
    apacheCXFVersion = "3.3.4"
    wsdls {
        register("demo") {
            wsdl.set(file("${projectDir}/src/demo/resources/Demo.wsdl"))
//            pkgs.put("http://mebank.com.au/service", "au.com.mebank.integration.service")
        }
    }
}
*/

/*wsdlToJava {

    logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
    logger.info("++ Configure the WSDL Plugin")
    logger.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++")

//    apacheCXFVersion = "3.3.4"
    wsdls {
        register("demo") {
            wsdl.set(file("${projectDir}/src/demo/resources/demo.wsdl"))
//            http://localhost:8092/service/demo?wsdl
//            pkgs.put("http://mebank.com.au/service", "au.com.mebank.integration.service.api")
//            pkgs.put("tns", "au.com.mebank.integration.service.api")
        }
    }
}*/

openApiGenerate {
    generatorName.set("kotlin")
    inputSpec.set("$projectDir/src/main/resources/demo.yaml")
    outputDir.set("$buildDir/generated")
    apiPackage.set("au.com.mebank.rest.service.api")
    invokerPackage.set("au.com.mebank.rest.service.invoker")
    modelPackage.set("au.com.mebank.rest.service.model")
//    modelFilesConstrainedTo = [
//        "Error"
//    ]
//    configOptions = [
//        dateLibrary: "java8"
//    ]
}

publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
}

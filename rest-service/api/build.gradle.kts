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
}

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


publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
        }
    }
}

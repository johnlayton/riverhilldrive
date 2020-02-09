package au.com.mebank.client.app

import org.slf4j.LoggerFactory
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan(
    "au.com.mebank.service.sal.config"
)
@ComponentScans(
    ComponentScan("au.com.mebank.client.app.handler"),
    ComponentScan("au.com.mebank.client.app.router"),
    ComponentScan("au.com.mebank.service.sal"),
    ComponentScan("au.com.mebank.service.api")
//    ,
//    ComponentScan("au.com.mebank.service")
)
class Application {
  private val log = LoggerFactory.getLogger(Application::class.java)
}

fun main(args: Array<String>) {
  runApplication<Application>(*args) {
//    setBannerMode(Banner.Mode.OFF)
//    setBanner(Banner())/
    webApplicationType = WebApplicationType.REACTIVE
  }
}

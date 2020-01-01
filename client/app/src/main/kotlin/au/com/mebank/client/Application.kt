package au.com.mebank.client

import org.slf4j.LoggerFactory
import org.springframework.boot.Banner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans
import org.springframework.context.annotation.FilterType

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan(
    "au.com.mebank.service.config"
)
@ComponentScans(
    ComponentScan("au.com.mebank")
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

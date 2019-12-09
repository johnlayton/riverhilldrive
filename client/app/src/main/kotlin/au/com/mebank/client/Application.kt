package au.com.mebank.client

import org.slf4j.LoggerFactory
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans
import org.springframework.context.annotation.FilterType

@SpringBootApplication
@ComponentScans(ComponentScan("au.com.mebank.service.config"))
class Application {
  private val log = LoggerFactory.getLogger(Application::class.java)
}

fun main(args: Array<String>) {

  System.getProperties()

  runApplication<Application>(*args) {
    setBannerMode(Banner.Mode.OFF)
  }
}

package au.com.mebank.rest.service.app

import org.slf4j.LoggerFactory
import org.springframework.boot.Banner
import org.springframework.boot.WebApplicationType
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application {
    private val log = LoggerFactory.getLogger(Application::class.java)
    init {
        log.info("Application Started")
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args) {
//        setBannerMode(Banner.Mode.OFF)
        webApplicationType = WebApplicationType.REACTIVE
//        webApplicationType = WebApplicationType.SERVLET
    }
}

package au.com.mebank.service.router

import au.com.mebank.service.handler.DemoHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

import org.springframework.web.reactive.function.BodyInserters.fromObject

@Configuration
class DemoRouter(private val handler: DemoHandler) {
    @Bean
    fun route() = router {
        POST("/demo", handler::sayHello)
    }
}
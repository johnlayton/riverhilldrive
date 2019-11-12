package au.com.mebank.client.router

import au.com.mebank.client.handler.DemoHandler
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
//        GET("/demo") { _ -> ServerResponse.ok().body(fromObject(arrayOf(1, 2, 3))) }
    }
}
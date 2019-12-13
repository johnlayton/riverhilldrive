package au.com.mebank.service.router

import au.com.mebank.service.handler.DemoHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

import org.springframework.web.reactive.function.BodyInserters.fromObject
import reactor.core.publisher.Mono

@Configuration
class DemoRouter(private val handler: DemoHandler) {

    init {
        println("+++++++++++++++++++")
        println("+++ here ++++++++++")
        println("+++++++++++++++++++")
    }

    @Bean
    fun route() = router {
        POST("/demo", handler::sayHello)
//        GET("/demo", {
//            ServerResponse.ok().body(BodyInserters.fromValue("hello world"))
//        })
    }
}
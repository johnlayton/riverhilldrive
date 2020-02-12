package au.com.mebank.rest.service.app.router

import au.com.mebank.rest.service.app.handler.DemoHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class DemoRouter(private val handler: DemoHandler) {

    init {
        println("+++++++++++++++++++")
        println("+++ Router ++++++++")
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
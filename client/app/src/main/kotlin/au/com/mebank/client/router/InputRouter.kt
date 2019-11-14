package au.com.mebank.client.router

import au.com.mebank.client.handler.InputHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router

@Configuration
class InputRouter(private val handler: InputHandler) {
    @Bean
    fun route() = router {
        POST("/demo", handler::sayHello)
//        GET("/demo") { _ -> ServerResponse.ok().body(fromObject(arrayOf(1, 2, 3))) }
    }
}
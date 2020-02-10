package au.com.mebank.client.app.router

import au.com.mebank.client.app.handler.InputHandler
import au.com.mebank.client.app.model.InputResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class InputRouter(private val handler: InputHandler) {

    @Bean
    fun route() = router {
        POST("/demo", handler::sayHello)
        GET("/demo") { _ ->
            ServerResponse.ok().body(fromValue(InputResponse(1, "Hello world!!!")))
        }
    }

//    @Bean
//    fun router() : RouterFunction<ServerResponse> {
//
//        return RouterFunctions.route()
//            .path("/demo", { builder ->
//                builder.GET("", HandlerFunction<ServerResponse>() {
//                    ServerResponse.ok().body(fromValue(InputResponse(0, "Hello world!!!")))
//                })
//
//            })
//            .build()
//
////        return route(GET("/demo"), HandlerFunction<ServerResponse>() {
////            ServerResponse.ok().body(fromValue(InputResponse(0, "Hello world!!!")))
////        })
//
////            { req ->
////            ServerResponse.ok().body(fromValue(InputResponse(0, "Hello world!!!")))
////        })
//    }

}
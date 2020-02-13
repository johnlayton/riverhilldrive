package au.com.mebank.rest.service.app.router

import au.com.mebank.rest.service.app.handler.DemoHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class DemoRouter(private val handler: DemoHandler) {

    init {
        println("+++++++++++++++++++")
        println("+++ Router ++++++++")
        println("+++++++++++++++++++")
    }

    @Bean
    fun route(resourceLoader: ResourceLoader) = router {

//       If open api file is in dependency...
        val swagger = resourceLoader.classLoader!!.getResource("demo.yaml")!!
            .readText(charset = Charsets.UTF_8)

//       If open api file is local...
//        val swagger = resourceLoader.getResource("classpath:demo.yml")
//            .file.readText(charset = Charsets.UTF_8)

        POST("/demo", handler::sayHello)
        GET("/demo.yaml", {
            ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(swagger))
        })
    }
}
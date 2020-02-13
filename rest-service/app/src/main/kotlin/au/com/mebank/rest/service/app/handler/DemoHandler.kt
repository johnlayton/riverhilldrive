package au.com.mebank.rest.service.app.handler

import au.com.mebank.rest.service.model.DemoRequest
import au.com.mebank.rest.service.model.DemoResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class DemoHandler {

    init {
        println("+++++++++++++++++++")
        println("+++ handler +++++++")
        println("+++++++++++++++++++")
    }

    private val log = LoggerFactory.getLogger(DemoHandler::class.java)

    fun sayHello(request: ServerRequest): Mono<ServerResponse> {
        log.info("Say Hello")
        return request.bodyToMono(DemoRequest::class.java).flatMap { it ->
            ServerResponse.ok().body(fromValue(DemoResponse(it.id, it.name + " - demo")))
        }.log()
    }

}
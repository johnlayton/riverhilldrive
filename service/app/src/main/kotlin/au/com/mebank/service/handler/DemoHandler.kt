package au.com.mebank.service.handler

import au.com.mebank.service.DemoRequest
import au.com.mebank.service.DemoResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class DemoHandler {

  fun sayHello(request: ServerRequest): Mono<ServerResponse> {
    return request.bodyToMono(DemoRequest::class.java).flatMap { it ->
      ServerResponse.ok().body(fromValue(DemoResponse(it.id, it.name)))
    }
  }

}
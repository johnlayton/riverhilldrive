package au.com.mebank.client.app.handler

import au.com.mebank.client.app.model.InputRequest
import au.com.mebank.client.app.model.InputResponse
import au.com.mebank.service.sal.client.DemoClient
import au.com.mebank.service.sal.model.DemoRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class InputHandler
@Autowired
constructor(/*private val client: WebClient,*/
    private val client: DemoClient
) {

  private val log = LoggerFactory.getLogger(InputHandler::class.java)

  fun sayHello(request: ServerRequest): Mono<ServerResponse> {
/*
    return request.bodyToMono(InputRequest::class.java).flatMap {
      client.post()
          .contentType(MediaType.APPLICATION_JSON)
          .bodyValue(it)
          .retrieve()
          .bodyToMono(InputResponse::class.java)
          .flatMap { response ->
            ServerResponse.ok().body(fromValue(InputResponse(response.id, response.name)))
          }
    }
*/
/*override fun sayHello(request: DemoRequest): Mono<DemoResponse> {
    return WebClient.builder()
            .baseUrl(address!!)
            .build()
            .post()
            .bodyValue(request)
            .retrieve()
            .bodyToMono(DemoResponse::class.java)
}*/
    log.info("Handle Input")
    return request.bodyToMono(InputRequest::class.java)
        .doOnNext {
          log.info("Input request -> ${it}")
        }
        .map {
          val req = DemoRequest(it.id, it.name)
          log.info("Demo request -> ${req}")
          req
        }
        .flatMap {
          log.info("Say Hello -> $it")
          client.sayHello(it)
        }
        .flatMap { response ->
          log.info("Demo Reqponses -> ${response}")
          ServerResponse.ok().body(fromValue(InputResponse(response.id, response.name)))
        }
  }
}

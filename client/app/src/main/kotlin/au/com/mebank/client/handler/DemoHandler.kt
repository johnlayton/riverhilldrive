package au.com.mebank.client.handler

import au.com.mebank.client.model.InputRequest
import au.com.mebank.client.model.InputResponse
import au.com.mebank.service.DemoClient
import au.com.mebank.service.DemoRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class DemoHandler(/*private val client: WebClient,*/
        private val client: DemoClient) {

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

        return request.bodyToMono(InputRequest::class.java)
                .map {
                    DemoRequest(it.id, it.name)
                }
                .flatMap {
                    client.sayHello(it)
                }
                .flatMap { response ->
                    ServerResponse.ok().body(fromValue(InputResponse(response.id, response.name)))
                }
    }
}

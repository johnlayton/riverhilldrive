package au.com.mebank.client.handler

import au.com.mebank.client.model.InputRequest
import au.com.mebank.client.model.InputResponse
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


@Component
class DemoHandler(private val client: WebClient) {

  fun sayHello(request: ServerRequest): Mono<ServerResponse> {

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
  }
}

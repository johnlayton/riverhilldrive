package au.com.mebank.client.handler

import au.com.mebank.client.model.InputRequest
import au.com.mebank.client.model.InputResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.BodyInserters.fromValue
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


@Component
class DemoHandler(private val client: WebClient) {

  fun sayHello(request: ServerRequest): Mono<ServerResponse> {

//    return client.post()
//        .uri("/demo")
//        .body(request.bodyToMono(InputRequest::class.java))
///*
//        .header("Authorization", "Basic " + Base64Utils
//            .encodeToString((username + ":" + token).getBytes(UTF_8)))
//*/
//        .retrieve()
//        .bodyToMono(InputResponse::class.java)

    return request.bodyToMono(InputRequest::class.java).flatMap { it ->
      ServerResponse.ok().body(fromValue(client
          .post()
          .uri("/demo")
          .body(BodyInserters.fromPublisher(Mono.just(it), InputRequest::class.java))
          .bodyToMono(InputResponse::class.java)))
    }
//    return request.bodyToMono(DemoRequest::class.java).flatMap { it ->
//      ServerResponse.ok().body(fromValue(DemoResponse(it.id, it.name)))
//    }
  }

}
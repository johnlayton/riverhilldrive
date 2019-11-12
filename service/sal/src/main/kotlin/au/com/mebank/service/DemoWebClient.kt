package au.com.mebank.service

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class DemoWebClient(address: String) : DemoClient {

    val client = WebClient.builder().baseUrl(address).build();

    override fun sayHello(request: DemoRequest): Mono<DemoResponse> {
        return client.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DemoResponse::class.java)
    }

}
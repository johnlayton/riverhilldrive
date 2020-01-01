package au.com.mebank.service.client

import au.com.mebank.service.DemoRequest
import au.com.mebank.service.DemoResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

//@Component
class DemoWebClient(address: String) : DemoClient {

    val client = WebClient.builder().baseUrl(address).build();

    override fun sayHello(request: DemoRequest): Mono<DemoResponse> {
        return client.post()
                .bodyValue(request)
                .retrieve()
                .bodyToMono(DemoResponse::class.java)
    }

}
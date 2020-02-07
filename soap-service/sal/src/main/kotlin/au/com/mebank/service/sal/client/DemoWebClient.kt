package au.com.mebank.service.sal.client

import au.com.mebank.service.DemoService
import au.com.mebank.service.sal.model.DemoRequest
import au.com.mebank.service.sal.model.DemoResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

//@Component
//class DemoWebClient(address: String) : DemoClient {
class DemoWebClient(private val demoService : DemoService) : DemoClient {

//    val client = WebClient.builder().baseUrl(address).build();

    override fun sayHello(request: DemoRequest): Mono<DemoResponse> {
        return Mono.just(demoService.sayHello(request.let {
            au.com.mebank.service.DemoRequest().apply {
                id = it.id
                name = it.name
            }
        }).let {
            DemoResponse(it.id, it.name)
        })
//        return client.post()
//                .bodyValue(request)
//                .retrieve()
//                .bodyToMono(DemoResponse::class.java)
    }

}
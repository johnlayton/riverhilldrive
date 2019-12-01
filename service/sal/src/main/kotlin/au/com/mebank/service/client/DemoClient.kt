package au.com.mebank.service.client

import au.com.mebank.service.DemoRequest
import au.com.mebank.service.DemoResponse
import reactor.core.publisher.Mono

interface DemoClient {

  fun sayHello(request: DemoRequest) : Mono<DemoResponse>

}
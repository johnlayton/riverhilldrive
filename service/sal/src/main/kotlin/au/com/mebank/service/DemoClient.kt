package au.com.mebank.service

import reactor.core.publisher.Mono

interface DemoClient {

  fun sayHello(request: DemoRequest) : Mono<DemoResponse>

}
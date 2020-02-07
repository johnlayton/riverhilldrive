package au.com.mebank.service.sal.client

import au.com.mebank.service.sal.model.DemoRequest
import au.com.mebank.service.sal.model.DemoResponse

import reactor.core.publisher.Mono

interface DemoClient {
  fun sayHello(request: DemoRequest) : Mono<DemoResponse>
}
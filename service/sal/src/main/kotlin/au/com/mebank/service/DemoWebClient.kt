package au.com.mebank.service

import reactor.core.publisher.Mono

class DemoWebClient : DemoClient {

  override fun sayHello(request: DemoRequest): Mono<DemoResponse> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}
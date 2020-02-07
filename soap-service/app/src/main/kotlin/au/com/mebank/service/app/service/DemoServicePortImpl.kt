package au.com.mebank.service.app.service

import au.com.mebank.service.model.DemoRequest
import au.com.mebank.service.model.DemoResponse
import javax.jws.WebService

@WebService(
    serviceName = "DemoService",
    portName = "DemoServicePort",
    targetNamespace = "http://mebank.com.au/service",
    endpointInterface = "au.com.mebank.service.app.service.DemoService")
class DemoServicePortImpl : DemoService {

  override fun sayHello(request: DemoRequest): DemoResponse {
    return DemoResponse(request.id,
        "Hello, Welcome to CXF Spring boot ${request.name}!!!");
  }

}
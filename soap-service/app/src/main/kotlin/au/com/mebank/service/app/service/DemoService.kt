package au.com.mebank.service.app.service

import au.com.mebank.service.model.DemoRequest
import au.com.mebank.service.model.DemoResponse
import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.jws.soap.SOAPBinding
import javax.xml.ws.RequestWrapper
import javax.xml.ws.ResponseWrapper

@WebService(name = "DemoService",
    targetNamespace = "http://mebank.com.au/service")
//,
//    wsdlLocation = "http://localhost:8092/soap-api/demo?wsdl")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.ENCODED, parameterStyle = SOAPBinding.ParameterStyle.BARE)
interface DemoService {

//  @WebMethod(action = "urn:SayHello")
  @WebMethod(operationName = "sayHello")
  @RequestWrapper( // localName = "sayHello",
      targetNamespace = "http://service.mebank.com.au/types",
      className = "au.com.mebank.service.model.DemoRequest")
  @ResponseWrapper( //localName = "sayHelloResponse",
      targetNamespace = "http://service.mebank.com.au/types",
      className = "au.com.mebank.service.model.DemoResponse")
  @WebResult(name = "response", targetNamespace = "http://service.mebank.com.au/types")
//  fun sayHello(@WebParam(name = "request", targetNamespace = "http://service.mebank.com.au/types", mode = WebParam.Mode.IN) request: DemoRequest): DemoResponse
  fun sayHello(@WebParam(name = "request") request: DemoRequest): DemoResponse
//  fun sayHello(request: DemoRequest): DemoResponse
}
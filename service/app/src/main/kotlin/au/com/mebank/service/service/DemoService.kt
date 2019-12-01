package au.com.mebank.service.service

import javax.jws.WebMethod
import javax.jws.WebParam
import javax.jws.WebResult
import javax.jws.WebService
import javax.xml.ws.RequestWrapper
import javax.xml.ws.ResponseWrapper

@WebService(targetNamespace = "http://service.ws.sample/", name = "Hello")
interface DemoService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "sayHello",
            targetNamespace = "http://service.ws.sample/",
            className = "sample.ws.service.SayHello")
    @WebMethod(action = "urn:SayHello")
    @ResponseWrapper(localName = "sayHelloResponse",
            targetNamespace = "http://service.ws.sample/",
            className = "sample.ws.service.SayHelloResponse")
    fun sayHello(@WebParam(name = "myname", targetNamespace = "") myname: String): String
}
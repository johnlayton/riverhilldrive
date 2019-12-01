package au.com.mebank.service.service

import javax.jws.WebService

@WebService(serviceName = "DemoService", portName = "DemoServicePort",
        targetNamespace = "http://service.ws.sample/",
        endpointInterface = "au.com.mebank.service.service.DemoService")
public class DemoServicePortImpl : DemoService {

    override fun sayHello(myname: String): String {
        return "Hello, Welcome to CXF Spring boot " + myname + "!!!";
    }

}
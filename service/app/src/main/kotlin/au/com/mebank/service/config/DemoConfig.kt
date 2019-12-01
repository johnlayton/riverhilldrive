package au.com.mebank.service.config

import au.com.mebank.service.service.DemoServicePortImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import javax.xml.ws.soap.SOAPBinding
import javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING



@Configuration
class DemoConfig
@Autowired
constructor(
        val bus: Bus
) {


//@Configuration
//class DemoConfig {
//
//    @Autowired
//    private val bus: Bus? = null

    @Bean
    fun endpoint(): Endpoint {
        val endpoint = EndpointImpl(bus, DemoServicePortImpl(), SOAPBinding.SOAP12HTTP_BINDING)
        endpoint.publish("/demo")
        return endpoint
    }

//    @Bean
//    fun endpoint(): Endpoint {
//        val endpointImplementation = buildEndpointImplementation(bus)
//
////        if (StringUtils.isNotBlank(wsdlPath) && StringUtils.isNotBlank(serviceHost)) {
////            val fullEndpointUrl = HTTPS_PROTOCOL + serviceHost + wsdlPath
////            log.debug("Overriding published endpoint URL to be: {}", fullEndpointUrl)
////            endpointImplementation.publishedEndpointUrl = fullEndpointUrl
////        }
//
////        val xmlDeclarationInterceptor = XmlDeclarationInterceptor()
////
////        endpointImplementation.outInterceptors.add(xmlDeclarationInterceptor)
////        endpointImplementation.outFaultInterceptors.add(xmlDeclarationInterceptor)
//
//        endpointImplementation.publish("/demo")
//        return endpointImplementation
//    }
//
//    protected fun buildEndpointImplementation(bus: Bus): EndpointImpl {
//        return EndpointImpl(this.bus, getEndpoint(), SOAPBinding.SOAP12HTTP_BINDING)
//    }
}
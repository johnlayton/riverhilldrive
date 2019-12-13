package au.com.mebank.service.config

import au.com.mebank.service.service.DemoServicePortImpl
import org.apache.cxf.Bus
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.ext.logging.LoggingFeature
import org.apache.cxf.jaxws.EndpointImpl
import org.apache.cxf.transport.servlet.CXFServlet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath
import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import javax.jws.soap.SOAPBinding
import javax.xml.ws.Endpoint


@Configuration
class DemoConfig
@Autowired
constructor(
//        val bus: Bus
) {

    @Bean
    fun dispatcherServlet(): ServletRegistrationBean<CXFServlet?>? {
        return ServletRegistrationBean(CXFServlet(), "/soap-api/*")
    }

    @Bean
    @Primary
    fun dispatcherServletPathProvider(): DispatcherServletPath? {
        return DispatcherServletPath { "" }
    }

    @Bean(name = [Bus.DEFAULT_BUS_ID])
    fun springBus(loggingFeature: LoggingFeature?): SpringBus? {
        val cxfBus = SpringBus()
        cxfBus.features.add(loggingFeature)
        return cxfBus
    }

    @Bean
    fun loggingFeature(): LoggingFeature? {
        val loggingFeature = LoggingFeature()
        loggingFeature.setPrettyLogging(true)
        return loggingFeature
    }

    @Bean
    fun endpoint(bus: Bus): Endpoint {
        val endpoint = EndpointImpl(bus, DemoServicePortImpl())
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
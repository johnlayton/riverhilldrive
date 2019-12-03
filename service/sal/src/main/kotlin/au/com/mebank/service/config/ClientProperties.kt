package au.com.mebank.service.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

//@EnableConfigurationProperties(ClientProperties::class)
//@ConfigurationProperties("service")
//@Validated
//@Component
//class ClientProperties
//@Autowired
//constructor(
//        @NotNull
//        @Pattern(regexp = ".*")
//        val address : String
//)

//@ConstructorBinding
//@EnableConfigurationProperties(ClientProperties::class)
//@ConfigurationProperties(prefix = "client")
//data class ClientProperties(
//        val address: String
//)


//    /**
//     * Path that serves as the base URI for the services.
//     */
//    private String path = "/services";
//
//    private final Servlet servlet = new Servlet();
//
//    @NotNull
//    @Pattern()
//    public String getPath() {
//        return this.path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public Servlet getServlet() {
//        return this.servlet;
//    }
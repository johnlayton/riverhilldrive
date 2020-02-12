package au.com.mebank.soap.service.sal.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import javax.validation.Valid

@ConstructorBinding
@ConfigurationProperties(prefix = "service")
data class ClientProperties(
    @Valid val address: String
)

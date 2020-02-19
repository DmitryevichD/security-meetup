package by.dm13y.examples.security.config


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "security")
data class UserProperties(
    val users: List<UserInfo> = mutableListOf()
)

data class UserInfo(
    var name: String? = null,
    var password: String? = null,
    var role: Roles? = null
)

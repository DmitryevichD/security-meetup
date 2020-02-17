package by.dm13y.example.securityexample.config.auth2

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore

@Configuration
@EnableResourceServer
class ResourceServer(
    private val tokenStore: TokenStore
): ResourceServerConfigurerAdapter() {
    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId("rest_service").tokenStore(tokenStore)
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/**").permitAll()

    }
}

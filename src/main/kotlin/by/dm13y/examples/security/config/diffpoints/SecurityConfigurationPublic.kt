package by.dm13y.examples.security.config.diffpoints

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Profile("multiply-auth-point")
@Configuration
@Order(4)
class SecurityConfigurationPublic() : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http
            .antMatcher("/api/v1/public/info")
            .authorizeRequests()
            .anyRequest()
            .permitAll()
    }
}

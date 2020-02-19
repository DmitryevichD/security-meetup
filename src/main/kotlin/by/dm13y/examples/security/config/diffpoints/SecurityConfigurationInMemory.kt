package by.dm13y.examples.security.config.diffpoints

import by.dm13y.examples.security.config.UserProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint

@Profile("multiply-auth-point")
@Configuration
@Order(1)
class SecurityConfigurationInMemory(
    private val userProperties: UserProperties,
    private val userCustomDetailServiceImpl: UserDetailsService
) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder) {

        userProperties.users
            .forEach {
                auth.inMemoryAuthentication()
                    .withUser(it.name)
                    .password(passwordEncoder().encode(it.name))
                    .roles(it.role!!.name)
            }
        auth.userDetailsService(userCustomDetailServiceImpl).passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http
            .antMatcher("/api/v1/info")
            .authorizeRequests().anyRequest().authenticated()
            .and()
            .httpBasic()
            .authenticationEntryPoint(authenticationEntryPoint())
    }

    @Bean
    fun authenticationEntryPoint(): AuthenticationEntryPoint? {
        val entryPoint = BasicAuthenticationEntryPoint()
        entryPoint.realmName = "user realm"
        return entryPoint
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return object : PasswordEncoder {
            override fun encode(rawPassword: CharSequence): String {
                return rawPassword.toString()
            }

            override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
                return rawPassword.toString() == encodedPassword
            }
        }
    }
}

package by.dm13y.examples.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
class SecurityConfiguration(
    private val userProperties: UserProperties
) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder) {

        userProperties.users
            .forEach {
                auth.inMemoryAuthentication()
                    .withUser(it.name)
                    .password(passwordEncoder()!!.encode(it.name))
                    .roles(it.role!!.name)
            }
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/api/v1/public/**").permitAll() //ВАЖНО СОБЛЮДАТЬ ПОРЯДОК ОТ ЧАСТНОГО К ОБЩЕМУ
            .antMatchers("/api/**").authenticated()
            .and()
            .formLogin()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
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

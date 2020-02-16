package by.dm13y.examples.security.config

import by.dm13y.examples.security.config.jwt.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
class SecurityConfiguration(
    private val userProperties: UserProperties,
    private val userCustomDetailServiceImpl: UserDetailsService,
    private val ldapUserMapper: LdapUserDetailsMapper,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder) {

        userProperties.users
            .forEach {
                auth.inMemoryAuthentication()
                    .withUser(it.name)
                    .password(passwordEncoder()!!.encode(it.name))
                    .roles(it.role!!.name)
            }

        auth.userDetailsService(userCustomDetailServiceImpl).passwordEncoder(passwordEncoder())

        //ldap attributes https://gerardnico.com/security/ldap/attribute
        auth.ldapAuthentication()
            .userDnPatterns("uid={0},ou=people")
            .groupSearchBase("ou=groups")
            .userDetailsContextMapper(ldapUserMapper)
            .contextSource()
            .url("ldap://localhost:8389/dc=springframework,dc=org")
            .and()
            .passwordCompare()
            .passwordEncoder(BCryptPasswordEncoder())
            .passwordAttribute("userPassword")

    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/api/v1/public/**").permitAll() //ВАЖНО СОБЛЮДАТЬ ПОРЯДОК ОТ ЧАСТНОГО К ОБЩЕМУ
            .antMatchers("/api/**").authenticated()
            .and()
            .httpBasic()
            .and()
            .formLogin()
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)


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

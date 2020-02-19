package by.dm13y.examples.security.config.diffpoints

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper

@Profile("multiply-auth-point")
@Configuration
@Order(2)
class SecurityConfigurationOther(
    private val ldapUserMapper: LdapUserDetailsMapper
) : WebSecurityConfigurerAdapter() {
    override fun configure(auth: AuthenticationManagerBuilder) {

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
        http
            .antMatcher("/api/v1/admin/info**")
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .httpBasic()
                //or
//            .and()
//            .exceptionHandling()
//            .defaultAuthenticationEntryPointFor(
//                LoginUrlAuthenticationEntryPoint("/your_login_page"),
//                AntPathRequestMatcher("/api/v1/admin/info")
//            ).and().formLogin()

    }
}

package by.dm13y.example.securityexample.config.auth2

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig(
    private val tokenEnhancer: CustomTokenEnhancer,
    private val accessTokenConverter: JwtAccessTokenConverter,
    private val tokenStore: TokenStore,
    private val authenticationManager: AuthenticationManager
) : AuthorizationServerConfigurerAdapter() {

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore)
            .reuseRefreshTokens(false)
            .accessTokenConverter(accessTokenConverter)
            .authenticationManager(authenticationManager)
            .tokenEnhancer(getTokenEnhancerChain())
            .pathMapping("/oauth/token", "/api/v1/auth/token")
            .pathMapping("/oauth/check_token", "/api/v1/auth/check_token")
    }

    private fun getTokenEnhancerChain(): TokenEnhancerChain? {
        val tokenEnhancerChain = TokenEnhancerChain()
        tokenEnhancerChain.setTokenEnhancers(listOf(tokenEnhancer, accessTokenConverter))
        return tokenEnhancerChain
    }


    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients
            .inMemory()
            .withClient("clientId")
            .authorizedGrantTypes("password", "refresh_token")
            .authorities("USER")
            .scopes("read", "write")
            .resourceIds("rest_service")
            .secret("secret")
            .accessTokenValiditySeconds(24 * 365 * 60 * 60)
    }
}

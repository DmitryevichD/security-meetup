package by.dm13y.examples.security.config.auth2

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import org.springframework.stereotype.Component

@Component
//Add custom info to token
class CustomTokenEnhancer: TokenEnhancer {
    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = buildAdditionInformation()
        return accessToken
    }

    private fun buildAdditionInformation(): Map<String, Any>? {
        val additionInformation: MutableMap<String, Any> = HashMap()
        additionInformation["sub"] = "some payload in json format"
        return additionInformation
    }
}

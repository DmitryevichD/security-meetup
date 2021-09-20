package by.dm13y.examples.security.config.jwt

import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED

class JwtAuthenticationFilter(
    private val requestMatcher: RequestMatcher,
    private val jwtTokenProvider: JwtTokenProvider
): AbstractAuthenticationProcessingFilter(requestMatcher) {


    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {
        val jwtToken = extractJwtToken(request)
        return jwtTokenProvider.getAuthentication(jwtToken);
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        val context = SecurityContextHolder.createEmptyContext()
        context.setAuthentication(authResult)

    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        failed: AuthenticationException?
    ) {
        SecurityContextHolder.clearContext()
        response?.setStatus(SC_UNAUTHORIZED)
    }

    private fun extractJwtToken(request: HttpServletRequest): String {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        return when (authHeader?.contains(OAuth2AccessToken.BEARER_TYPE)) {
            true -> authHeader.substring(OAuth2AccessToken.BEARER_TYPE.length).trim()
            else -> throw JwtAuthenticationException("Jwt token is null")
        }
    }

    class JwtAuthenticationException(msg: String?) : AuthenticationException(msg) {
    }

}

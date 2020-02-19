package by.dm13y.examples.security.config.jwt

import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        extractJwtToken(request)?.also { authenticateWithJwt(it) }
        filterChain.doFilter(request, response)
    }

    private fun authenticateWithJwt(jwtToken: String) {
        try {
            SecurityContextHolder.clearContext()
            val authentication = jwtTokenProvider.getAuthentication(jwtToken)
            SecurityContextHolder.getContext().setAuthentication(authentication)
        } catch (ex: Exception) {
            SecurityContextHolder.clearContext()
            throw ex
        }
    }

    private fun extractJwtToken(request: HttpServletRequest): String? {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        return when (authHeader?.contains(OAuth2AccessToken.BEARER_TYPE)) {
            true -> authHeader.substring(OAuth2AccessToken.BEARER_TYPE.length).trim()
            else -> null
        }
    }
}
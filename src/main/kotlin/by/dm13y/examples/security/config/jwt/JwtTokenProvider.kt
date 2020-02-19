package by.dm13y.examples.security.config.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.*

@Suppress("UNCHECKED_CAST")
@Component
class JwtTokenProvider {
    private val signingKey: String = "some_signing_key"

    fun getAuthentication(jwt: String): Authentication {
        val claims = getJwtClaims(jwt)
        val userName = claims["user_name"] as String
        val authorities = extractAuthorities(claims["authorities"] as List<String>)

        val principal = User(userName, UUID.randomUUID().toString(), authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)

    }

    private fun extractAuthorities(sourceAuthority: List<String>): List<GrantedAuthority>? {
        return sourceAuthority
            .map { SimpleGrantedAuthority(it) }
            .toList()

    }

    private fun getJwtClaims(jwt: String): Claims {
        return Jwts.parser().setSigningKey(signingKey.toByteArray()).parseClaimsJws(jwt).body
    }
}

package by.dm13y.examples.security.config.custom

import by.dm13y.example.securityexample.config.UserDetailInfo
import org.springframework.context.annotation.Profile
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserCustomDetailServiceImpl: UserDetailsService {
    private val userMap = mapOf("jdbcUser" to "USER")

    override fun loadUserByUsername(username: String?): UserDetails {
        if (!userMap.keys.contains(username)) {
            throw UsernameNotFoundException("User $username is not found.")
        }

        return UserDetailInfo(username, userMap[username])
    }
}

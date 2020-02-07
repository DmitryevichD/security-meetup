package by.dm13y.examples.security.service.impl

import by.dm13y.examples.security.model.AdminInfoDto
import by.dm13y.examples.security.model.PublicInfoDto
import by.dm13y.examples.security.model.UserInfoDto
import by.dm13y.examples.security.service.InfoGeneratorService
import org.springframework.stereotype.Service
import java.util.stream.IntStream
import kotlin.random.Random
import kotlin.streams.toList

@Service
class InfoGeneratorServiceImpl : InfoGeneratorService {
    private val countDto = 10L

    override fun generatePublicInfo(): List<PublicInfoDto> {
        return IntStream.generate { Random.nextInt() }
                .limit(countDto)
                .mapToObj { num -> PublicInfoDto("Public info $num") }
                .toList()
    }

    override fun generatePrivateAdminInfo(): List<AdminInfoDto> {
        return IntStream.generate { Random.nextInt() }
                .limit(countDto)
                .mapToObj { num -> AdminInfoDto("Private common info $num") }
                .toList()
    }

    override fun generatePrivateLdapInfo(): List<UserInfoDto> {
        return IntStream.generate { Random.nextInt() }
                .limit(countDto)
                .mapToObj { num -> UserInfoDto("Private Ldap info $num") }
                .toList()
    }
}

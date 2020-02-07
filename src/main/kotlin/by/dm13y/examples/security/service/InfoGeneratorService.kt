package by.dm13y.examples.security.service

import by.dm13y.examples.security.model.AdminInfoDto
import by.dm13y.examples.security.model.PublicInfoDto
import by.dm13y.examples.security.model.UserInfoDto

interface InfoGeneratorService {
    fun generatePublicInfo(): List<PublicInfoDto>
    fun generatePrivateAdminInfo(): List<AdminInfoDto>
    fun generatePrivateLdapInfo(): List<UserInfoDto>
}

package by.dm13y.examples.security.controller

import by.dm13y.examples.security.model.UserInfoDto
import by.dm13y.examples.security.service.InfoGeneratorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/info")
class PrivateCommonController(private val infoGeneratorService: InfoGeneratorService) {
    @GetMapping
    fun privateLdapInfo(): List<UserInfoDto> {
        return infoGeneratorService.generatePrivateLdapInfo()
    }
}

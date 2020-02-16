package by.dm13y.examples.security.controller

import by.dm13y.examples.security.model.AdminInfoDto
import by.dm13y.examples.security.service.InfoGeneratorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("/api/v1/jwt/info")
class PrivateJwtController(private val infoGeneratorService: InfoGeneratorService) {
    @GetMapping
    @RolesAllowed("JWT")
    fun publicInfo(): List<AdminInfoDto> {
        return infoGeneratorService.generatePrivateAdminInfo()
    }
}

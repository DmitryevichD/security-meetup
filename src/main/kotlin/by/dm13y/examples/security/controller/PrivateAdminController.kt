package by.dm13y.examples.security.controller

import by.dm13y.examples.security.model.AdminInfoDto
import by.dm13y.examples.security.service.InfoGeneratorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/admin/info")
class PrivateAdminController(private val infoGeneratorService: InfoGeneratorService) {
    @GetMapping
    fun publicInfo(): List<AdminInfoDto> {
        return infoGeneratorService.generatePrivateAdminInfo()
    }
}

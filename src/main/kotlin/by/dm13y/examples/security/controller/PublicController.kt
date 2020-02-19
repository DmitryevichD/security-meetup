package by.dm13y.examples.security.controller

import by.dm13y.examples.security.model.PublicInfoDto
import by.dm13y.examples.security.service.InfoGeneratorService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/public/info")
class PublicController(private val infoGeneratorService: InfoGeneratorService) {
    @GetMapping
    fun publicInfo(): List<PublicInfoDto> {
        return infoGeneratorService.generatePublicInfo()
    }
}

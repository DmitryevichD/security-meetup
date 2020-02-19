package by.dm13y.examples.security

import by.dm13y.examples.security.config.UserProperties
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class SecurityExampleApplicationTests {
    @Autowired
    lateinit var userProperties: UserProperties

    @Test
    fun contextLoads() {
    }

}

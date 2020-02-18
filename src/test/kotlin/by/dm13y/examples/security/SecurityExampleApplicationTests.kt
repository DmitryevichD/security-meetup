package by.dm13y.examples.security

import by.dm13y.examples.security.config.UserProperties
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class SecurityExampleApplicationTests {
    @Autowired
    lateinit var userProperties: UserProperties

    @Test
    fun contextLoads() {
    }

}

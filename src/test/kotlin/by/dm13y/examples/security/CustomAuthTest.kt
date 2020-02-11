package by.dm13y.examples.security

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class CustomAuthTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `Correct auth and correct grant with custom detail service`() {
        mockMvc.get("/api/v1/info") {
            header("Authorization", "Basic " + Base64.getEncoder().encodeToString("jdbcUser:jdbcUser".toByteArray()))
        }.andExpect { status { isOk } }
    }

    @Test
    fun `Correct auth and incorrect grant with custom detail service`() {
        mockMvc.get("/api/v1/admin/info") {
            header("Authorization", "Basic " + Base64.getEncoder().encodeToString("jdbcUser:jdbcUser".toByteArray()))
        }.andExpect { status { isForbidden } }
    }

    @Test
    fun `Incorrect auth grant with custom detail service`() {
        mockMvc.get("/api/v1/admin/info") {
            header("Authorization", "Basic " + Base64.getEncoder().encodeToString("jdbcUs:jdbcUs".toByteArray()))
        }.andExpect { status { isUnauthorized } }
    }
}

package by.dm13y.examples.security

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.*

@ActiveProfiles("multiply-auth-point")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@DirtiesContext()
class DiffBasicAuthTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `access to public controller`() {
        mockMvc.get("/api/v1/public/info") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk } }
    }


    @Test
    fun `access to inMemory config without basic auth`() {
        mockMvc.get("/api/v1/info") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isUnauthorized } }
    }

    @Test
    fun `access to inMemory config by other config user `() {
        mockMvc.get("/api/v1/info") {
            accept = MediaType.APPLICATION_JSON
            header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ben:benspassword".toByteArray()))
        }.andExpect { status { isUnauthorized } }
    }

    @Test
    fun `access to inMemory config by inMemory config user `() {
        mockMvc.get("/api/v1/info") {
            accept = MediaType.APPLICATION_JSON
            header("Authorization", "Basic " + Base64.getEncoder().encodeToString("user:user".toByteArray()))
        }.andExpect { status { isOk } }
    }

    @Test
    fun `access to other config without basic auth`() {
        mockMvc.get("/api/v1/admin/info") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isUnauthorized } }
    }

    @Test
    fun `access to other config by other config user `() {
        mockMvc.get("/api/v1/admin/info") {
            accept = MediaType.APPLICATION_JSON
            header("Authorization", "Basic " + Base64.getEncoder().encodeToString("ben:benspassword".toByteArray()))
        }.andExpect { status { isOk } }
    }

    @Test
    fun `access to other config by inMemory config user `() {
        mockMvc.get("/api/v1/admin/info") {
            accept = MediaType.APPLICATION_JSON
            header("Authorization", "Basic " + Base64.getEncoder().encodeToString("user:user".toByteArray()))
        }.andExpect { status { isUnauthorized } }
    }

}

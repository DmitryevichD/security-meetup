package by.dm13y.examples.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
class Auth2Test {
    @Autowired
    lateinit var mockMvc: MockMvc
    val mapper = jacksonObjectMapper()

    @Test
    fun `Auth2 authentication server test`() {
        mockMvc.post("/api/v1/auth/token") {
            param("grant_type", "password")
            param("username", "admin")
            param("password", "admin")
            header("Authorization", "Basic " + Base64.getEncoder().encodeToString("clientId:secret".toByteArray()))
        }.andExpect { status { isOk } }
    }

    @Test
    fun `Auth2 get resource`() {
        val response = mockMvc.post("/api/v1/auth/token") {
            param("grant_type", "password")
            param("username", "admin")
            param("password", "admin")
            header("Authorization", "Basic " + Base64.getEncoder().encodeToString("clientId:secret".toByteArray()))
        }.andReturn()
            .response
            .contentAsString

        val authTokenInfo: Map<String, String> = mapper.readValue(response)
        val accessToken = authTokenInfo["access_token"]

        mockMvc.get("/api/v1/info") {
            accept = MediaType.APPLICATION_JSON
            header("Authorization", "Bearer $accessToken")
        }.andExpect { status { isOk } }
    }
}
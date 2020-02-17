package by.dm13y.examples.security

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@Disabled("Conflict with auth2 resource server test. For use this test commit ResourceServer")
class BasicAuthTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `access to public controller`() {
        mockMvc.get("/api/v1/public/info") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk } }
    }

    @Test
    fun `access to common private controller without authorization`() {
        mockMvc.get("/api/v1/info") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isUnauthorized } }
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `access to common private controller with authorization`() {
        mockMvc.get("/api/v1/info") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk } }
    }

    @Test
    @WithMockUser(username = "user", roles = ["USER"])
    fun `access to admin private controller with role user`() {
        mockMvc.get("/api/v1/admin/info") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isForbidden } }
    }

    @Test
    @WithMockUser(username = "admin", roles = ["ADMIN"])
    fun `access to admin private controller with role admin`() {
        mockMvc.get("/api/v1/admin/info") {
            accept = MediaType.APPLICATION_JSON
        }.andExpect { status { isOk } }
    }
}

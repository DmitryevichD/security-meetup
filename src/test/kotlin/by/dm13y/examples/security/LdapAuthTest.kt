package by.dm13y.examples.security

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc

@ActiveProfiles("basic-test")
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension::class)
@DirtiesContext
class LdapAuthTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `LDAP authentication`() {
        val login: SecurityMockMvcRequestBuilders.FormLoginRequestBuilder = formLogin()
            .user("ben")
            .password("benspassword")

        mockMvc.perform(login)
            .andExpect(authenticated().withUsername("ldap")) //Авторизовали пользотеля ben (AD) как ldap (Current)
    }
}

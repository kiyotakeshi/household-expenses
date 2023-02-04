package com.example.householdExpenses.integration

import com.example.householdExpenses.core.security.SecurityConfig
import com.example.householdExpenses.domain.Fixtures
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

/**
 * @ref https://github.com/spring-projects/spring-security-samples/blob/main/servlet/spring-boot/java/jwt/login/src/test/java/example/web/HelloControllerTests.java
 * [com.example.householdExpenses.presentation.api.expense.ExpenseController]
 * @author kiyota
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig::class)
internal class CategoryIntegrationTests @Autowired constructor(
    // RestTemplate を使用することも検討
    val mockMvc: MockMvc,
) {
    private val requestPath = "/api/categories";

    @Test
    internal fun `get categories with HttpBasicAuth`() {
        mockMvc.get(requestPath) {
            with(httpBasic("user1@example.com", "1qazxsw2"))
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
                // ID は DB にて採番されるため
                jsonPath("$[0].id") { value(1) }
                jsonPath("$[0].name") { value(Fixtures.CategoryA().name) }
                jsonPath("$[0].rank") { value(Fixtures.CategoryA().rank) }
                jsonPath("$[1].name") { value(Fixtures.CategoryB().name) }
            }
    }

    @Test
    @Throws(Exception::class)
    fun `should return 401 when unauthenticated`() {
        mockMvc.get(requestPath) {
            with(httpBasic("no-exist-user@example.com", "password"))
        }
            .andExpect {
                status {
                    isUnauthorized()
                }
            }
    }
}
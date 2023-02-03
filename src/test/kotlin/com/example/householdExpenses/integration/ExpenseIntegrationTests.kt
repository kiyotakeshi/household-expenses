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
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

/**
 * [com.example.householdExpenses.presentation.api.expense.ExpenseRestController]
 * @author kiyota
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig::class)
internal class ExpenseIntegrationTests @Autowired constructor(
    val mockMvc: MockMvc,
) {
    val requestPath = "/api/expenses";

    @Test
    internal fun `get expenses with HttpBasicAuth`() {
        mockMvc.get(requestPath) {
            with(SecurityMockMvcRequestPostProcessors.httpBasic("user1@example.com", "1qazxsw2"))
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
                // ID は DB にて採番されるため
                jsonPath("$[0].id") { value(1) }
                jsonPath("$[0].name") { value(Fixtures.ExpenseA().name) }
                jsonPath("$[0].price") { value(Fixtures.ExpenseA().price) }
                jsonPath("$[0].memo") { value(Fixtures.ExpenseA().memo) }
                jsonPath("$[0].date") { value(Fixtures.ExpenseA().date.toString()) }
                jsonPath("$[0].category_name") { value(Fixtures.ExpenseA().category.name) }
                jsonPath("$[1].name") { value(Fixtures.ExpenseB().name) }
                jsonPath("$[1].category_name") { value(Fixtures.ExpenseB().category.name) }
                jsonPath("$[2].name") { value(Fixtures.ExpenseC().name) }
            }
    }

    @Test
    @Throws(Exception::class)
    fun `should return 401 when unauthenticated`() {
        mockMvc.get(requestPath) {
            with(SecurityMockMvcRequestPostProcessors.httpBasic("no-exist-user@example.com", "password"))
        }
            .andExpect {
                status {
                    isUnauthorized()
                }
            }
    }
}
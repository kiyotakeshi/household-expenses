package com.example.householdExpenses.integration

import com.example.householdExpenses.IntegrationTestUtils
import com.example.householdExpenses.core.security.SecurityConfig
import com.example.householdExpenses.domain.Fixtures
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

/**
 * [com.example.householdExpenses.presentation.api.expense.ExpenseController]
 * @author kiyota
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig::class)
internal class ExpenseIntegrationTests @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
){
    val requestBasePath = "/api/expenses";

    @Test
    internal fun `get expenses with Basic Authentication`() {
        mockMvc.get(requestBasePath) {
            with(SecurityMockMvcRequestPostProcessors.httpBasic("user1@example.com", "1qazxsw2"))
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
                // ID は DB にて採番されるため
                jsonPath("$[0].id") { isNumber() }
                jsonPath("$[0].name") { value(Fixtures.ExpenseA().name) }
                jsonPath("$[0].price") { value(Fixtures.ExpenseA().price) }
                jsonPath("$[0].memo") { value(Fixtures.ExpenseA().memo) }
                jsonPath("$[0].date") { value(Fixtures.ExpenseA().date.toString()) }
                jsonPath("$[0].category_name") { value(Fixtures.ExpenseA().category.name) }
                jsonPath("$[1].name") { value(Fixtures.ExpenseB().name) }
                jsonPath("$[1].category_name") { value(Fixtures.ExpenseB().category.name) }
            }
    }

    @Test
    internal fun `get categories with JWT`() {
         val token = IntegrationTestUtils.fetchJwt(mockMvc)

        mockMvc.get(requestBasePath) {
            header("Authorization", "Bearer $token")
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
            }
    }

    @Test
    @Throws(Exception::class)
    fun `should return 401 when unauthenticated`() {
        mockMvc.get(requestBasePath) {
            with(SecurityMockMvcRequestPostProcessors.httpBasic("no-exist-user@example.com", "password"))
        }
            .andExpect {
                status {
                    isUnauthorized()
                }
            }
    }

    @Test
    @DirtiesContext
    internal fun `add expense with JWT`() {
        val token = IntegrationTestUtils.fetchJwt(mockMvc)
         val requestBody = objectMapper.writeValueAsString(Fixtures.expenseRequestDtoA())

        mockMvc.post("$requestBasePath/members/1") {
            contentType = MediaType.APPLICATION_JSON
            content = requestBody
            header("Authorization", "Bearer $token")
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    jsonPath("$.id") { isNumber() }
                    jsonPath("$.category_name") { value("食費") }
                    jsonPath("$.name") { value(Fixtures.expenseRequestDtoA().name) }
                    jsonPath("$.price") { value(Fixtures.expenseRequestDtoA().price) }
                    jsonPath("$.memo") { value(Fixtures.expenseRequestDtoA().memo) }
                    jsonPath("$.date") { value(Fixtures.expenseRequestDtoA().date.toString()) }
                    jsonPath("$.repeatable_month") { value(Fixtures.expenseRequestDtoA().repeatableMonth) }
                    jsonPath("$.repeatable_count") { value(Fixtures.expenseRequestDtoA().repeatableCount) }
                }
            }
    }
}
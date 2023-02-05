package com.example.householdExpenses.integration

import com.example.householdExpenses.IntegrationTestUtils
import com.example.householdExpenses.core.security.SecurityConfig
import com.example.householdExpenses.domain.Fixtures
import com.example.householdExpenses.model.MemberRequestDto
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.util.Base64Utils
import org.springframework.web.servlet.function.RequestPredicates.contentType
import java.time.LocalDate

/**
 * @ref https://github.com/spring-projects/spring-security-samples/blob/main/servlet/spring-boot/java/jwt/login/src/test/java/example/web/HelloControllerTests.java
 * [com.example.householdExpenses.presentation.api.expense.ExpenseController]
 * @author kiyota
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(SecurityConfig::class)
internal class MemberIntegrationTests @Autowired constructor(
    // RestTemplate を使用することも検討
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {
    private val requestPath = "/api/members";

    // @ref https://github.com/spring-projects/spring-security-samples/blob/main/servlet/spring-boot/java/jwt/login/src/test/java/example/web/HelloControllerTests.java#L47
    @Test
    @DirtiesContext
    internal fun `add member with JWT`() {
        val token = IntegrationTestUtils.fetchJwt(mockMvc)
        val requestBody = objectMapper.writeValueAsString(Fixtures.memberRequestDtoA())

        mockMvc.post(requestPath) {
            contentType = MediaType.APPLICATION_JSON
            content = requestBody
            header("Authorization", "Bearer $token")
        }
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    jsonPath("$.id") { isNumber() }
                    jsonPath("$.name") { value(Fixtures.memberRequestDtoA().name) }
                    jsonPath("$.birthday") { value(Fixtures.memberRequestDtoA().birthday.toString()) }
                }
            }
    }
}
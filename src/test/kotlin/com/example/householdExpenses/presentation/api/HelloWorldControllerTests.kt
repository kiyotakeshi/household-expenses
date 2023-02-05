package com.example.householdExpenses.presentation.api

import com.example.householdExpenses.core.security.SecurityConfig
import com.example.householdExpenses.presentation.api.HelloWorldController
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

/**
 * https://kotlinlang.org/docs/kotlin-doc.html#links-to-elements
 * [com.example.householdExpenses.presentation.api.HelloWorldController]
 *
 * MVC slice test
 * https://spring.pleiades.io/spring-boot/docs/current/reference/html/features.html#features.testing.spring-boot-applications.spring-mvc-tests
 * https://spring.pleiades.io/spring-boot/docs/current/reference/html/test-auto-configuration.html#appendix.test-auto-configuration
 * @author kiyota
 */
@WebMvcTest(HelloWorldController::class)
@AutoConfigureMockMvc
@Import(SecurityConfig::class)
internal class
HelloWorldControllerTests(
    @Autowired
    val mockMvc: MockMvc
) {
    @Test
    @DisplayName("/api/hello は public access ができる")
    internal fun helloWorld() {
        mockMvc.get("/api/hello")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType("text/plain;charset=UTF-8")
                    string("hello world")
                }
            }
    }
}
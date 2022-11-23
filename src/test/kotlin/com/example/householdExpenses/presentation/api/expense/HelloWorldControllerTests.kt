package com.example.householdExpenses.presentation.api.expense

import org.apache.tomcat.util.buf.Utf8Encoder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

/**
 * https://kotlinlang.org/docs/kotlin-doc.html#links-to-elements
 * [com.example.householdExpenses.presentation.api.expense.HelloWorldController]
 * @author kiyota
 */
@SpringBootTest
@AutoConfigureMockMvc
internal class HelloWorldControllerTests @Autowired constructor(
    val mockMvc: MockMvc
) {
    @Test
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
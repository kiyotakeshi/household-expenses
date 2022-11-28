package com.example.householdExpenses.integration

import com.example.householdExpenses.domain.Fixtures
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate

/**
 * [com.example.householdExpenses.presentation.api.expense.ExpenseRestController]
 * @author kiyota
 */
@SpringBootTest
@AutoConfigureMockMvc
internal class CategoryIntegrationTests @Autowired constructor(
    val mockMvc: MockMvc
) {
    @Test
    internal fun getCategories(){
        mockMvc.get("/api/categories")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
                // ID は DB にて採番されるため
                jsonPath("$[0].id") { value(1)}
                jsonPath("$[0].name") { value(Fixtures.CategoryA().name)}
                jsonPath("$[0].rank") { value(Fixtures.CategoryA().rank)}
                jsonPath("$[1].name") { value(Fixtures.CategoryB().name)}
            }
    }
}
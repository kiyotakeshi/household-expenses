package com.example.householdExpenses.integration

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
internal class ExpenseIntegrationTests @Autowired constructor(
    val mockMvc: MockMvc
) {
    @Test
    internal fun getExpenses(){
        mockMvc.get("/api/expenses")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
                jsonPath("$[0].id") { value(1)}
                jsonPath("$[0].name") { value("粉ミルク")}
                jsonPath("$[0].price") { value(500)}
                jsonPath("$[0].memo") { value("200gの缶のもの")}
                jsonPath("$[0].date") { value(LocalDate.of(2022,11,23).toString())}
            }
    }
}
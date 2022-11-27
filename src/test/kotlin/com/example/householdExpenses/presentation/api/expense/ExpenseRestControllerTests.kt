package com.example.householdExpenses.presentation.api.expense

import com.example.householdExpenses.domain.Fixtures
import com.example.householdExpenses.usecase.expense.GetExpensesUsecase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDate

/**
 * [com.example.householdExpenses.presentation.api.expense.ExpenseRestController]
 * @author kiyota
 */
@WebMvcTest(ExpenseRestController::class)
@AutoConfigureMockMvc
internal class ExpenseRestControllerTests(
    @Autowired
    val mockMvc: MockMvc,
) {

    // @ref https://medium.com/@darych90/kotlin-spring-boot-mockk-6d1c1a6463ac
    // or use [SpringMockK](https://github.com/Ninja-Squad/springmockk) introduced in official tutorial
    // https://spring.io/guides/tutorials/spring-boot-kotlin/#_exposing_http_api
    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        // fun getExpensesUsecase(): GetExpensesUsecase = mockk()
        fun getExpensesUsecase() = mockk<GetExpensesUsecase>()
    }

    @Autowired
    lateinit var getExpensesUsecase: GetExpensesUsecase

    @Test
    fun getExpenses() {
        every { getExpensesUsecase.getExpenses() } returns listOf(Fixtures.ExpenseA(), Fixtures.ExpenseB(), Fixtures.ExpenseC())

        mockMvc.get("/api/expenses")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
                jsonPath("$") { isArray() }
                jsonPath("$[0].name") { value("粉ミルク")}
                jsonPath("$[0].price") { value(500)}
                jsonPath("$[0].memo") { value("200gの缶のもの")}
                jsonPath("$[0].category_name") { value("食費")}
                jsonPath("$[0].date") { value(LocalDate.of(2022,11,23).toString())}
                jsonPath("$[1].name") { value("おしゃぶり")}
                jsonPath("$[1].category_name") { value("消耗品")}
                jsonPath("$[2].name") { value("おやつ")}
            }

        verify(exactly = 1) { getExpensesUsecase.getExpenses() }
    }
}
package com.example.householdExpenses.presentation.api.expense

import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.domain.expense.ExpenseRepository
import com.example.householdExpenses.usecase.expense.GetExpensesUsecase
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
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
        val expense1 = Expense(
            id = 1,
            category_id = 1,
            member_id = 1,
            name = "粉ミルク",
            price = 500,
            memo = "200gの缶のもの",
            date = LocalDate.of(2022, 11, 23),
            repeatable_month = 1,
            repeatable_count = 1
        )
        val expense2 = Expense(2, 5, 1, "おしゃぶり", 300, null, LocalDate.of(2022, 11, 23), 0, 0)

        every { getExpensesUsecase.getExpenses() } returns listOf(expense1, expense2)

        mockMvc.get("/api/expenses")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                }
                jsonPath("$") { isArray() }
                jsonPath("$[0].id") { value(1)}
                jsonPath("$[0].name") { value("粉ミルク")}
                jsonPath("$[0].price") { value(500)}
                jsonPath("$[0].memo") { value("200gの缶のもの")}
                jsonPath("$[0].date") { value(LocalDate.of(2022,11,23).toString())}
                jsonPath("$[1].name") { value("おしゃぶり")}
            }

        verify(exactly = 1) { getExpensesUsecase.getExpenses() }
    }
}
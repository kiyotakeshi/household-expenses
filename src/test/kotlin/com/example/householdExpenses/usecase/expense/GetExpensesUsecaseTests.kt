package com.example.householdExpenses.usecase.expense

import com.example.householdExpenses.domain.expense.ExpenseRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * @author kiyota
 */
internal class GetExpensesUsecaseTests {

    private val expenseRepository: ExpenseRepository = mockk(relaxed = true)
    private val sut = GetExpensesUsecase(expenseRepository)

    @Test
    fun `should call its repository to retrieve expenses`() {
        // if you want to specify explicitly,
        // every { expenseRepository.getExpenses() } returns emptyList()
        sut.getExpenses()
        verify(exactly = 1) { expenseRepository.getExpenses() }
    }
}
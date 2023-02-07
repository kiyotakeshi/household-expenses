package com.example.householdExpenses.usecase.expense

import com.example.householdExpenses.domain.expense.ExpenseRepository
import com.example.householdExpenses.domain.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

/**
 * @author kiyota
 */
internal class GetExpensesUsecaseTests {
    private val authentication: Authentication = mockk()
    private val expenseRepository: ExpenseRepository = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk(relaxed = true)
    private val sut = GetExpensesUsecase(expenseRepository, userRepository)

    @Test
    fun `should call its repository to retrieve expenses`() {
        val userName = "user1@example.com"
        every { authentication.name } returns userName
        mockkStatic(SecurityContextHolder::class)
        every { SecurityContextHolder.getContext().authentication } returns authentication

        sut.getExpenses()

        verify(exactly = 1) { userRepository.getUser(any()) }
        verify(exactly = 1) { expenseRepository.getExpenses(any()) }
    }
}
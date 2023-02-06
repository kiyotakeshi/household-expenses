package com.example.householdExpenses.usecase.expense

import com.example.householdExpenses.domain.Fixtures
import com.example.householdExpenses.domain.expense.ExpenseRepository
import com.example.householdExpenses.domain.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.lang.RuntimeException

/**
 * @author kiyota
 */
internal class AddExpensesUsecaseTests {
    private val authentication: Authentication = mockk()
    private val expenseRepository: ExpenseRepository = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk()
    private val sut = AddExpensesUsecase(expenseRepository, userRepository)

    @Test
    fun `should call its repository to add expense`() {
        val userName = "user1@example.com"
        every { authentication.name } returns userName
        mockkStatic(SecurityContextHolder::class)
        every { SecurityContextHolder.getContext().authentication } returns authentication
        every { userRepository.hasMember(any(), any()) } returns true

        val memberId = 1
        val request = Fixtures.expenseRequestDtoA()
        sut.addExpense(memberId, request)

        verify(exactly = 1) { userRepository.hasMember(eq(userName), eq(memberId)) }
        verify(exactly = 1) { expenseRepository.addExpense(eq(memberId), eq(request)) }
    }

    @Test
    fun `should not call its repository to add expense(user doesn't have specified member)`() {
        val userName = "user1@example.com"
        every { authentication.name } returns userName
        mockkStatic(SecurityContextHolder::class)
        every { SecurityContextHolder.getContext().authentication } returns authentication
        every { userRepository.hasMember(any(), any()) } returns false

        val memberId = 1
        val request = Fixtures.expenseRequestDtoA()
        assertThatThrownBy{
            sut.addExpense(memberId, request)
        }.isInstanceOf(RuntimeException::class.java)
            .hasMessageContaining("user(${authentication.name}) doesn't have specified member (id:$memberId)")

        verify(exactly = 0) { expenseRepository.addExpense(any(), any()) }
    }
}
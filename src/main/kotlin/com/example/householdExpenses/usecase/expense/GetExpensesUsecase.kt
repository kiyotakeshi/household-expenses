package com.example.householdExpenses.usecase.expense

import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.domain.expense.ExpenseRepository
import com.example.householdExpenses.domain.user.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * @author kiyota
 */
@Service
class GetExpensesUsecase(
    private val expenseRepository: ExpenseRepository,
    private val userRepository: UserRepository
) {

    fun getExpenses(): List<Expense> {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = userRepository.getUser(authentication.name) ?: throw UsernameNotFoundException("user details not found for the user: ${authentication.name}")
        return this.expenseRepository.getExpenses(user.id!!)
    }
}
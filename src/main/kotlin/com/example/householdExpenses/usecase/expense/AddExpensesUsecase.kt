package com.example.householdExpenses.usecase.expense

import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.domain.expense.ExpenseRepository
import com.example.householdExpenses.domain.user.UserRepository
import com.example.householdExpenses.model.ExpenseRequestDto
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

/**
 * @author kiyota
 */
@Service
class AddExpensesUsecase(
    private val expenseRepository: ExpenseRepository,
    private val userRepository: UserRepository,
) {
    fun addExpense(memberId: Int, request: ExpenseRequestDto): Expense {
        val authentication = SecurityContextHolder.getContext().authentication
        userRepository.hasMember(authentication.name, memberId) || throw RuntimeException("user(${authentication.name}) doesn't have specified member (id:$memberId)")
        return this.expenseRepository.addExpense(memberId,request)
    }
}
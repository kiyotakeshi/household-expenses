package com.example.householdExpenses.usecase.expense

import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.domain.expense.ExpenseRepository
import org.springframework.stereotype.Service

/**
 * @author kiyota
 */
@Service
class GetExpensesUsecase(private val expenseRepository: ExpenseRepository) {

    fun getExpenses(): List<Expense> = this.expenseRepository.getExpenses()
}
package com.example.householdExpenses.domain.expense

import com.example.householdExpenses.model.ExpenseRequestDto

/**
 * @author kiyota
 */
interface ExpenseRepository {
    fun getExpenses(): List<Expense>
    fun getExpense(id:Int): Expense?
    fun addExpense(memberId: Int,request: ExpenseRequestDto): Expense
}
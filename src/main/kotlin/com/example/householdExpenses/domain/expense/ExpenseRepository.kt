package com.example.householdExpenses.domain.expense

/**
 * @author kiyota
 */
interface ExpenseRepository {
    fun getExpenses(): List<Expense>
}
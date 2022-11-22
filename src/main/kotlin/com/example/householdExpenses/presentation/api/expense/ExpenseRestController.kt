package com.example.householdExpenses.presentation.api.expense

import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.usecase.expense.GetExpensesUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author kiyota
 */
@RestController
@RequestMapping("/api/expenses")
class ExpenseRestController(private val getExpensesUsecase: GetExpensesUsecase) {

    @GetMapping
    fun getExpenses(): ResponseEntity<List<Expense>> {
        val expenses: List<Expense> = getExpensesUsecase.getExpenses()
        return ResponseEntity.ok(expenses)
    }
}
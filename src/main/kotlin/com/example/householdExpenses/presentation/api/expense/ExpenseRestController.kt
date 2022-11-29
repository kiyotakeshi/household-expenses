package com.example.householdExpenses.presentation.api.expense

import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.model.ExpenseResponseDto
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
    fun getExpenses(): ResponseEntity<List<ExpenseResponseDto>> {
        val expenses: List<Expense> = getExpensesUsecase.getExpenses()

        val response = expenses.map {
            ExpenseResponseDto(
                id = it.id!!,
                categoryName = it.category.name,
                name = it.name,
                price = it.price,
                memo = it.memo,
                date = it.date,
                repeatableMonth = it.repeatableMonth,
                repeatableCount = it.repeatableCount
            )
        }.toList()

        return ResponseEntity.ok(response)
    }
}
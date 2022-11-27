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
    fun getExpenses(): ResponseEntity<ExpenseListResponseDto> {
        val expenses: List<Expense> = getExpensesUsecase.getExpenses()

        val response = ExpenseListResponseDto(
            expenses.map {
                ExpenseResponseDto(
                    id = it.id,
                    category_name = it.category.name,
                    member_id = it.member_id,
                    name = it.name,
                    price = it.price,
                    memo = it.memo,
                    date = it.date,
                    repeatable_month = it.repeatable_month,
                    repeatable_count = it.repeatable_count
                )
            }.toList()
        )

        return ResponseEntity.ok(response)
    }
}
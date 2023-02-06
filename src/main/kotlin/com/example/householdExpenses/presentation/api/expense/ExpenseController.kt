package com.example.householdExpenses.presentation.api.expense

import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.model.ExpenseRequestDto
import com.example.householdExpenses.model.ExpenseResponseDto
import com.example.householdExpenses.usecase.expense.AddExpensesUsecase
import com.example.householdExpenses.usecase.expense.GetExpensesUsecase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author kiyota
 */
@RestController
@RequestMapping("/api/expenses")
class ExpenseController(
    private val getExpensesUsecase: GetExpensesUsecase,
    private val addExpensesUsecase: AddExpensesUsecase,
) {

    // TODO: user で絞ったものを返す(member でも絞ったほうがいいかも)
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

    @PostMapping("/members/{memberId}")
    fun addExpense(
        @PathVariable memberId: Int,
        @Valid @RequestBody request: ExpenseRequestDto,
    ): ResponseEntity<ExpenseResponseDto> {
        val expense = addExpensesUsecase.addExpense(memberId, request)
        return ResponseEntity.ok(
            ExpenseResponseDto(
                expense.id!!,
                expense.category.name,
                expense.name,
                expense.price,
                expense.memo,
                expense.date,
                expense.repeatableMonth,
                expense.repeatableCount
            )
        )
    }
}
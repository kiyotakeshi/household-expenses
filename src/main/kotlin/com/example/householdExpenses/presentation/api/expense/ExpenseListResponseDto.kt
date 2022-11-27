package com.example.householdExpenses.presentation.api.expense

import java.time.LocalDate

data class ExpenseListResponseDto(
    val expenses: List<ExpenseResponseDto>
)

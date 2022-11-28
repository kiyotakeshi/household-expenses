package com.example.householdExpenses.presentation.api.expense

import java.time.LocalDate

data class ExpenseResponseDto(
    val id: Int?,
    val category_name: String,
    val member_id: Int,
    val name: String,
    val price: Int,
    val memo: String?,
    val date: LocalDate,
    val repeatable_month: Int,
    val repeatable_count: Int
)

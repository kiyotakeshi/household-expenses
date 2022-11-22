package com.example.householdExpenses.domain.expense

import java.time.LocalDate

/**
 * @author kiyota
 */
data class Expense (
    val id: Int,
    val category_id: Int,
    val member_id: Int,
    val name: String,
    val price: Int,
    val memo: String?,
    val date: LocalDate,
    val repeatable_month: Int,
    val repeatable_count: Int
)
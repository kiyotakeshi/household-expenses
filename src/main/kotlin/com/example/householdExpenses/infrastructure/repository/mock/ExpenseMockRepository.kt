package com.example.householdExpenses.infrastructure.repository.mock

import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.domain.expense.ExpenseRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ExpenseMockRepository : ExpenseRepository {

    private val expenses = listOf(
        Expense(
            id = 1,
            category_id = 1,
            member_id = 1,
            name = "粉ミルク",
            price = 500,
            memo = "200gの缶のもの",
            date = LocalDate.of(2022, 11, 23),
            repeatable_month = 1,
            repeatable_count = 1
        ),
        Expense(2, 5, 1, "おしゃぶり", 300, null, LocalDate.of(2022, 11, 23), 0, 0),
        Expense(3, 1, 2, "おやつ", 150, null, LocalDate.of(2022, 11, 23), 0, 0)
    )

    override fun getExpenses(): List<Expense> = expenses
}
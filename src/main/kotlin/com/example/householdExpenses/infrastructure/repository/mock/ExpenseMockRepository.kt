package com.example.householdExpenses.infrastructure.repository.mock

import com.example.householdExpenses.domain.category.Category
import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.domain.expense.ExpenseRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class ExpenseMockRepository : ExpenseRepository {

    val category1 = Category.create(name = "食費", rank = 1)
    val category2 = Category.create(name = "消耗品", rank = 2)

    private val expenses = listOf(
        Expense.create(
            category = category1,
            member_id = 1,
            name = "粉ミルク",
            price = 500,
            memo = "200gの缶のもの",
            date = LocalDate.of(2022, 11, 23),
            repeatable_month = 1,
            repeatable_count = 1
        ),
        Expense.create(category2, 1, "おしゃぶり", 300, null, LocalDate.of(2022, 11, 23), 0, 0),
        Expense.create(category1, 2, "おやつ", 150, null, LocalDate.of(2022, 11, 23), 0, 0)
    )

    override fun getExpenses(): List<Expense> = expenses
}
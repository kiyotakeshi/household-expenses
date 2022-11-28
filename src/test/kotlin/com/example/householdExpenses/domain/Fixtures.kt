package com.example.householdExpenses.domain

import com.example.householdExpenses.domain.category.Category
import com.example.householdExpenses.domain.expense.Expense
import java.time.LocalDate

/**
 * @author kiyota
 */
class Fixtures {
    companion object {
        fun CategoryA(): Category {
            return Category.create(name = "食費", rank = 1)
        }
        fun CategoryB(): Category {
            return Category.create(name = "消耗品", rank = 2)
        }
        fun ExpenseA(): Expense {
            return Expense.create(
                category = CategoryA(),
                member_id = 1,
                name = "粉ミルク",
                price = 500,
                memo = "200gの缶のもの",
                date = LocalDate.of(2022, 11, 23),
                repeatable_month = 1,
                repeatable_count = 1
            )
        }

        fun ExpenseB(): Expense {
            return Expense.create(CategoryB(), 1, "おしゃぶり", 300, null, LocalDate.of(2022, 11, 24), 0, 0)
        }

        fun ExpenseC(): Expense {
            return Expense.create(CategoryA(), 2, "おやつ", 150, null, LocalDate.of(2022, 11, 23), 0, 0)
        }
    }
}
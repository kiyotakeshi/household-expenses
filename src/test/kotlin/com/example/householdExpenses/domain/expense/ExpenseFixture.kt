package com.example.householdExpenses.domain.expense

import java.time.LocalDate

/**
 * @author kiyota
 */
class ExpenseFixture {
    companion object {
        fun ExpenseA(): Expense {
            return Expense.create(
                category_id = 1,
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
            return Expense.create(5, 1, "おしゃぶり", 300, null, LocalDate.of(2022, 11, 23), 0, 0)
        }

        fun ExpenseC(): Expense {
            return Expense.create(1, 2, "おやつ", 150, null, LocalDate.of(2022, 11, 23), 0, 0)
        }
    }
}
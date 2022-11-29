package com.example.householdExpenses.domain.expense

import com.example.householdExpenses.domain.category.Category
import java.time.LocalDate

/**
 * @author kiyota
 */
class Expense private constructor(
    id: Int?, category: Category, memberId: Int, name: String, price: Int, memo: String?,
    date: LocalDate, repeatableMonth: Int, repeatableCount: Int
) {
    var id: Int? = id
    val category: Category = category
    val memberId = memberId
    val name = name
    val price = price
    val memo = memo
    val date = date
    val repeatableMonth = repeatableMonth
    val repeatableCount = repeatableCount

    companion object {

        fun create(
            category: Category, member_id: Int, name: String, price: Int, memo: String?,
            date: LocalDate, repeatable_month: Int, repeatable_count: Int
        ): Expense {
            return Expense(null, category, member_id, name, price, memo, date, repeatable_month, repeatable_count)
        }
        fun reconstruct(
            id: Int, category: Category, member_id: Int, name: String, price: Int, memo: String?,
            date: LocalDate, repeatable_month: Int, repeatable_count: Int
        ): Expense {
            return Expense(id, category, member_id, name, price, memo, date, repeatable_month, repeatable_count)
        }
    }
}
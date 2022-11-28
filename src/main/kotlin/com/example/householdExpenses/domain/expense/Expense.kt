package com.example.householdExpenses.domain.expense

import com.example.householdExpenses.domain.category.Category
import java.time.LocalDate

/**
 * @author kiyota
 */
class Expense private constructor(
    id: Int?, category: Category, member_id: Int, name: String, price: Int, memo: String?,
    date: LocalDate, repeatable_month: Int, repeatable_count: Int
) {
    var id: Int? = id
    val category: Category = category
    val member_id = member_id
    val name = name
    val price = price
    val memo = memo
    val date = date
    val repeatable_month = repeatable_month
    val repeatable_count = repeatable_count

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
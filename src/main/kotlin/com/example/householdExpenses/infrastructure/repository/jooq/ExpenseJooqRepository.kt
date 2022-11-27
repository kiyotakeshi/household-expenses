package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.domain.category.Category
import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.domain.expense.ExpenseRepository
import com.example.householdExpenses.jooq.codegen.keys.EXPENSES__FK_EXPENSE_CATEGORY_CD0468A2
import com.example.householdExpenses.jooq.codegen.tables.records.CategoriesRecord
import com.example.householdExpenses.jooq.codegen.tables.records.ExpensesRecord
import com.example.householdExpenses.jooq.codegen.tables.references.CATEGORIES
import com.example.householdExpenses.jooq.codegen.tables.references.EXPENSES
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Repository
@Primary
class ExpenseJooqRepository(private val create: DSLContext) : ExpenseRepository {

    override fun getExpenses(): List<Expense> {
        val records: Result<Record> = create.selectFrom(
            EXPENSES.leftOuterJoin(CATEGORIES)
                .on(EXPENSES.CATEGORY_ID.eq(CATEGORIES.ID))
        )
            .orderBy(EXPENSES.ID)
            .fetch()

        val expenses = records.map {
            val category = Category.reconstruct(
                id = it.getValue(CATEGORIES.ID)!!,
                name = it.getValue(CATEGORIES.NAME)!!,
                rank = it.getValue(CATEGORIES.RANK)!!
            )
            Expense.reconstruct(
                id = it.getValue(EXPENSES.ID)!!,
                category = category,
                member_id = it.getValue(EXPENSES.MEMBER_ID)!!,
                name = it.getValue(EXPENSES.NAME)!!,
                price = it.getValue(EXPENSES.PRICE)!!,
                memo = it.getValue(EXPENSES.MEMO),
                date = it.getValue(EXPENSES.DATE)!!,
                repeatable_month = it.getValue(EXPENSES.REPEATABLE_MONTH)!!,
                repeatable_count = it.getValue(EXPENSES.REPEATABLE_COUNT)!!
            )
        }
            .toList()

        return expenses
    }
}
package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.domain.category.Category
import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.domain.expense.ExpenseRepository
import com.example.householdExpenses.jooq.codegen.tables.Expenses
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
        val e: Expenses = EXPENSES.`as`("e")
        val c = CATEGORIES.`as`("c")

        val records: Result<Record> = create.selectFrom(
            e.leftOuterJoin(c)
                .on(e.CATEGORY_ID.eq(c.ID))
        )
            .orderBy(e.ID)
            .fetch()

        val expenses = records.map {
            val category = Category.reconstruct(
                id = it.getValue(c.ID)!!,
                name = it.getValue(c.NAME)!!,
                rank = it.getValue(c.RANK)!!
            )
            Expense.reconstruct(
                id = it.getValue(e.ID)!!,
                category = category,
                member_id = it.getValue(e.MEMBER_ID)!!,
                name = it.getValue(e.NAME)!!,
                price = it.getValue(e.PRICE)!!,
                memo = it.getValue(e.MEMO),
                date = it.getValue(e.DATE)!!,
                repeatable_month = it.getValue(e.REPEATABLE_MONTH)!!,
                repeatable_count = it.getValue(e.REPEATABLE_COUNT)!!
            )
        }.toList()

        return expenses
    }
}
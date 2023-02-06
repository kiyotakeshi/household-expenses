package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.domain.category.Category
import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.domain.expense.ExpenseRepository
import com.example.householdExpenses.jooq.codegen.sequences.EXPENSE_ID_SEQ
import com.example.householdExpenses.jooq.codegen.tables.Expenses
import com.example.householdExpenses.jooq.codegen.tables.references.CATEGORIES
import com.example.householdExpenses.jooq.codegen.tables.references.EXPENSES
import com.example.householdExpenses.model.ExpenseRequestDto
import org.jooq.DSLContext
import org.jooq.Field
import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Repository

@Repository
class ExpenseJooqRepository(private val create: DSLContext) : ExpenseRepository {

    val e: Expenses = EXPENSES.`as`("e")
    val c = CATEGORIES.`as`("c")

    override fun getExpenses(): List<Expense> {

        val records: Result<Record> = create.selectFrom(
            e.leftOuterJoin(c)
                .on(e.CATEGORY_ID.eq(c.ID))
        )
            .orderBy(e.ID)
            .fetch()

        val expenses = records.map {
            Expense.reconstruct(
                id = it.getValue(e.ID)!!,
                category = Category.reconstruct(
                    id = it.getValue(c.ID)!!,
                    name = it.getValue(c.NAME)!!,
                    rank = it.getValue(c.RANK)!!
                ),
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

    override fun getExpense(id: Int): Expense? {

        val record: Record = create.selectFrom(
            e.leftOuterJoin(c)
                .on(e.CATEGORY_ID.eq(c.ID))
        )
            .where(e.ID.eq(id))
            .fetchOne() ?: return null

        return record.map {
            Expense.reconstruct(
                id = it.getValue(e.ID)!!,
                category = Category.reconstruct(
                    id = it.getValue(c.ID)!!,
                    name = it.getValue(c.NAME)!!,
                    rank = it.getValue(c.RANK)!!
                ),
                member_id = it.getValue(e.MEMBER_ID)!!,
                name = it.getValue(e.NAME)!!,
                price = it.getValue(e.PRICE)!!,
                memo = it.getValue(e.MEMO),
                date = it.getValue(e.DATE)!!,
                repeatable_month = it.getValue(e.REPEATABLE_MONTH)!!,
                repeatable_count = it.getValue(e.REPEATABLE_COUNT)!!
            )
        }
    }

    override fun addExpense(memberId: Int, request: ExpenseRequestDto): Expense {
        val returningExpense = create.insertInto(e)
            .values(
                EXPENSE_ID_SEQ.nextval(),
                request.categoryId,
                memberId,
                request.name,
                request.price,
                request.memo,
                request.date,
                request.repeatableMonth,
                request.repeatableCount
            )
            .returning()
            .first()

        return this.getExpense(returningExpense[e.ID]!!)!!
    }
}
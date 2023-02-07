package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.domain.category.Category
import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.domain.expense.ExpenseRepository
import com.example.householdExpenses.jooq.codegen.sequences.EXPENSE_ID_SEQ
import com.example.householdExpenses.jooq.codegen.tables.Expenses
import com.example.householdExpenses.jooq.codegen.tables.references.CATEGORIES
import com.example.householdExpenses.jooq.codegen.tables.references.EXPENSES
import com.example.householdExpenses.jooq.codegen.tables.references.MEMBERS
import com.example.householdExpenses.jooq.codegen.tables.references.USERS
import com.example.householdExpenses.model.ExpenseRequestDto
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Repository
import java.lang.RuntimeException

@Repository
class ExpenseJooqRepository(private val create: DSLContext) : ExpenseRepository {

    val e: Expenses = EXPENSES.`as`("e")
    val c = CATEGORIES.`as`("c")
    val m = MEMBERS.`as`("m")
    val u = USERS.`as`("u")

    override fun getExpenses(userId: Int): List<Expense> {
        val records: Result<Record> = create.select()
            .from(e)
            .leftJoin(m).on(e.MEMBER_ID.eq(m.ID))
            .leftJoin(u).on(m.USER_ID.eq(u.ID))
            .leftJoin(c).on(e.CATEGORY_ID.eq(c.ID))
            .where(u.ID.eq(userId))
            .orderBy(e.ID)
            .fetch()
            // .fetchInto(Expense::class.java)

        val expenses = records.map {
            Expense.reconstruct(
                id = it[e.ID]!!,
                category = Category.reconstruct(
                    id = it[c.ID]!!,
                    name = it[c.NAME]!!,
                    rank = it[c.RANK]!!
                ),
                member_id = it[e.MEMBER_ID]!!,
                name = it[e.NAME]!!,
                price = it[e.PRICE]!!,
                memo = it[e.MEMO],
                date = it[e.DATE]!!,
                repeatable_month = it[e.REPEATABLE_MONTH],
                repeatable_count = it[e.REPEATABLE_COUNT]
            )
        }.toList()

        return expenses
    }

    override fun getExpense(id: Int): Expense {

        val record: Record = create.selectFrom(
            e.leftOuterJoin(c)
                .on(e.CATEGORY_ID.eq(c.ID))
        )
            .where(e.ID.eq(id))
            .fetchOne() ?: throw RuntimeException("expense not found: id($id)")

        return record.map {
            Expense.reconstruct(
                id = it[e.ID]!!,
                category = Category.reconstruct(
                    id = it[c.ID]!!,
                    name = it[c.NAME]!!,
                    rank = it[c.RANK]!!
                ),
                member_id = it[e.MEMBER_ID]!!,
                name = it[e.NAME]!!,
                price = it[e.PRICE]!!,
                memo = it[e.MEMO],
                date = it[e.DATE]!!,
                repeatable_month = it[e.REPEATABLE_MONTH],
                repeatable_count = it[e.REPEATABLE_COUNT]
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

        return this.getExpense(returningExpense[e.ID]!!)
    }
}
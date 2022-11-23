package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.domain.expense.Expense
import com.example.householdExpenses.domain.expense.ExpenseRepository
import com.example.householdExpenses.jooq.codegen.tables.records.ExpensesRecord
import com.example.householdExpenses.jooq.codegen.tables.references.EXPENSES
import org.jooq.DSLContext
import org.jooq.Result
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Repository

@Repository
@Primary
class ExpenseJooqRepository(private val create: DSLContext) : ExpenseRepository {

    override fun getExpenses(): List<Expense> {
        val expensesRecords: Result<ExpensesRecord> = create.selectFrom(EXPENSES)
            .fetch()

        return expensesRecords.map {
            Expense(
                it.id!!, it.categoryId!!, it.memberId!!,
                it.name!!, it.price!!, it.memo, it.date!!, it.repeatableMonth!!, it.repeatableCount!!
            )
        }.toList()
    }
}
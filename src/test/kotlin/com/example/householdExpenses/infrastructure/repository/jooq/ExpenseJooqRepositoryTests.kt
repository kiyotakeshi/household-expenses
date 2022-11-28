package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.domain.Fixtures
import com.example.householdExpenses.domain.expense.Expense
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * [com.example.householdExpenses.infrastructure.repository.jooq]
 * @author kiyota
 */
@JooqTest
internal class ExpenseJooqRepositoryTests(@Autowired sut: ExpenseJooqRepository) {
    private val sut: ExpenseJooqRepository

    init {
        this.sut = sut
    }

    @Test
    fun selectAll() {
        val expenses: List<Expense> = sut.getExpenses()
        assertThat(expenses).hasSize(3)
        assertThat(expenses[0].name).isEqualTo(Fixtures.ExpenseA().name)
        assertThat(expenses[0].category.name).isEqualTo(Fixtures.ExpenseA().category.name)
    }

    @Configuration
    @Import(ExpenseJooqRepository::class)
    internal class LocalTestContext
}
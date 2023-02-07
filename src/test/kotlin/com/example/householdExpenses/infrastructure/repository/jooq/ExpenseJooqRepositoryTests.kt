package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.domain.Fixtures
import com.example.householdExpenses.domain.expense.Expense
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor

/**
 * [com.example.householdExpenses.infrastructure.repository.jooq.ExpenseJooqRepository]
 * @author kiyota
 */
@JooqTest
@Import(ExpenseJooqRepository::class, RsaKeyConversionServicePostProcessor::class)
internal class ExpenseJooqRepositoryTests(@Autowired private val sut: ExpenseJooqRepository) {

    @Test
    fun selectAll() {
        val actual: List<Expense> = sut.getExpenses(1)
        assertThat(actual).hasSize(2)
        assertThat(actual[0].name).isEqualTo(Fixtures.ExpenseA().name)
        assertThat(actual[0].category.name).isEqualTo(Fixtures.ExpenseA().category.name)
    }

    @Test
    fun selectById() {
        val actual = sut.getExpense(1)

        assertThat(actual.category.name).isEqualTo("食費")
        assertThat(actual.name).isEqualTo(Fixtures.ExpenseA().name)
        assertThat(actual.date).isEqualTo(Fixtures.ExpenseA().date)
    }

    @Test
    fun `selectById throw exception`() {
        val notExitsId = 1000
        assertThatThrownBy {
            sut.getExpense(notExitsId)
        }.isInstanceOf(RuntimeException::class.java)
            .hasMessageContaining("expense not found: id($notExitsId)")
    }

    @Test
    fun addExpense() {
        val actual = sut.addExpense(1, Fixtures.expenseRequestDtoA())
        assertThat(actual.category.name).isEqualTo("食費")
        assertThat(actual.name).isEqualTo(Fixtures.expenseRequestDtoA().name)
    }
}
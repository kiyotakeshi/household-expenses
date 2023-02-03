package com.example.householdExpenses.infrastructure.repository.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

/**
 * @author kiyota
 */
internal class ExpenseMockRepositoryTests {

    private val mockDataSource = ExpenseMockRepository()

    @Test
    fun `should provide a collection of expenses`() {
        val expenses = mockDataSource.getExpenses()
        assertThat(expenses).isNotEmpty
        assertThat(expenses).hasSize(3);
    }

    @Test
    fun `should provide some mock data`(){
        val expenses = mockDataSource.getExpenses()
        assertThat(expenses).allMatch{
            it.name.isNotBlank()
            it.date.isAfter(LocalDate.of(2022,11,1))
        }
    }
}
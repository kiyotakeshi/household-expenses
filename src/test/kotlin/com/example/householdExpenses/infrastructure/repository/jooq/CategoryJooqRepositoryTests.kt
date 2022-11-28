package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.domain.category.Category
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

/**
 * [com.example.householdExpenses.infrastructure.repository.jooq.CategoryJooqRepository]
 * @author kiyota
 */
@JooqTest
internal class CategoryJooqRepositoryTests(@Autowired sut: CategoryJooqRepository) {
    private val sut: CategoryJooqRepository

    init {
        this.sut = sut
    }

    @Test
    internal fun selectAll() {
        val categories: List<Category> = sut.getCategories()
        assertThat(categories).hasSize(2)
    }

    @Configuration
    @Import(CategoryJooqRepository::class)
    internal class LocalTestContext
}
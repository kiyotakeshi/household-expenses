package com.example.householdExpenses.infrastructure.repository.jooq

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import

/**
 * [com.example.householdExpenses.infrastructure.repository.jooq.UserJooqRepository]
 * @author kiyota
 */
@JooqTest
@Import(UserJooqRepository::class)
internal class UserJooqRepositoryTests(@Autowired private val sut: UserJooqRepository) {

    @Test
    internal fun getUser() {
        val actual = sut.getUser("user1@example.com")
        assertThat(actual?.id).isEqualTo(1)
        assertThat(actual?.roles).contains("USER")
    }
}
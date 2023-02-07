package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.model.MemberRequestDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor
import java.time.LocalDate

/**
 * @author kiyota
 */
@JooqTest
@Import(MemberJooqRepository::class, RsaKeyConversionServicePostProcessor::class)
internal class MemberJooqRepositoryTests(@Autowired private val sut: MemberJooqRepository) {

    @Test
    internal fun add() {
        val member = MemberRequestDto("baby1", LocalDate.of(2023, 2, 5))
        val userId = 1
        val actual = sut.addMember(member, userId)
    }
}
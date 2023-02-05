package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.core.security.RsaKeyProperties
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import
import org.springframework.security.config.crypto.RsaKeyConversionServicePostProcessor

/**
 * [com.example.householdExpenses.infrastructure.repository.jooq.UserJooqRepository]
 * @author kiyota
 */
@JooqTest
@Import(
    UserJooqRepository::class,
    // require ResourceKeyConverterAdapter
    // > Caused by: org.springframework.core.convert.ConverterNotFoundException: No converter found capable of converting from type [java.lang.String] to type [java.security.interfaces.RSAPublicKey]
    // @see https://github.com/spring-projects/spring-security/issues/9316
    RsaKeyConversionServicePostProcessor::class
)
@EnableConfigurationProperties(RsaKeyProperties::class)
internal class UserJooqRepositoryTests(@Autowired private val sut: UserJooqRepository) {

    @Test
    internal fun getUser() {
        val actual = sut.getUser("user1@example.com")
        assertThat(actual?.id).isEqualTo(1)
        assertThat(actual?.roles).contains("USER")
    }
}
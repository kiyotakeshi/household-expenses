package com.example.householdExpenses.core.security

import com.example.householdExpenses.domain.Fixtures.Companion.AdminUser
import com.example.householdExpenses.domain.Fixtures.Companion.NormalUser
import com.example.householdExpenses.domain.user.UserRepository
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.security.core.authority.SimpleGrantedAuthority

/**
 * [com.example.householdExpenses.core.security.CustomUserDetails]
 * @author kiyota
 */
internal class CustomUserDetailsTests {
    private val userRepository: UserRepository = mockk()
    private val sut = CustomUserDetails(userRepository)

    @Test
    internal fun `user has USER role`() {
        every { userRepository.getUser(any()) } returns NormalUser()
        val actual = sut.loadUserByUsername("user1@example.com")
        val userAuthority = SimpleGrantedAuthority("ROLE_USER")
        assertThat(actual.authorities).contains(userAuthority)
    }

    @Test
    internal fun `admin user has ADMIN,USER roles`() {
        every { userRepository.getUser(any()) } returns AdminUser()
        val actual = sut.loadUserByUsername("user1@example.com")
        val authorities = listOf(SimpleGrantedAuthority("ROLE_ADMIN"), SimpleGrantedAuthority("ROLE_USER"))
        assertThat(actual.authorities).containsAll(authorities)
    }
}
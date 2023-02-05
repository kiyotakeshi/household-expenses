package com.example.householdExpenses.usecase.member

import com.example.householdExpenses.domain.member.MemberRepository
import com.example.householdExpenses.domain.user.UserRepository
import com.example.householdExpenses.model.MemberRequestDto
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDate

/**
 * @author kiyota
 */
internal class AddMemberUsecaseTests {
    private val authentication: Authentication = mockk()
    private val memberRepository: MemberRepository = mockk(relaxed = true)
    private val userRepository: UserRepository = mockk(relaxed = true)
    private val sut = AddMemberUsecase(memberRepository, userRepository)

    @Test
    fun `should call its repository to retrieve expenses`() {
        val userName = "user1@example.com"
        every { authentication.name } returns userName
        mockkStatic(SecurityContextHolder::class)
        every { SecurityContextHolder.getContext().authentication } returns authentication

        val member = MemberRequestDto("baby1", LocalDate.of(2023, 2, 5))
        sut.addMember(member)
        verify(exactly = 1) { userRepository.getUser(eq(userName)) }
        verify(exactly = 1) { memberRepository.addMember(any(), any()) }
    }
}
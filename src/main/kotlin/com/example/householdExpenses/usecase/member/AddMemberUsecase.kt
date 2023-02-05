package com.example.householdExpenses.usecase.member

import com.example.householdExpenses.domain.member.Member
import com.example.householdExpenses.domain.member.MemberRepository
import com.example.householdExpenses.domain.user.UserRepository
import com.example.householdExpenses.model.MemberRequestDto
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AddMemberUsecase(
    private val memberRepository: MemberRepository,
    private val userRepository: UserRepository
) {
    fun addMember(request: MemberRequestDto): Member {
        val authentication = SecurityContextHolder.getContext().authentication
        val user = userRepository.getUser(authentication.name) ?: throw UsernameNotFoundException("user details not found for the user: ${authentication.name}")
        return memberRepository.addMember(request, user.id!!)
    }
}

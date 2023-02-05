package com.example.householdExpenses.domain.member

import com.example.householdExpenses.model.MemberRequestDto

interface MemberRepository {
    fun addMember(request: MemberRequestDto, userId: Int): Member
}

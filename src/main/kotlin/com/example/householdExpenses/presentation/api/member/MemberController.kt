package com.example.householdExpenses.presentation.api.member

import com.example.householdExpenses.domain.member.Member
import com.example.householdExpenses.model.MemberRequestDto
import com.example.householdExpenses.model.MemberResponseDto
import com.example.householdExpenses.usecase.member.AddMemberUsecase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author kiyota
 */
@RestController
@RequestMapping("/api/members")
class MemberController(
    private val addMemberUsecase: AddMemberUsecase,
) {

    @PostMapping
    fun addMember(@Valid @RequestBody memberRequestDto: MemberRequestDto): ResponseEntity<MemberResponseDto> {
        val member: Member = addMemberUsecase.addMember(memberRequestDto)
        return ResponseEntity.ok(MemberResponseDto(member.id!!, member.name, member.birthday))
    }
}
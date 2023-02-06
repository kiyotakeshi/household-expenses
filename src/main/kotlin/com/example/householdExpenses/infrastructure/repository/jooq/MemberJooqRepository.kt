package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.domain.member.Member
import com.example.householdExpenses.domain.member.MemberRepository
import com.example.householdExpenses.jooq.codegen.tables.references.MEMBERS
import com.example.householdExpenses.model.MemberRequestDto
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class MemberJooqRepository(private val create: DSLContext) : MemberRepository {

    override fun addMember(request: MemberRequestDto, userId: Int): Member {
        val m = MEMBERS.`as`("m")

        val returningMember = create.insertInto(m, m.USER_ID, m.NAME, m.BIRTHDAY)
            .values(userId, request.name, request.birthday)
            .returning()
            .first()

        return returningMember.map {
            Member.reconstruct(it[m.ID]!!, it[m.NAME]!!, it[m.BIRTHDAY]!!)
        }
    }
}
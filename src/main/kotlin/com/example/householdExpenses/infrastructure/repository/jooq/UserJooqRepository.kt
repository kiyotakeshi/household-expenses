package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.domain.user.User
import com.example.householdExpenses.domain.user.UserRepository
import com.example.householdExpenses.jooq.codegen.tables.references.MEMBERS
import com.example.householdExpenses.jooq.codegen.tables.references.ROLES
import com.example.householdExpenses.jooq.codegen.tables.references.USERS
import com.example.householdExpenses.jooq.codegen.tables.references.USERS_ROLES
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.springframework.stereotype.Repository

@Repository
class UserJooqRepository(private val create: DSLContext) : UserRepository {

    val u = USERS.`as`("u")
    val r = ROLES.`as`("r")
    val ur = USERS_ROLES.`as`("ur")
    val m = MEMBERS.`as`("m")

    override fun getUser(email: String): User? {

        val result: Result<Record> = create.select()
            .from(u)
            .leftJoin(ur)
            .on(u.ID.eq(ur.USER_ID))
            .leftJoin(r)
            .on(ur.ROLE_ID.eq(r.ID))
            .where(u.EMAIL.eq(email))
            .fetch()

        if (result.isEmpty()) {
            return null
        }

        val roles = result.map { it[r.NAME] }.toSet()
        val first = result.first()
        return User.reconstruct(first[u.ID]!!, first[u.EMAIL]!!, first[u.PASSWORD]!!, roles)
    }

    override fun hasMember(userName: String, memberId: Int): Boolean {
        return create
            .selectCount()
            .from(u)
            .leftJoin(m).on(u.ID.eq(m.USER_ID))
            .where(m.ID.eq(memberId).and(u.EMAIL.eq(userName)))
            .fetchOne(0, Long::class.java)!! > 0
    }
}

package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.domain.user.User
import com.example.householdExpenses.domain.user.UserRepository
import com.example.householdExpenses.jooq.codegen.tables.records.RolesRecord
import com.example.householdExpenses.jooq.codegen.tables.records.UsersRecord
import com.example.householdExpenses.jooq.codegen.tables.references.ROLES
import com.example.householdExpenses.jooq.codegen.tables.references.USERS
import com.example.householdExpenses.jooq.codegen.tables.references.USERS_ROLES
import org.jooq.*
import org.springframework.stereotype.Repository

@Repository
class UserJooqRepository(private val create: DSLContext) : UserRepository {

    override fun getUser(email: String): User? {
        val u = USERS.`as`("u")
        val r = ROLES.`as`("r")
        val ur = USERS_ROLES.`as`("ur")

        val result: Result<Record> = create.select()
            .from(u)
            .join(ur)
            .on(u.ID.eq(ur.USER_ID))
            .join(r)
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
}

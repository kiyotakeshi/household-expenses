package com.example.householdExpenses.domain.member

import java.time.LocalDate

/**
 * @author kiyota
 */
class Member private constructor(
        id: Int?, name: String, birthday: LocalDate
){
        var id = id
        val name = name
        val birthday = birthday

        companion object {
            fun create(name: String, birthday: LocalDate): Member {
                return Member(null, name, birthday)
            }
            fun reconstruct(id: Int, name: String, birthday: LocalDate): Member {
                return Member(id, name, birthday)
            }
        }
}


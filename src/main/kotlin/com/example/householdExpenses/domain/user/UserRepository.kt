package com.example.householdExpenses.domain.user

interface UserRepository {
    fun getUser(email :String): User
    fun hasMember(userName: String, memberId: Int): Boolean
}

package com.example.householdExpenses.domain.user

interface UserRepository {
    fun getUser(email :String): User?
}

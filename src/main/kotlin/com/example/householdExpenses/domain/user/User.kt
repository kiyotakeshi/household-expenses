package com.example.householdExpenses.domain.user

class User private constructor(
    id: Int?, email: String, password: String, roles: Set<String>?
){
    var id = id
    val email = email
    val password = password
    val roles = roles

    companion object {
        fun reconstruct(id: Int, email: String, password: String, roles: Set<String>?): User {
            return User(id, email, password, roles)
        }
    }

    override fun toString(): String {
        return "User(id=$id, email='$email', password='$password', roles=$roles)"
    }
}

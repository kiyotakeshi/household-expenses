package com.example.householdExpenses.core.security

import com.example.householdExpenses.domain.user.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * @author kiyota
 */
@Service
class CustomUserDetails(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.getUser(email)

        val authorities: MutableList<GrantedAuthority> = ArrayList()
        user.roles?.map {
            authorities.add(SimpleGrantedAuthority("ROLE_${it}"))
        }
        return User(user.email, user.password, authorities)
    }
}
package com.example.householdExpenses.presentation.api.user

import com.example.householdExpenses.core.security.TokenService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author kiyota
 */
@RestController
@RequestMapping("/api/token")
class TokenController(
    private val tokenService: TokenService
) {
    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(TokenController::class.java)
    }
    @PostMapping
    fun generateJwt(authentication: Authentication): String {
        LOG.debug("token requested for user: ${authentication.name}")
        val token = tokenService.generateToken(authentication)
        LOG.debug("token generated: $token")
        return token
    }
}
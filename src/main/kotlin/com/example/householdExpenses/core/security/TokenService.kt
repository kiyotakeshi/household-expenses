package com.example.householdExpenses.core.security

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * @author kiyota
 */
@Service
class TokenService(private val jwtEncoder: JwtEncoder) {

    fun generateToken(authentication: Authentication): String {
        val now = Instant.now()

        val scope: String = authentication.authorities.joinToString(separator = " ") { it.authority }

        val claims = JwtClaimsSet.builder()
            // self-signed JWT のため
            .issuer("self")
            .issuedAt(now)
            .expiresAt(now.plus(8, ChronoUnit.HOURS))
            .subject(authentication.name)
            .claim("scope", scope)
            .build()

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).tokenValue
    }
}

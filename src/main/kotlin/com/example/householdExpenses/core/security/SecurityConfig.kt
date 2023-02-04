package com.example.householdExpenses.core.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

/**
 * @author kiyota
 */
@EnableWebSecurity(debug = true)
@Configuration
class SecurityConfig {

    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain? {
        http
            .authorizeHttpRequests {
                it.requestMatchers("/api/hello").permitAll()
                    .requestMatchers("/api/**").hasRole("USER")
                    .anyRequest().authenticated()
            }
            .httpBasic()
            // TODO: enable
            .and().csrf().disable()
        // .addFilterAfter(LoggingAuthoritiesFilter(), BasicAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

}
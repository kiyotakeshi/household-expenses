package com.example.householdExpenses

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

/**
 * @author kiyota
 */
class IntegrationTestUtils {

    companion object {
        fun fetchJwt(mockMvc: MockMvc): String {
            val tokenRequest = mockMvc.post("/api/token") {
                with(SecurityMockMvcRequestPostProcessors.httpBasic("user1@example.com", "1qazxsw2"))
            }
                .andExpect { status { isOk() } }
                .andReturn()

            return tokenRequest.response.contentAsString
        }
    }
}
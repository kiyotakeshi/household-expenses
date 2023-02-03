package com.example.householdExpenses.presentation.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author kiyota
 */
@RestController
@RequestMapping("/api/hello")
class HelloWorldController {

    @GetMapping
    fun helloWorld(): String = "hello world"
}
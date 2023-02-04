package com.example.householdExpenses

import com.example.householdExpenses.core.security.RsaKeyProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(RsaKeyProperties::class)
@SpringBootApplication
class HouseholdExpensesApplication

fun main(args: Array<String>) {
	runApplication<HouseholdExpensesApplication>(*args)
}

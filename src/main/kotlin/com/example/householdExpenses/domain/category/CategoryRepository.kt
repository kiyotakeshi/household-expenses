package com.example.householdExpenses.domain.category

interface CategoryRepository {
    fun getCategories(): List<Category>
}

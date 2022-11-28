package com.example.householdExpenses.usecase.category

import com.example.householdExpenses.domain.category.Category
import com.example.householdExpenses.domain.category.CategoryRepository
import org.springframework.stereotype.Service

@Service
class GetCategoriesUsecase(private val categoryRepository: CategoryRepository) {

    fun getCategories(): List<Category> = this.categoryRepository.getCategories()
}

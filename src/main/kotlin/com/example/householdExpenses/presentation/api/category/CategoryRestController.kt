package com.example.householdExpenses.presentation.api.category

import com.example.householdExpenses.domain.category.Category
import com.example.householdExpenses.usecase.category.GetCategoriesUsecase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author kiyota
 */
@RestController
@RequestMapping("/api/categories")
class CategoryRestController(private val getCategoriesUsecase: GetCategoriesUsecase) {

    @GetMapping
    fun getCategories(): ResponseEntity<List<CategoryResponseDto>> {
        val categories: List<Category> = getCategoriesUsecase.getCategories()

        val response = categories.map {
            CategoryResponseDto(id = it.id, name = it.name, rank = it.rank)
        }.toList()

        return ResponseEntity.ok(response)
    }
}
package com.example.householdExpenses.usecase.category

import com.example.householdExpenses.domain.category.CategoryRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

/**
 * [com.example.householdExpenses.usecase.category.GetCategoriesUsecase]
 * @author kiyota
 */
internal class GetCategoriesUsecaseTests {

    private val categoryRepository: CategoryRepository = mockk(relaxed = true)
    private val sut = GetCategoriesUsecase(categoryRepository)

    @Test
    internal fun `should call its repository to retrieve expenses`() {
        sut.getCategories()

        verify(exactly = 1) { categoryRepository.getCategories() }
    }
}
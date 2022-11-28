package com.example.householdExpenses.infrastructure.repository.jooq

import com.example.householdExpenses.domain.category.Category
import com.example.householdExpenses.domain.category.CategoryRepository
import com.example.householdExpenses.jooq.codegen.tables.Categories
import com.example.householdExpenses.jooq.codegen.tables.records.CategoriesRecord
import com.example.householdExpenses.jooq.codegen.tables.references.CATEGORIES
import org.jooq.DSLContext
import org.jooq.Result
import org.springframework.stereotype.Repository

@Repository
class CategoryJooqRepository(private val create: DSLContext) : CategoryRepository {
    override fun getCategories(): List<Category> {
        val c: Categories = CATEGORIES.`as`("c")

        val records: Result<CategoriesRecord> = create.selectFrom(c)
            .fetch()

        val categories = records.map {
            Category.reconstruct(
                id = it.getValue(c.ID)!!,
                name = it.getValue(c.NAME)!!,
                rank = it.getValue(c.RANK)!!
            )
        }.toList()

        return categories
    }
}
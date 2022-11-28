package com.example.householdExpenses.domain.category

/**
 * @author kiyota
 */
class Category private constructor(
    id: Int?, name: String, rank: Int
) {
    var id: Int? = id
    val name = name
    val rank = rank

    companion object {
        fun create(name: String, rank: Int): Category {
            return Category(null, name, rank)
        }
        fun reconstruct(id: Int, name: String, rank: Int): Category {
            return Category(id, name, rank)
        }
    }
}
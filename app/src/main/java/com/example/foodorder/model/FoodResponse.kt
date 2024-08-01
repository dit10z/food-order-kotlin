package com.example.foodorder.model

data class FoodResponse(
    val total: Int,
    val data: List<FoodItem>,
    val message: String,
    val statusCode: Int,
    val status: String
)

data class FoodItem(
    val foodId: Int,
    val categories: Category,
    val foodName: String,
    val price: Int,
    val imageFilename: String,
    val isFavorite: Boolean
)

data class Category(
    val categoryId: Int,
    val categoryName: String
)

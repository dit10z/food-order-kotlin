package com.example.foodorder.model

data class SignInResponse(
    val data: UserData,
    val statusCode: Int,
    val status: String,
    val message: String,
)

data class UserData (
    val user_id: Int,
    val token: String,
    val username: String,
)

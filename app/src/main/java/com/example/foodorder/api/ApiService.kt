package com.example.foodorder.api

import com.example.foodorder.model.SignInRequest
import com.example.foodorder.model.SignInResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/user-management/users/sign-in")
    fun login(@Body request: SignInRequest): Call<SignInResponse>
}

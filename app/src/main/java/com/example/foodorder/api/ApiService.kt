package com.example.foodorder.api

import com.example.foodorder.model.SignInRequest
import com.example.foodorder.model.SignInResponse
import com.example.foodorder.model.FoodResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @POST("/user-management/users/sign-in")
    fun login(@Body request: SignInRequest): Call<SignInResponse>

    @GET("/food-order/foods")
    fun getFoods(
        @Query("pageNumber") pageNumber: Int,
        @Query("pageSize") pageSize: Int,
        @Query("sortBy") sortBy: String,
        @Query("foodName") foodName: String,
        @Query("categoryId") categoryId: Int
    ): Call<FoodResponse>
}

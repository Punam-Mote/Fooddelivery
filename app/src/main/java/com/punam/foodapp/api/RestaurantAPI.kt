package com.punam.foodapp.api

import com.punam.foodapp.entity.Restaurant
import com.punam.foodapp.entity.User
import com.punam.foodapp.response.AddRestaurantResponse
import com.punam.foodapp.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RestaurantAPI {
    // Add Restaurants
    @POST("addRestaurant/")
    suspend fun addRestaurant(
        @Header("Authorization") token : String,
        @Body restaurant : Restaurant
    ): Response<AddRestaurantResponse>
}
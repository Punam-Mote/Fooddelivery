package com.punam.foodapp.repository

import com.punam.foodapp.api.MyApiRequest
import com.punam.foodapp.api.RestaurantAPI
import com.punam.foodapp.api.ServiceBuilder
import com.punam.foodapp.api.UserAPI
import com.punam.foodapp.entity.User
import com.punam.foodapp.response.RegisterResponse

class RestaurantRepository : MyApiRequest() {
    private val restaurantAPI = ServiceBuilder.buildService(RestaurantAPI::class.java)

    //Add Restaurant
    suspend fun registerUser(user: User): RegisterResponse {
        return apiRequest {
            restaurantAPI.registerUser(user)
        }
    }
}
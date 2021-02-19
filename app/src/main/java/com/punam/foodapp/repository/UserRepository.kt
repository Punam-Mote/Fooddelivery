package com.punam.foodapp.repository

import com.punam.foodapp.api.MyApiRequest
import com.punam.foodapp.api.ServiceBuilder
import com.punam.foodapp.api.UserAPI
import com.punam.foodapp.entity.User
import com.punam.foodapp.response.LoginResponse
import com.punam.foodapp.response.RegisterResponse

class UserRepository
    : MyApiRequest() {
    private val userAPI = ServiceBuilder.buildService(UserAPI::class.java)

    //Register User
    suspend fun registerUser(user : User) : RegisterResponse {
        return apiRequest {
            userAPI.registerUser(user)
        }
    }
    suspend fun loginUser(email : String, password : String): LoginResponse{
        return apiRequest {
            userAPI.checkUser(email, password)
        }
    }
}
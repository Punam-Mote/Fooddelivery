package com.punam.foodapp.api

import com.punam.foodapp.entity.User
import com.punam.foodapp.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserAPI {
    //register user
    @POST("auth/register")
    suspend fun registerUser(
        @Body email : User
    ): Response<LoginResponse>

    //login user
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun checkUser(
        @Field("email") email : String,
        @Field("password") password : String
    ): Response<LoginResponse>
}
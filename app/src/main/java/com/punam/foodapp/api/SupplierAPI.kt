package com.punam.foodapp.api

import com.punam.foodapp.entity.Supplier
import com.punam.foodapp.response.LoginResponse
import com.punam.foodapp.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface SupplierAPI {
    //register user
    @POST("insertSupplier/")
    suspend fun registerSupplier(
        @Body email: Supplier
    ): Response<RegisterResponse>

    //login user
    @FormUrlEncoded
    @POST("loginSupplier/")
    suspend fun checkSupplier(
        @Field("email") email : String,
        @Field("password") password : String
    ): Response<LoginResponse>
}
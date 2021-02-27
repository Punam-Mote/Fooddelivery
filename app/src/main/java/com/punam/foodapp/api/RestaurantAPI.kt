package com.punam.foodapp.api

import com.punam.foodapp.entity.Restaurant
import com.punam.foodapp.entity.User
import com.punam.foodapp.response.*
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface RestaurantAPI {
    // Add Restaurants
    @POST("addRestaurant/")
    suspend fun addRestaurant(
        @Header("Authorization") token : String,
        @Body restaurant : Restaurant
    ): Response<AddRestaurantResponse>

//    @GET("Restaurant/all/")
//    suspend fun getAllRestaurants(
//           @Body restaurant: Restaurant
//    ):Response<GetAllRestaurantResponse>

    @DELETE("/deleteRestaurant/{id}")
    suspend fun deleteRestaurant(
            @Header("Authorization") token: String,
            @Path("id") id:String
    ):Response<DeleteRestaurantResponse>

    @PUT("updateRestaurant/")
    suspend fun updateRestaurant(
            @Header("Authorization") token : String,
            @Path("id") id : String,
            @Body restaurant: Restaurant
    ):Response<UpdateRestaurantResponse>

    @Multipart
    @PUT("restaurant/upload-image/{id}")
    suspend fun uploadImage(
            @Header("Authorization") token: String,
            @Path("id") id: String,
            @Part file: MultipartBody.Part
    ): Response<ImageResponse>
}
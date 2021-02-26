package com.punam.foodapp.repository

import com.punam.foodapp.api.MyApiRequest
import com.punam.foodapp.api.RestaurantAPI
import com.punam.foodapp.api.ServiceBuilder
import com.punam.foodapp.api.UserAPI
import com.punam.foodapp.entity.Restaurant
import com.punam.foodapp.entity.User
import com.punam.foodapp.response.*
import okhttp3.MultipartBody

class RestaurantRepository : MyApiRequest() {
    private  val restaurantAPI =
        ServiceBuilder.buildService(RestaurantAPI::class.java)
    //Add student
    suspend fun addRestaurant(restaurant : Restaurant): AddRestaurantResponse{
        return apiRequest {
            restaurantAPI.addRestaurant(
                ServiceBuilder.token!!,restaurant
            )
        }
    }

    suspend fun deleteRestaurant(id:String):DeleteRestaurantResponse{
        return apiRequest {
            restaurantAPI.deleteRestaurant(ServiceBuilder.token!!,id)
        }
    }

    suspend fun updateRestaurant(id:String,updateRestaurant: Restaurant):UpdateRestaurantResponse{
        return apiRequest {
            restaurantAPI.updateRestaurant(ServiceBuilder.token!!,id,updateRestaurant)
        }
    }

    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : ImageResponse {
        return apiRequest {
            restaurantAPI.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }
}
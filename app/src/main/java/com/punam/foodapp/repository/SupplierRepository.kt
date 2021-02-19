package com.punam.foodapp.repository

import com.punam.foodapp.api.MyApiRequest
import com.punam.foodapp.api.ServiceBuilder
import com.punam.foodapp.api.SupplierAPI
import com.punam.foodapp.entity.Supplier
import com.punam.foodapp.response.LoginResponse
import com.punam.foodapp.response.RegisterResponse

class SupplierRepository
    : MyApiRequest() {
    private val SupplierAPI = ServiceBuilder.buildService(SupplierAPI::class.java)

    //Register User
    suspend fun registerSupplier(supplier : Supplier) : RegisterResponse {
        return apiRequest {
            SupplierAPI.registerSupplier(supplier)
        }
    }
    suspend fun loginSupplier(email : String, password : String): LoginResponse{
        return apiRequest {
            SupplierAPI.checkSupplier(email, password)
        }
    }
}
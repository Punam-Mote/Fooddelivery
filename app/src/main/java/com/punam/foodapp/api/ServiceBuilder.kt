package com.punam.foodapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    //private const val BASE_URL = "http://10.0.2.2:90/"
    //private const val BASE_URL = "http://192.168.43.106:3000/api/v1/"
   // private const val BASE_URL = "http://192.168.1.67:90/"
   // private const val BASE_URL = "http://192.168.137.164:90/"

    private const val BASE_URL = "http://localhost:90/"



    var token : String? = null

    private val okHttp = OkHttpClient.Builder()

    //Create retrofit builder
    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttp.build())

    //Create retrofit instance
    private val retrofit = retrofitBuilder.build()

    //Generic function
    fun <T> buildService(serviceType : Class<T>): T{
        return retrofit.create(serviceType)
    }

}
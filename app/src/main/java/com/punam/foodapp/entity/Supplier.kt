package com.punam.foodapp.entity

import androidx.room.Entity

@Entity
data class Supplier(
    val _id : String? = null,
    val restaurant : String? = null,
    val email : String? = null,
    val password : String? = null,
    val contact : String? = null,
    val userType: String?=null,
)
package com.punam.foodapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val _id : String? = null,
    val username : String? = null,
    val email : String? = null,
    val password : String? = null,
    val contact : String? = null,
)
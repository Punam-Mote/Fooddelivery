package com.punam.foodapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Supplier(
    val _id : String? = null,
    val restaurant : String? = null,
    val email : String? = null,
    val password : String? = null,
    val contact : String? = null,
    val userType: String?=null,
){
    @PrimaryKey(autoGenerate = true)
    var supplierId: Int = 0
}

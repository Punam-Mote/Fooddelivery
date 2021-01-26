package com.punam.foodapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    var username:String?=null,
    var email:String?=null,
    var password:String?=null,
    var contact:String?=null,
){
    @PrimaryKey(autoGenerate = true)
    var userId:Int=0
}
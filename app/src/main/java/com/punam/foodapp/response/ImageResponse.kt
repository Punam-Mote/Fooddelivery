package com.punam.foodapp.response

import com.punam.foodapp.entity.Restaurant

data class ImageResponse (
        val success : Boolean?=null,
        val restaurant : Restaurant?=null
        )
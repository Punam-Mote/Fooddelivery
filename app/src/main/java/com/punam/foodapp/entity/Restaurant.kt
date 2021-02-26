package com.punam.foodapp.entity

import android.os.Parcel
import android.os.Parcelable

data class Restaurant (
    val _id : String? = null,
    val restaurantName : String? = null,
    val rMenu : String? = null,
    val rPrice : String? = null,
    val rAddress : String? = null,
    val rcategory : String? = null,
    val rDeliveryHour : String? = null,
    val rImage: String? = null
        ):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(restaurantName)
        parcel.writeString(rMenu)
        parcel.writeString(rPrice)
        parcel.writeString(rAddress)
        parcel.writeString(rcategory)
        parcel.writeString(rDeliveryHour)
        parcel.writeString(rImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}
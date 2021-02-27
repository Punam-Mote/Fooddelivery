package com.punam.foodapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.punam.foodapp.entity.Restaurant

@Dao
interface RestaurantDAO {
    @Insert
    suspend fun insertRestaurant(restaurant : Restaurant)

    @Query("SELECT * FROM Restaurant")
    suspend fun getAllRestaurants() : List<Restaurant>
}
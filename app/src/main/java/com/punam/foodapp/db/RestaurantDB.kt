package com.punam.foodapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.punam.foodapp.dao.RestaurantDAO
import com.punam.foodapp.entity.Restaurant
import com.punam.foodapp.entity.Supplier
import com.punam.foodapp.entity.User

@Database(
        entities = [(Restaurant::class), (Supplier::class),(User::class)],
        version = 1,
        exportSchema = false
)
abstract class RestaurantDB : RoomDatabase(){
    abstract fun getRestaurantDAO(): RestaurantDAO
    companion object {
        @Volatile
        private var instance: RestaurantDB? = null

        fun getInstance(context: Context): RestaurantDB {
            if (instance == null) {
                synchronized(RestaurantDB::class) {
                    instance = buildDatabase(context)
                }
            }
            return instance!!
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        RestaurantDB::class.java,
                        "RestaurantDB"
                ).build()
    }
}
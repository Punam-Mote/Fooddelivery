package com.punam.foodapp.ui

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.punam.foodapp.R
import com.punam.foodapp.adapter.RestaurantAdapter
import com.punam.foodapp.db.RestaurantDB
import com.punam.foodapp.repository.RestaurantRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class DashboardActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        recyclerView = findViewById(R.id.recyclerView)
       // getAllRestaurants()
        CoroutineScope(Dispatchers.IO).launch {
            val listRestaurants = RestaurantDB.getInstance(this@DashboardActivity).getRestaurantDAO().getAllRestaurants()

            withContext(Main){
                recyclerView.adapter = RestaurantAdapter(this@DashboardActivity, listRestaurants)
                recyclerView.layoutManager = LinearLayoutManager(this@DashboardActivity)
            }
        }
        // Check for permission
        if (!hasPermission()) {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
                this@DashboardActivity,
                permissions, 777
        )
    }

    private fun hasPermission(): Boolean {
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                            this,
                            permission
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
            }
        }
        return hasPermission
    }



}
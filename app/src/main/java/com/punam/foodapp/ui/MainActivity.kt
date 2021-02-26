package com.punam.foodapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.punam.foodapp.Map.MapsActivity
import com.punam.foodapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun gotoMap(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }
}
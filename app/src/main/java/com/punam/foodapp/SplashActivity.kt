package com.punam.foodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //IO and Main
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            startActivity(
                Intent(
                this@SplashActivity,
                LoginActivity::class.java
            )
            )
            finish()
            getSharedPref()
        }
    }
    private fun getSharedPref() {
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val username = sharedPref.getString("username", "")
        val password = sharedPref.getString("password", "")
        Toast.makeText(this, "Username : $username and password : $password", Toast.LENGTH_SHORT)
                .show()
    }
}
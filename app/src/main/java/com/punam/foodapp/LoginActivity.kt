package com.punam.foodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    private lateinit var btnLogin:Button
    private lateinit var tvSignup:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnLogin=findViewById(R.id.btnLogin)
        tvSignup=findViewById(R.id.tvSignup)

        btnLogin.setOnClickListener {
            val intent= Intent(this,DashboardActivity::class.java)
            startActivity(intent)
        }
        tvSignup.setOnClickListener {
            val intent=Intent(this,SignupUserActivity::class.java)
            startActivity(intent)
        }
    }
}
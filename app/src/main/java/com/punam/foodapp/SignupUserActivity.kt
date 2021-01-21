package com.punam.foodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SignupUserActivity : AppCompatActivity() {
    private lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_user)
        btnRegister=findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent);
        }
    }
}
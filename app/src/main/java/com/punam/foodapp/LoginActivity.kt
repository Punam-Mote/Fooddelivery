package com.punam.foodapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.punam.foodapp.db.UserDB
import com.punam.foodapp.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin:Button
    private lateinit var tvSignup:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etEmail=findViewById(R.id.etEmail)
        etPassword=findViewById(R.id.etPassword)
        btnLogin=findViewById(R.id.btnLogin)
        tvSignup=findViewById(R.id.tvSignup)

        btnLogin.setOnClickListener {
            login()
        }
        tvSignup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupUserActivity::class.java))
        }
    }
    private fun login() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        var user: User? = null
        CoroutineScope(Dispatchers.IO).launch {
            user = UserDB.getInstance(this@LoginActivity)
                    .getUserDAO()
                    .checkUser(email, password)
            if (user == null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Invalid Username or Password", Toast.LENGTH_SHORT)
                            .show()
                }
            }
            else {
                startActivity(Intent(this@LoginActivity,
                        DashboardActivity::class.java))
            }
        }

    }
}
package com.punam.foodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.punam.foodapp.R
import com.punam.foodapp.db.UserDB
import com.punam.foodapp.entity.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupSupplierActivity : AppCompatActivity() {
    var usertype = arrayOf("Restaurant", "Vendor")

    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var etContact: EditText
    private lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_supplier)
        etUsername=findViewById(R.id.etUsername)
        etEmail=findViewById(R.id.etEmail)
        etPassword=findViewById(R.id.etPassword)
        etConfirmPassword=findViewById(R.id.etConfirmPassword)
        etContact=findViewById(R.id.etContact)
        btnRegister=findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val contact= etContact.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            if (password != confirmPassword) {
                etPassword.error = "Password does not match"
                etPassword.requestFocus()
                return@setOnClickListener
            } else {
                val user = User(username, email, password, contact)
//                CoroutineScope(Dispatchers.IO).launch {
//                    UserDB
//                        .getInstance(this@SignUpUserActivity)
//                        .getUserDAO()
//                        .registerUser(user)
//                    // Switch to Main thread
//                    withContext(Dispatchers.Main){
//                        Toast.makeText(
//                            this@SignupUserActivity,
//                            "user registered",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }
            }
        }
    }
}
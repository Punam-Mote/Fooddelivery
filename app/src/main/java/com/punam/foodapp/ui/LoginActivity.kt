package com.punam.foodapp.ui

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.punam.foodapp.R
import com.punam.foodapp.api.ServiceBuilder
import com.punam.foodapp.repository.SupplierRepository
import com.punam.foodapp.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin:Button
    private lateinit var tvSignup:TextView
    private lateinit var linearLayout: LinearLayout
    private lateinit var chkRememberMe: CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        etEmail=findViewById(R.id.etEmail)
        etPassword=findViewById(R.id.etPassword)
        btnLogin=findViewById(R.id.btnLogin)
        tvSignup=findViewById(R.id.tvSignup)
        linearLayout = findViewById(R.id.linearLayout)
        chkRememberMe = findViewById(R.id.chkRememberMe)

        checkRunTimePermission()

        btnLogin.setOnClickListener {
            Userlogin()
            SupplierLogin()
        }
        tvSignup.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupSupplierActivity::class.java))
        }
    }
    private fun checkRunTimePermission() {
        if (!hasPermission()) {
            requestPermission()
        }
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
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@LoginActivity, permissions, 1)
    }

    private fun Userlogin() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.loginUser(email, password)

                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token}"
                    loginSharedPref()
                    startActivity(
                        Intent(
                            this@LoginActivity,
                            DashboardActivity::class.java
                        )
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        val snack =
                            Snackbar.make(
                                linearLayout,
                                "Invalid credentials",
                                Snackbar.LENGTH_LONG
                            )
                        snack.setAction("OK", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Login error", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    private fun SupplierLogin() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = SupplierRepository()
                val response = repository.loginSupplier(email, password)

                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token}"
                    loginSharedPref()
                    startActivity(
                            Intent(
                                    this@LoginActivity,
                                    DashboardActivity::class.java
                            )
                    )
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        val snack =
                                Snackbar.make(
                                        linearLayout,
                                        "Invalid credentials",
                                        Snackbar.LENGTH_LONG
                                )
                        snack.setAction("OK", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                            this@LoginActivity,
                            "Login error", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    fun loginSharedPref() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val loginSharedPref = getSharedPreferences("LoginPref", MODE_PRIVATE)
        val editor = loginSharedPref.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.commit()
    }


}
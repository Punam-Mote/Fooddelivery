package com.punam.foodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.punam.foodapp.R
import com.punam.foodapp.entity.Supplier
import com.punam.foodapp.entity.User
import com.punam.foodapp.repository.SupplierRepository
import com.punam.foodapp.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupSupplierActivity : AppCompatActivity() {
    var usertypes = arrayOf("Restaurant", "Vendor")

    private lateinit var etRestaurant: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var etContact: EditText
    private lateinit var btnRegister: Button
    private lateinit var userType: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_supplier)
        etRestaurant=findViewById(R.id.etRestaurant)
        etEmail=findViewById(R.id.etEmail)
        etPassword=findViewById(R.id.etPassword)
        etConfirmPassword=findViewById(R.id.etConfirmPassword)
        etContact=findViewById(R.id.etContact)
        btnRegister=findViewById(R.id.btnRegister)
        userType=findViewById(R.id.usertype)

        //for user type i have used AutoCompleteTextView
        val adapter= ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                usertypes
        )
        userType.adapter=adapter
        //on item selected listener on spinner

        userType.onItemSelectedListener=
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                    ) {
                        val selectedItem=parent?.getItemAtPosition(position).toString()
                        Toast.makeText(this@SignupSupplierActivity,"Selected item:$selectedItem",Toast.LENGTH_SHORT).show()
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                }

        btnRegister.setOnClickListener {
            val restaurant = etRestaurant.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val contact= etContact.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            val userType= userType.selectedItem.toString()



            if (password != confirmPassword) {
                etPassword.error = "Password does not match"
                etPassword.requestFocus()
                return@setOnClickListener
            } else {
                val supplier =
                    Supplier(restaurant = restaurant, email = email, password = password, contact=contact,userType = userType)
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val supplierRepository = SupplierRepository()
                        val response = supplierRepository.registerSupplier(supplier)
                        if(response.message == "Supplier Registered."){
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                        this@SignupSupplierActivity,
                                        "User Registered", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@SignupSupplierActivity,
                                "Username cannot be duplicate", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}
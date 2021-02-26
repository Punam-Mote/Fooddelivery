package com.punam.foodapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.punam.foodapp.R
import com.punam.foodapp.entity.Restaurant
import com.punam.foodapp.repository.RestaurantRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateRestaurantActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etName: TextInputEditText
    private lateinit var etMenu: TextInputEditText
    private lateinit var etPrice: TextInputEditText
    private lateinit var etAddress: TextInputEditText
    private lateinit var etCategory: TextInputEditText
    private lateinit var etDelivery: TextInputEditText

    private lateinit var btnUpdate: Button

    private lateinit var ImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_restaurant)
        etName = findViewById(R.id.etName)
        etMenu = findViewById(R.id.etMenu)
        etAddress = findViewById(R.id.etAddress)
        etPrice = findViewById(R.id.etPrice)
        etCategory = findViewById(R.id.etCategory)
        etDelivery = findViewById(R.id.etDelivery)
        btnUpdate = findViewById(R.id.btnUpdate)
        ImageView = findViewById(R.id.ImageView)

        btnUpdate.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnUpdate->{
                updateRestaurant()
            }
        }
    }
    private fun updateRestaurant() {
        val intent = intent.getParcelableExtra<Restaurant>("student")
        val restaurantName = etName.text.toString()
        val rMenu = etMenu.text.toString()
        val rPrice = etPrice.text.toString()
        val rAddress = etAddress.text.toString()
        val rcategory = etCategory.text.toString()
        val rDeliveryHour = etDelivery.text.toString()
        val restaurant = Restaurant(restaurantName = restaurantName, rMenu = rMenu, rPrice = rPrice, rAddress = rAddress, rcategory=rcategory,rDeliveryHour=rDeliveryHour)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val restaurantRepository = RestaurantRepository()
                val response = restaurantRepository.updateRestaurant(intent?._id!!,restaurant)
                if(response.success == true){
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@UpdateRestaurantActivity,
                            "Restaurant updated", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@UpdateRestaurantActivity,
                        ex.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }
}
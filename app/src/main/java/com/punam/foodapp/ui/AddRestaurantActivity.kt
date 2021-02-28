package com.punam.foodapp.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.punam.foodapp.R
import com.punam.foodapp.db.RestaurantDB
import com.punam.foodapp.entity.Restaurant
import com.punam.foodapp.repository.RestaurantRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class AddRestaurantActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var etName: TextInputEditText
    private lateinit var etMenu: TextInputEditText
    private lateinit var etPrice: TextInputEditText
    private lateinit var etAddress: TextInputEditText
    private lateinit var etCategory: TextInputEditText
    private lateinit var etDelivery: TextInputEditText

    private lateinit var btnSave: Button
    private lateinit var btnView: Button

    private lateinit var ImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_restaurant)

        etName = findViewById(R.id.etName)
        etMenu = findViewById(R.id.etMenu)
        etAddress = findViewById(R.id.etAddress)
        etPrice = findViewById(R.id.etPrice)
        etCategory = findViewById(R.id.etCategory)
        etDelivery = findViewById(R.id.etDelivery)
        btnSave = findViewById(R.id.btnSave)
        btnView= findViewById(R.id.btnView)
        ImageView = findViewById(R.id.ImageView)

        btnSave.setOnClickListener(this)

        btnView.setOnClickListener {
            startActivity(
                    Intent(
                            this@AddRestaurantActivity,
                            DashboardActivity::class.java
                    )
            )
        }

        ImageView.setOnClickListener {
            loadPopUpMenu()
        }
    }

    private fun loadPopUpMenu() {
        // Load pop up menu
        val popupMenu = PopupMenu(this@AddRestaurantActivity, ImageView)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuCamera ->
                    openCamera()
                R.id.menuGallery ->
                    openGallery()
            }
            true
        }
        popupMenu.show()
    }


    private var REQUEST_GALLERY_CODE = 0
    private var REQUEST_CAMERA_CODE = 1
    private var imageUrl: String? = null
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_CODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                        contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                ImageView.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            } else if (requestCode == REQUEST_CAMERA_CODE && data != null) {
                val imageBitmap = data.extras?.get("data") as Bitmap
                val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
                val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
                imageUrl = file!!.absolutePath
                ImageView.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
            }
        }
    }

    private fun bitmapToFile(
            bitmap: Bitmap,
            fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                    getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()
            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }
    }

    override fun onClick(v: View?) {
        when (v?.id){
            R.id.btnSave->{
                saveRestaurant()
            }
        }
    }
    private fun saveRestaurant() {
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
                val response = restaurantRepository.addRestaurant(restaurant)
                if(response.success == true){
                    RestaurantDB.getInstance(this@AddRestaurantActivity).getRestaurantDAO().insertRestaurant(restaurant)
                  //  Log.i("res",response.restaurant!!._id.toString())
                    if (imageUrl!=null){
                        uploadImage(response.restaurant!!._id!!)
                    }
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                                this@AddRestaurantActivity,
                                "Restaurant added", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                            this@AddRestaurantActivity,
                            ex.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }
//    private fun uploadImage(bitmap: Bitmap){
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//        val bytes: ByteArray = stream.toByteArray()
//
//        val file = File(this.cacheDir, "user-image.jpeg")
//        file.createNewFile()
//        val fileOutputStream = FileOutputStream(file)
//        fileOutputStream.write(bytes)
//        fileOutputStream.flush()
//        fileOutputStream.close()
//
//        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
//        val body = MultipartBody.Part.createFormData("picture",file.name,requestBody)
//        val api = Api.getClient();
//        val service = api.create(uploadInter::class.java);
//        val callApi = service.uploadFile(body);
//        callApi.enqueue(object : Callback<ServerResponse> {
//            override fun onResponse(
//                    call: Call<ServerResponse>,
//                    response: Response<ServerResponse>
//            ) {
//                if(response.code()==200){
//                    val response = response.body()!!
//                    findViewById<TextView>(R.id.responseText).text =
//                            response.getMessage().toString()
//                }
//            }
//            override fun onFailure(call: Call<ServerResponse>, t: Throwable) {
//                findViewById<TextView>(R.id.responseText).text =
//                        t.localizedMessage
//            }
//        })
//    }
    private fun uploadImage(restaurantId:String) {
        if (imageUrl != null) {
            val file = File(imageUrl!!)
            val reqFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val body =
                    MultipartBody.Part.createFormData("file", file.name, reqFile)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val restaurantRepository = RestaurantRepository()
                    val response = restaurantRepository.uploadImage(restaurantId, body)
                    if (response.success == true) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddRestaurantActivity, "Uploaded", Toast.LENGTH_SHORT)
                                    .show()
                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.d("Error uploading image", ex.localizedMessage)
                        Toast.makeText(
                                this@AddRestaurantActivity,
                                ex.localizedMessage,
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}


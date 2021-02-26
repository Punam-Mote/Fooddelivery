package com.punam.foodapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.punam.foodapp.R
import com.punam.foodapp.api.ServiceBuilder
import com.punam.foodapp.entity.Restaurant
import com.punam.foodapp.repository.RestaurantRepository
import com.punam.foodapp.ui.UpdateRestaurantActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantAdapter (
        val listRestaurants : MutableList<Restaurant>,
        val context: Context
        ): RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>(){
    class RestaurantViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvname : TextView
        val tvmenu : TextView
        val tvprice : TextView
        val tvaddress : TextView
        val tvcategory : TextView
        val tvdelivery : TextView
        val delete : ImageView
        val update : ImageView
        val photo: ImageView
        init {
            tvname= view.findViewById(R.id.tvname)
            tvmenu= view.findViewById(R.id.tvmenu)
            tvprice= view.findViewById(R.id.tvprice)
            tvaddress= view.findViewById(R.id.tvaddress)
            tvcategory= view.findViewById(R.id.tvcategory)
            tvdelivery= view.findViewById(R.id.tvdelivery)
            delete = view.findViewById(R.id.delete)
            update = view.findViewById(R.id.update)
            photo = view.findViewById(R.id.photo)
        }

    }
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RestaurantAdapter.RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.display_layout, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantAdapter.RestaurantViewHolder, position: Int) {
        val rstlst = listRestaurants[position]
        holder.tvname.text = rstlst.restaurantName
        holder.tvmenu.text = rstlst.rMenu.toString()
        holder.tvprice.text = rstlst.rPrice.toString()
        holder.tvaddress.text = rstlst.rAddress.toString()
        holder.tvcategory.text = rstlst.rcategory.toString()
        holder.tvdelivery.text = rstlst.rDeliveryHour.toString()


        val imagePath = ServiceBuilder.loadImagePath() + rstlst.rImage
        if (!rstlst.rImage.equals("no-photo.jpg")) {
            Glide.with(context)
                    .load(imagePath)
                    .fitCenter()
                    .into(holder.photo)
        }

//        holder.delete.setOnClickListener{
//            listStudents.removeAt(position)
//            notifyDataSetChanged()
//        }
        holder.update.setOnClickListener {
            val intent = Intent(context, UpdateRestaurantActivity::class.java)
            intent.putExtra("student",rstlst)
            context.startActivity(intent)
        }
        holder.delete.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete student")
            builder.setMessage("Are you sure you want to delete ${rstlst.restaurantName} ??")
            builder.setIcon(android.R.drawable.ic_delete)
            builder.setPositiveButton("Yes") { _, _ ->
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val restaurantRepository = RestaurantRepository()
                        val response = restaurantRepository.deleteRestaurant(rstlst._id!!)
                        if (response.success == true) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                        context,
                                        "Student Deleted",
                                        Toast.LENGTH_SHORT
                                ).show()
                            }
                            withContext(Dispatchers.Main) {
                                listRestaurants.remove(rstlst)
                                notifyDataSetChanged()
                            }
                        }
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                    context,
                                    ex.toString(),
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            builder.setNegativeButton("No") { _, _ ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return listRestaurants.size
    }

}
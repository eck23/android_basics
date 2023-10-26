package com.example.loginscreen.adaptors

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginscreen.R
import com.example.loginscreen.models.CarModel

class CustomAdaptor(
    private var mList: List<CarModel>,
    private val clickListener: (carModel: CarModel) -> Unit
) : RecyclerView.Adapter<CustomAdaptor.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)


        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val carModel = mList[position]

        Log.d("ADAPTER UPDATE", mList.size.toString())


        holder.imageView.setImageResource(carModel.carImage)

        holder.titleTextView.text =
            if (carModel.carManufactureName.isNullOrEmpty()) carModel.manufactureName
            else carModel.carManufactureName

        holder.subTitleTextView.text = carModel.countryName

        holder.itemView.setOnClickListener {

            clickListener(carModel)

        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<CarModel>) {
        mList = newData
        notifyDataSetChanged()
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        var imageView: ImageView = itemView.findViewById(R.id.recyclerviewImage)
        val titleTextView: TextView = itemView.findViewById(R.id.cardTitleText)
        val subTitleTextView: TextView = itemView.findViewById(R.id.cardSubTitleText)
    }
}
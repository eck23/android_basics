package com.example.loginscreen.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginscreen.R
import com.example.loginscreen.models.CarModel

class CustomAdaptor (private var mList: List<CarModel>,private val clickListener: (carModel:CarModel)->Unit) : RecyclerView.Adapter<CustomAdaptor.ViewHolder>() {






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)


//        view.setOnClickListener(){
//
//            Log.d("RECYCLER VIEW","😂😂😂😂😂😂item clicked")
//
//
//        }

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val carModel = mList[position]

        Log.d("ADAPTER UPDATE", mList.size.toString())

        // sets the image to the imageview from our itemHolder class
//        holder.imageView.setImageResource()

        holder.imageView.setImageResource(carModel.carImage)
        // sets the text to the textview from our itemHolder class
        holder.titleTextView.text = carModel.carManufactureName
        holder.subTitleTextView.text =carModel.countryName

        holder.itemView.setOnClickListener{

            clickListener(carModel)

        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }


    public fun setData(newData: List<CarModel>) {
        mList=newData
    }


    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {


        var imageView: ImageView = itemView.findViewById(R.id.recyclerviewImage)
        val titleTextView: TextView = itemView.findViewById(R.id.cardTitleText)
        val subTitleTextView:TextView= itemView.findViewById(R.id.cardSubTitleText)
    }
}
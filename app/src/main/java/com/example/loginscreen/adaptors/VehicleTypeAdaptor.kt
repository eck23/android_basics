package com.example.loginscreen.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.loginscreen.R
import com.example.loginscreen.models.VehicleType


class VehicleTypeAdapter(private val mList: List<VehicleType>,) : RecyclerView.Adapter<VehicleTypeAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vehicle_type_text, parent, false)


        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return mList.size
    }

    override fun onBindViewHolder(holder: VehicleTypeAdapter.ViewHolder, position: Int) {

        var vehicleType:String=(position+1).toString() +". "+ mList[position].name

        holder.vehicleType.text=vehicleType



    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {


       val vehicleType:TextView= itemView.findViewById(R.id.vehicleTypesText)
    }
}
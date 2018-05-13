package com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.adding

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.model.VehicleTypes


class VehicleAddingAdapter(val contexts: Context, public val vehicleTypes: List<VehicleTypes>) : ArrayAdapter<VehicleTypes>(contexts, R.layout.vehicle_adding_adapter) {


    override fun getCount(): Int {
        return vehicleTypes.size
    }

    override fun getItem(position: Int): VehicleTypes {
        return vehicleTypes[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewItem = LayoutInflater.from(contexts).inflate(R.layout.vehicle_adding_adapter, parent, false)
        val img = viewItem.findViewById<ImageView>(R.id.im_vehicle_adding_adapter)
        val text = viewItem.findViewById<TextView>(R.id.tv_vehicle_adding_adapter)

        img.setImageResource(vehicleTypes[position].img)
        text.text = vehicleTypes[position].name
        return  viewItem
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewItem = LayoutInflater.from(contexts).inflate(R.layout.vehicle_adding_adapter, parent, false)
        val img = viewItem.findViewById<ImageView>(R.id.im_vehicle_adding_adapter)
        val text = viewItem.findViewById<TextView>(R.id.tv_vehicle_adding_adapter)

        img.setImageResource(vehicleTypes[position].img)
        text.text = vehicleTypes[position].name
        return  viewItem
    }

}
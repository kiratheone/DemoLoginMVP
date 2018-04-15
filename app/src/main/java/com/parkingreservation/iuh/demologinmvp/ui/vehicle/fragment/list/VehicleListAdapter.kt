package com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.list

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import butterknife.ButterKnife
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.databinding.VehicleListAdapterBinding
import com.parkingreservation.iuh.demologinmvp.model.Vehicle

class VehicleListAdapter(val context: Context, private val vehicles: List<Vehicle> ): RecyclerView.Adapter<VehicleListAdapter.RecyclerHolder>() {

    lateinit var  binding: VehicleListAdapterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.vehicle_list_adapter, parent, false)
        return RecyclerHolder(binding)
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(vehicles[position])
    }

    class RecyclerHolder(val binding: VehicleListAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

        /**
         *  binding data to view
         */
        fun bind(vehicle: Vehicle) {
            this.binding.vehicle = vehicle
            ButterKnife.bind(this, binding.root)
            binding.executePendingBindings()
        }

    }
}
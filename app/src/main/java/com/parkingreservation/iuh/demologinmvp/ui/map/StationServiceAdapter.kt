package com.parkingreservation.iuh.demologinmvp.ui.map

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import butterknife.ButterKnife
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.databinding.AdapterStationServiceBinding
import com.parkingreservation.iuh.demologinmvp.model.StationServiceModel

class StationServiceAdapter(private val context: Context, private val stationServices: List<StationServiceModel> )
    : RecyclerView.Adapter<StationServiceAdapter.RecyclerHolder>(){

    lateinit var binding: AdapterStationServiceBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_station_service, parent, false)
        return RecyclerHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.stationServices.size
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(this.stationServices[position])
    }



    class RecyclerHolder(val binding: AdapterStationServiceBinding): RecyclerView.ViewHolder(binding.root) {

        /**
         * binding data to view
         */

        fun bind(service: StationServiceModel) {
            this.binding.service = service
            val view = binding.root.findViewById<ImageView>(R.id.im_station_service_icon)
            view.setImageResource(service.img!!)
            ButterKnife.bind(this, binding.root)
            binding.executePendingBindings()
        }
    }
}
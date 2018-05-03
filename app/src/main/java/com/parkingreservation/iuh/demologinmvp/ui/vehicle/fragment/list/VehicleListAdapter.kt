package com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.list

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.databinding.VehicleListAdapterBinding
import com.parkingreservation.iuh.demologinmvp.model.VehicleModel
import com.parkingreservation.iuh.demologinmvp.ui.vehicle.VehicleTypeUtil

class VehicleListAdapter(val context: Context, private val vehicles: MutableList<VehicleModel> )
    : RecyclerView.Adapter<VehicleListAdapter.RecyclerHolder>() {

    lateinit var  binding: VehicleListAdapterBinding

    @BindView(R.id.im_remove_vehicle)
    lateinit var rmVehicle: AppCompatImageView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.vehicle_list_adapter, parent, false)
        ButterKnife.bind(this, binding.root)

        val types = VehicleTypeUtil.getVehicleType()
        return RecyclerHolder(binding, types)
    }

    override fun getItemCount(): Int {
        return vehicles.size
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(vehicles[position])
        rmVehicle.setOnClickListener{this.vehicles.removeAt(position)}
    }

    class RecyclerHolder(val binding: VehicleListAdapterBinding, private val types: HashMap<String, Int>) : RecyclerView.ViewHolder(binding.root) {

        @BindView(R.id.vehicle_type_img)
        lateinit var img: AppCompatImageView
        /**
         *  binding data to view
         */
        fun bind(vehicle: VehicleModel) {
            this.binding.vehicle = vehicle
            ButterKnife.bind(this, binding.root)
            img.setImageResource(types[vehicle.vehicleTypeModel.typeName]!!)
            binding.executePendingBindings()
        }
    }
}
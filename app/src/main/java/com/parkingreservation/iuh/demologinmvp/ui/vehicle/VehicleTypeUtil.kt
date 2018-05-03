package com.parkingreservation.iuh.demologinmvp.ui.vehicle

import com.parkingreservation.iuh.demologinmvp.R

class VehicleTypeUtil {

    companion object {
        fun getVehicleType(): HashMap<String, Int> {
            var a = HashMap<String, Int>()
            a["Xe Đạp"] = R.drawable.ic_vehicle_bycicle
            a["Xe Máy"] = R.drawable.ic_vehicle_bike
            a["Ô Tô"] = R.drawable.ic_vehicle_car
            a["Xe Tải"] = R.drawable.ic_vehicle_truck
            a["Xe Khách"] = R.drawable.ic_vehicle_bus
            return a
        }
    }
}
package com.parkingreservation.iuh.demologinmvp.ui.vehicle

import com.parkingreservation.iuh.demologinmvp.R

class VehicleTypeUtil {

    companion object {
        fun getVehicleType(): HashMap<String, Pair<Int, Int>> {
            var a = HashMap<String, Pair<Int, Int>>()
            a["Xe Đạp"] = Pair(R.drawable.ic_vehicle_bycicle, 1)
            a["Xe Máy"] = Pair(R.drawable.ic_vehicle_bike, 2)
            a["Ô Tô"] = Pair(R.drawable.ic_vehicle_car, 3)
            a["Xe Tải"] = Pair(R.drawable.ic_vehicle_truck, 4)
            a["Xe Khách"] = Pair(R.drawable.ic_vehicle_bus, 5)
            return a
        }
    }
}
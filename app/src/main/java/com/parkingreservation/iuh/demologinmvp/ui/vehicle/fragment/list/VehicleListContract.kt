package com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.list

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.VehicleModel

class VehicleListContract {
    interface Presenter {

    }

    interface View: BaseView {
        fun updateVehicle(vehicles: List<VehicleModel>)
    }
}
package com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.adding

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.VehicleModel

class VehicleAddingContract {
    interface Presenter {
        fun saveVehicle(vehicle: VehicleModel)
    }

    interface View: BaseView  {

    }
}
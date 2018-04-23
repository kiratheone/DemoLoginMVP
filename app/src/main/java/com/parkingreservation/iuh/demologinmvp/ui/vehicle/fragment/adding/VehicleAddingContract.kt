package com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.adding

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.Vehicle

class VehicleAddingContract {
    interface Presenter {
        fun saveVehicle(vehicle: Vehicle)
    }

    interface View: BaseView  {

    }
}
package com.parkingreservation.iuh.demologinmvp.ui.map

import com.google.android.gms.maps.model.Marker
import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.Station
import com.parkingreservation.iuh.demologinmvp.model.User

class MapContract {
    interface Presenter {
        fun loadStationContent(marker: Marker)
        fun bookParkingLot(station: String, vehiclePosition: Int, type: Int)
    }

    interface View : BaseView {
        fun loadUserHeader(user: User)
        fun addStationContent(station: Station?)
    }
}


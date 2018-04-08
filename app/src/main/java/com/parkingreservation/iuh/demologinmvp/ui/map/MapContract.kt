package com.parkingreservation.iuh.demologinmvp.ui.map

import com.google.android.gms.maps.model.Marker
import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.LoginModel
import com.parkingreservation.iuh.demologinmvp.model.Station

class MapContract {
    interface Presenter {
        fun loadStationContent(marker: Marker)
    }

    interface View : BaseView {
        fun loadUserHeader(user: LoginModel)
        fun addStationContent(station: Station?)
    }
}


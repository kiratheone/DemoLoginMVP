package com.parkingreservation.iuh.demologinmvp.ui.map.fragment.mapview

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.temp.Location
import com.parkingreservation.iuh.guest.models.MapResult

class MapViewContract {
    interface Presenter {
        fun getNearbyStation(location: Location)
    }

    interface View: BaseView {
        /**
         *  downloaded all location successfully
         *  it's happening when all location have been loaded on client
         */
        fun loadNearbyStation(mapResult: MapResult)
    }
}
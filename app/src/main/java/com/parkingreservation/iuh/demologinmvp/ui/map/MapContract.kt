package com.parkingreservation.iuh.demologinmvp.ui.map

import com.google.android.gms.maps.model.Marker
import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.*

class MapContract {
    interface Presenter {
        fun loadStationContent(marker: Marker)
        fun bookParkingLot(station: String, vehiclePosition: String, type: List<TicketTypeModels>)
        fun loadTicketTypes(marker: Marker)
        fun isStationValidate(): Boolean
        fun isSlotNotEnough(): Boolean
    }

    interface View : BaseView {
        fun loadUserHeader(user: User?)
        fun addStationContent(station: Station?)
        fun onLoadTicketTypesSuccess(s: MutableList<StationServiceModel>)
        fun onStationImageLoaded(images: List<String>)
        fun onStationCommentLoaded(comments: List<Comment>)
        fun onReservationSuccessful()
        fun onEmptyVehicle()
    }
}



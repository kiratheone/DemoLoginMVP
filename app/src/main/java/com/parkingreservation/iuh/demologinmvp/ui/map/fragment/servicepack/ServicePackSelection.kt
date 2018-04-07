package com.parkingreservation.iuh.demologinmvp.ui.map.fragment.servicepack

import com.parkingreservation.iuh.demologinmvp.R


enum class ServicePackSelection(val imgUrl: Int, val nameA: Int) {
    CLEANING (R.drawable.ic_service_pack_cleaning   ,      R.string.service_clean)   ,
    PARKING  (R.drawable.ic_service_pack_parking    ,      R.string.service_parking) ,
    REPAIR   (R.drawable.ic_service_pack_repair     ,      R.string.service_repair)  ,
    RESCUE   (R.drawable.ic_service_pack_rescue     ,      R.string.service_rescue)  ,
    BOOKING  (R.drawable.ic_service_pack_booking    ,      R.string.service_booking) ,
    CHARGING (R.drawable.ic_service_pack_charging   ,      R.string.service_charging),
}
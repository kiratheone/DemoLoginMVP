package com.parkingreservation.iuh.demologinmvp.ui.map.fragment.servicepack

import com.parkingreservation.iuh.demologinmvp.R


enum class ServicePackSelection(val imgUrl: Int, val nameA: Int) {
    PARKING(R.drawable.ic_service_pack_parking, R.string.service_parking),
    CHARGING(R.drawable.ic_service_pack_charging, R.string.service_charging),
    REPAIR(R.drawable.ic_service_pack_repair, R.string.service_repair),
    CLEANING(R.drawable.ic_service_pack_washing, R.string.service_clean),
}
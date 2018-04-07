package com.parkingreservation.iuh.guest.models

import com.parkingreservation.iuh.demologinmvp.model.temp.Geometry


data class Result(
        val geometry: Geometry,
        val icon: String,
        val id: String,
        val name: String,
        val placeId: String
)
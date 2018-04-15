package com.parkingreservation.iuh.demologinmvp.model

import com.google.gson.annotations.SerializedName

data class Vehicle(@SerializedName("vehicleTypeID")
                   val vehicleTypeID: Int = 0,
                   @SerializedName("licensePlate")
                   val licensePlate: String = "",
                   @SerializedName("driverID")
                   val driverID: String? = "",
                   @SerializedName("name")
                   val name: String = "",
                   @SerializedName("id")
                   val id: String = "")
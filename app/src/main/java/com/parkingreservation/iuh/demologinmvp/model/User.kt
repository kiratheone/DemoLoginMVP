package com.parkingreservation.iuh.demologinmvp.model

import com.google.gson.annotations.SerializedName

data class User(@SerializedName("password")
                val password: String? = "",
                @SerializedName("phoneNumber")
                val phoneNumber: String? = "",
                @SerializedName("address")
                val address: String? = "",
                @SerializedName("balance")
                val balance: Int? = 0,
                @SerializedName("vehicles")
                val vehicles: List<Vehicle>? = emptyList(),
                @SerializedName("driverName")
                val driverName: String = "",
                @SerializedName("applicationID")
                val applicationID: String? = "",
                @SerializedName("userID")
                var userID: String? = "",
                @SerializedName("email")
                val email: String = "")
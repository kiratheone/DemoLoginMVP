package com.parkingreservation.iuh.demologinmvp.model

import java.sql.Timestamp


data class Station(val address: String = ""
                   , val coordinate: String = ""
                   , val star: Int = 0
                   , val stationVehicleTypes: List<StationVehicleTypes>? = emptyList()
                   , val services: List<Service>? = emptyList()
                   , val ownerID: String = ""
                   , val createdDate: Int = 0
                   , val name: String = ""
                   , val closeTime: String = ""
                   , val id: Int = 0
                   , val applicationID: String = ""
                   , val status: String = "")

data class Service(val serviceID: Int = 0
                   , val serviceName: String = "")

data class StationVehicleTypes(val vehicleTypeId: Int = 0
                               , val usedSlots: Int = 0
                               , val totalSlots: Int = 0
                               , val id: Int = 0
                               , val holdingSlots: Int = 0
                               , val stationID: Int = 0)

data class Comment(val id: String? = null
                   , val userID: String? = null
                   , val stationID: Int = 0
                   , val content: String? = null
                   , val star: Double = 0.toDouble()
                   , val createdTime: String? = null)


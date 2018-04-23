package com.parkingreservation.iuh.demologinmvp.model

data class Tickets(val ticketTypeModels: List<TicketTypeModelsItem>?,
                   val createdDate: String = "",
                   val checkOutTime: String = "",
                   val totalPrice: Int? = 0,
                   val checkInTime: String? = "",
                   val paid: Boolean? = false,
                   val qRCode: String = "",
                   val vehicleModel: VehicleModel,
                   val stationName: String = "",
                   val id: String = "",
                   val stationID: String = "",
                   val status: String? = "")

data class TicketTypeModelsItem(val id: Int = 0,
                                val vehicleTypeID: Int = 0,
                                val price: Int = 0,
                                val name: String = "",
                                var ticketTypeID: Int = id,
                                val holdingTime: String = "",
                                val serviceID: Int = 0,
                                val stationID: Int = 0)

data class VehicleModel(val vehicleTypeModel: VehicleTypeModel,
                        val licensePlate: String = "",
                        val driverID: String = "",
                        val name: String = "",
                        val id: String = "")

data class VehicleTypeModel(val typeName: String = "",
                            val typeID: Int = 0)

data class Reservation(val userID: String = "",
                       val stationID: Int = 0,
                       val vehicleID: String = "",
                       val paid: Boolean? = false,
                       val ticketTypeModels: List<TicketTypeModelsItem>?,
                       val totalPrice: Int? = 0)

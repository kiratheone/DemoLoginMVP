package com.parkingreservation.iuh.demologinmvp.model

data class Tickets(val ticketTypeModels: List<TicketTypeModels>?
                   , val createdDate: String = ""
                   , val checkOutTime: String = ""
                   , val totalPrice: Int? = 0
                   , val checkInTime: String? = ""
                   , val paid: Boolean? = false
                   , val qRCode: String = ""
                   , val vehicleModel: VehicleModel
                   , val stationName: String = ""
                   , val id: String = ""
                   , val stationID: String = ""
                   , val status: String? = "")


data class TicketTypeModels(val stationVehicleTypeID: Int = 0
                            , val price: Int = 0
                            , val name: String = ""
                            , val ticketTypeID: Int = 0
                            , val holdingTime: String = ""
                            , val serviceID: Int = 0
                            , val vehicleTypeName: String = "")

data class VehicleModel(val vehicleTypeModel: VehicleTypeModel
                        , val licensePlate: String = ""
                        , var driverID: String = ""
                        , val name: String = ""
                        , val id: String = "")

data class VehicleTypeModel(val typeName: String = ""
                            , val typeID: Int = 0)

data class Reservation(val userID: String = ""
                       , val stationID: Int = 0
                       , val vehicleID: String = ""
                       , val paid: Boolean? = false
                       , val ticketTypeModels: List<TicketTypeModels>?
                       , val totalPrice: Int? = 0)

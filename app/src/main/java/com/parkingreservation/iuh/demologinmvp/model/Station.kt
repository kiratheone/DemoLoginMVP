package com.parkingreservation.iuh.demologinmvp.model


data class Station(val id: Int = 0
                   , val address: String = ""
                   , val name: String = ""
                   , val description: String = ""
                   , val level: Int = 0
                   , val coordinate: String = ""
                   , val ownerID: String
                   , val services: List<Service>? = emptyList()
                   , val ticketTypes: List<TicketTypeModelsItem> = emptyList())


data class Service(val serviceID: Int = 0
                   , val serviceName: String = "")

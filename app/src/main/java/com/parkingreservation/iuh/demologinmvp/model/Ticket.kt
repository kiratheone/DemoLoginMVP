package com.parkingreservation.iuh.demologinmvp.model

data class Ticket(val id: String, val createdDate: String, val place: String,
                  val checkInTime: String, val checkOutTime: String
                  , val type: String, val qRCode: String?)
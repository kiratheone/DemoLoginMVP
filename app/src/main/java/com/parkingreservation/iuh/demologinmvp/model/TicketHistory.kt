package com.parkingreservation.iuh.guest.models

data class TicketHistory(val id: String, val date: String, val place: String, val type: String,
                         val bookedTime: String, val bookedDate: String, val QRcode: String) {}
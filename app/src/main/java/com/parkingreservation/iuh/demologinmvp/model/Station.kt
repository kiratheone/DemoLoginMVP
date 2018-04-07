package com.parkingreservation.iuh.guest.models


data class Station(val name: String, val description: String, val rating: Int,
                   val location: Location, val images: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Station

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}
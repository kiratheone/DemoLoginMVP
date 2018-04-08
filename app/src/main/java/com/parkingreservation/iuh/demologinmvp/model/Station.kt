package com.parkingreservation.iuh.demologinmvp.model


data class Station(val id: Int, val address: String, val name: String, val description: String, val level: Int,
                   val coordinate: String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Station

        if (id != other.id) return false
        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }

}
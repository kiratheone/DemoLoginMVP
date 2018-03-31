package com.parkingreservation.iuh.demologinmvp.model


data class Account (val id: String, val phone: String, val email: String, val address: String){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
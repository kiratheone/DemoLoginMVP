package com.parkingreservation.iuh.demologinmvp.model


data class User (val userId: String, val name: String, val phoneNumber: String, val email: String, val address: String
                 , val vehicles: Array<Vehicle>){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        return userId.hashCode()
    }
}
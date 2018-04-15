package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.model.Vehicle
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VehicleService {

    /**
     *  get all user's vehicle
     *  @return: list of vehicle, empty if user has no vehicle
     */
    @GET("api/vehicles/driver/{id}")
    fun getVehiclesOfUser(@Path("id") id: String): Observable<List<Vehicle>>

    /**
     * add new user's vehicle
     * @vehicle vehicle of user
     * @userID who created vehicle
     */
    @POST("/")
    fun addNewVehicle(vehicle: Vehicle, userId: String)
}
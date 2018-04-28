package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.model.Vehicle
import com.parkingreservation.iuh.demologinmvp.model.VehicleModel
import io.reactivex.Observable
import retrofit2.http.*

interface VehicleService {

    /**
     *  get all user's vehicle
     *  @return: list of vehicle, empty if user has no vehicle
     */
    @GET("api/vehicles/driver/{id}")
    fun getVehiclesOfUser(@Path("id") id: String, @Header("Authorization") token: String): Observable<List<VehicleModel>>

    /**
     * add new user's vehicle
     * @vehicle vehicle of user
     * @userID who created vehicle
     */
    @POST("api/vehicles/")
    fun addVehicle(@Body vehicle: VehicleModel, @Header("Authorization") token: String): Observable<VehicleModel>
}
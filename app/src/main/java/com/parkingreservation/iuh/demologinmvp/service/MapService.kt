package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.util.MapUtil
import com.parkingreservation.iuh.demologinmvp.model.Station
import com.parkingreservation.iuh.demologinmvp.model.StationLocation
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MapService {

    /**
     * get nearby station in a area
     * @lat vertical axis
     * @Lng horizontal axis
     * @return List of Station
     */
    @GET("api/maps/place?rad=${MapUtil.PROXIMITY_RADIUS}")
    fun getNearbyStation(@Query("lat") lat: String,
                         @Query("lng") lng: String): Observable<Array<StationLocation>>


    /**
     * get specific Station
     * @userID Station userID
     * @return a specific Station
     */
    @GET("api/stations/{id}")
    fun getStationDetail(@Path("id")id: String, @Header("Authorization") token: String): Observable<Station>
}
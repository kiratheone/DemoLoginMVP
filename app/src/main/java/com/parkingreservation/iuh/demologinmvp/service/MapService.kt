package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.util.MapUtil
import com.parkingreservation.iuh.guest.models.MapResult
import com.parkingreservation.iuh.demologinmvp.model.Station
import com.parkingreservation.iuh.demologinmvp.model.StationLocation
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MapService {

    /**
     * get nearby station in a area
     * @lat vertical axis
     * @Lng horizontal axis
     * @return List of Station
     */
    @GET("radius=${MapUtil.PROXIMITY_RADIUS}")
    fun getNearbyStation(@Query("lat") lat: String,
                         @Query("lng") lng: String): Observable<Array<StationLocation>>


    /**
     * get specific Station
     * @userId Station userId
     * @return a specific Station
     */
    @GET("/stations/{userId}")
    fun getStationDetail(@Path("userId")id: String): Observable<Station>
}
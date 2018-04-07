package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.util.MapUtil
import com.parkingreservation.iuh.guest.models.MapResult
import com.parkingreservation.iuh.guest.models.Station
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface MapService {

    @GET("json?radius=${MapUtil.PROXIMITY_RADIUS}&type=hospital&sensor=true&key=${MapUtil.MAP_KEY}")
    fun getNearbyParkingPlace(@Query("location") location: String): Observable<MapResult>

    @GET("/get-station-detail")
    fun getStationDetail(id: String): Observable<Station>
}
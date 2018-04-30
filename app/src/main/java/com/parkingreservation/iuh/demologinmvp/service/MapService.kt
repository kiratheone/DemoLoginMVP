package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.model.Comment
import com.parkingreservation.iuh.demologinmvp.util.MapUtil
import com.parkingreservation.iuh.demologinmvp.model.Station
import com.parkingreservation.iuh.demologinmvp.model.StationLocation
import io.reactivex.Observable
import retrofit2.http.*

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
     * @id Station id
     * @return a specific Station
     */
    @GET("api/stations/{id}")
    fun getStationDetail(@Path("id") id: String, @Header("Authorization") token: String): Observable<Station>

    /**
     * get station image
     * @id Station Id
     * @return list images
     */
    @GET("api/stations/{id}/images")
    fun getStationImage(@Path("id") id: String, @Header("Authorization") token: String): Observable<List<String>>

    /**
     * get station comment
     * @id station id
     * @page number of page, each page have
     * @return list of comment by user
     */
    @GET("api/comments/station/{id}")
    fun getStationComment(@Path("id") id: String
                          , @Query("page") page: Int
                          , @Header("Authorization") token: String): Observable<List<Comment>>
}
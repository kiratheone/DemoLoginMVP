package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileService {

    /**
     * get all of User
     * @return list of User
     */
    @GET("owners")
    fun getAllUser(): Observable<Array<User>>

    /**
     * get specific User
     * @userID User userID
     * @return a User
     */
    @GET("api/drivers/find")
    fun getDriver(@Query("userName")name: String): Observable<User>
}
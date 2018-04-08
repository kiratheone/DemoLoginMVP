package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.model.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface ProfileService {

    /**
     * get all of User
     * @return list of User
     */
    @GET("owners")
    fun getAllUser(): Observable<Array<User>>

    /**
     * get specific User
     * @userId User userId
     * @return a User
     */
    @GET("owners/{userId}")
    fun getUser(@Path("userId")id: String): Observable<User>

}
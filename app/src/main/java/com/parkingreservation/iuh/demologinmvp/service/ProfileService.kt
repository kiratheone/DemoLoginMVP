package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.model.Account
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

/**
* Created by Kushina on 28/03/2018.
*/



interface ProfileService {

    @GET("/profile")
    fun getUserProfile(): Observable<Account>

    @PUT
    fun updateUserProfile(a: Account): Observable<Account>

    @POST
    fun createNewUser(a: Account): Observable<Account>
}
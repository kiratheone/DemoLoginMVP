package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.model.LoginModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

/**
* Created by Kushina on 25/03/2018.
*/

interface LoginService {

    @GET("/users")
    fun getUser(): Observable<List<LoginModel>>

    @POST
    fun signOut(): Observable<LoginModel>

    @POST
    fun register(): Observable<LoginModel>
}
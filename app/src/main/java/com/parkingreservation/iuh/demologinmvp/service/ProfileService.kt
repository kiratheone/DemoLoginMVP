package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.model.User
import io.reactivex.Observable
import retrofit2.http.*

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
    fun getDriver(@Query("userName")name: String, @Header("Authorization") token: String): Observable<User>

    /**
     * update driver detail
     * @id user id
     * @user user data
     * @return a User
     */
    @PUT("api/drivers/{id}")
    fun updateDriver(@Path("id")id: String,@Body user: User): Observable<User>

    /**
     *  create new driver
     *  @user user information
     */
    @POST("api/drivers/")
    fun createDriver(@Body user: User): Observable<User>
}
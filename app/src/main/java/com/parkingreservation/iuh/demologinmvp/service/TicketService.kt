package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.model.Ticket
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TicketService {

    /**
     * get all user's ticket
     * @userId userId of user
     * @return list of user's Ticket
     */
    @GET("tickets/user/{userId}")
    fun getTicketByUser(@Path("userId") id: String): Observable<Array<Ticket>>

    /**
     * get list of Tickets are using
     * @userId: user userId
     * @return: list of Ticket
     */
    @GET("tickets/user/{userId}?status=dkm")
    fun getTicketByUserCurrentlyInUse(@Path("userId") id: String): Observable<Array<Ticket>>

    /**
     * get list of tickets were used
     * @userId user userId
     * @return list of ticket
     */
    @GET("tickets/user/{userId}?status=mkc")
    fun getExpiredTicketByUser(@Path("userId") id: String): Observable<Array<Ticket>>
}
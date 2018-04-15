package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.model.Ticket
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TicketService {

    /**
     * get all user's ticket
     * @userID userID of user
     * @return list of user's Ticket
     */
    @GET("api/tickets/user/{userID}")
    fun getTicketByUser(@Path("userID") id: String): Observable<Array<Ticket>>

    /**
     * get list of Tickets are using
     * @userID: user userID
     * @return: list of Ticket
     */
    @GET("api/tickets/user/{userID}?status=dkm")
    fun getCurrentTicket(@Path("userID") id: String): Observable<Array<Ticket>>

    /**
     * get list of tickets were used
     * @userID user userID
     * @return list of ticket
     */
    @GET("api/tickets/user/{id}?status=Used")
    fun getUsedTicket(@Path("id") id: String): Observable<Array<Ticket>>
}
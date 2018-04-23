package com.parkingreservation.iuh.demologinmvp.service

import com.parkingreservation.iuh.demologinmvp.model.Reservation
import com.parkingreservation.iuh.demologinmvp.model.Ticket
import com.parkingreservation.iuh.demologinmvp.model.Tickets
import io.reactivex.Observable
import retrofit2.http.*

interface TicketService {

    /**
     * get all user's ticket
     * @userID userID of user
     * @return list of user's Ticket
     */
    @GET("api/tickets/user/{userID}")
    fun getTicketByUser(@Path("userID") id: String): Observable<Array<Tickets>>

    /**
     * get list of Tickets are using
     * @userID: user userID
     * @return: list of Ticket
     */
    @GET("api/tickets/user/{userID}?status=Used")
    fun getCurrentTicket(@Path("userID") id: String, @Header("Authorization") token: String): Observable<Array<Tickets>>

    /**
     * get list of tickets were used
     * @userID user userID
     * @return list of ticket
     */
    @GET("api/tickets/user/{id}?status=Used")
    fun getUsedTicket(@Path("id") id: String, @Header("Authorization") token: String): Observable<Array<Tickets>>

    /**
     * Booking a vehicle (Reservation)
     */
    @POST("api/tickets/reservation")
    fun bookParkingLot(@Body tickets: Reservation, @Header("Authorization") token: String): Observable<Tickets>
}
package com.parkingreservation.iuh.demologinmvp.ui.map

import android.util.Log
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.exception.AuthorizationException
import com.parkingreservation.iuh.demologinmvp.model.*
import com.parkingreservation.iuh.demologinmvp.service.MapService
import com.parkingreservation.iuh.demologinmvp.service.TicketService
import com.parkingreservation.iuh.demologinmvp.service.VehicleService
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.USER
import com.parkingreservation.iuh.demologinmvp.util.TokenHandling
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.sql.Date
import java.sql.Timestamp
import javax.inject.Inject
import kotlin.concurrent.thread

class MapPresenter(mapView: MapContract.View) : BasePresenter<MapContract.View>(mapView), MapContract.Presenter {

    companion object {
        var TAG = MapPresenter::class.java.simpleName!!
    }

    @Inject
    lateinit var reservation: TicketService

    @Inject
    lateinit var vehicleService: VehicleService

    @Inject
    lateinit var mapService: MapService

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null

    private var vehicle = emptyList<VehicleModel>()
    private lateinit var currentStation: Station

    override fun onViewCreated() {
//        fakeUser()
        loadUserNav()
        thread {
            loadUserVehicle()
        }
    }

    private fun loadUserVehicle() {
        if (userAlreadyExistOnLocal()) {
            val id = (pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) as User).userID!!
            val token = TokenHandling.getTokenHeader(pref)
            subscription = vehicleService.getVehiclesOfUser(id, token)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe({ this.vehicle = it })
        }
    }

    fun getUserVehicle(): Array<String> {
        val numbers: MutableList<String> = mutableListOf()
        this.vehicle.forEach({ vehicle ->
            numbers.add("${vehicle.name} -  ${vehicle.licensePlate}")
        })
        return numbers.toTypedArray()
    }

    fun getTicketTypes(): Array<String> {
        val numbers: MutableList<String> = mutableListOf()
        this.currentStation.services!!.forEach({ numbers.add(it.serviceName) })
        return numbers.toTypedArray()
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun loadUserNav() {
        if (userAlreadyExistOnLocal())
            view.loadUserHeader(pref.getData(USER, User::class.java)!!)
    }


    override fun loadStationContent(marker: Marker) {
        try {
            val token = TokenHandling.getTokenHeader(pref)
            Log.i(TAG, "token name is: $token")
            loadStation(marker, token)
        } catch (e: AuthorizationException) {
            view.showError(e.message + "User cant access this view")
            Log.w(TAG, e.message)
        }
    }

    private fun loadStation(marker: Marker, token: String) {
        subscription = mapService.getStationDetail(marker.title, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ data ->
                    currentStation = data
                    view.addStationContent(data)
                    Log.i(TAG, "get station successfully")
                }, {
                    Log.w(TAG, "error while loading station from server + ${it.message}")
                    view.showError("Cannot load station")
                    view.addStationContent(null)
                })
    }

    private fun userAlreadyExistOnLocal(): Boolean = pref.getData(USER, User::class.java) != null

    override fun bookParkingLot(station: String, vehiclePosition: Int, type: Int) {
        view.showLoading()
        val userID = (pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) as User).userID!!
        val vehicleID = this.vehicle[vehiclePosition]
        val ticketType = currentStation.ticketTypes[type]
        ticketType.ticketTypeID = ticketType.id
        val res = Reservation(
                paid = false,
                totalPrice = 0,
                userID = userID,
                vehicleID = vehicleID.id,
                stationID = station.toInt(),
                ticketTypeModels = listOf(ticketType)
        )

        val token = TokenHandling.getTokenHeader(pref)
        subscription = reservation.bookParkingLot(res, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        {
                            view.showSuccess("reservation successfully")
                            Log.i(TAG, "ticket post successful")
                        },
                        {
                            view.showError("some thing error")
                            Log.i(TAG, "cant post a ticket ${it.message}")
                        }
                )
    }
}
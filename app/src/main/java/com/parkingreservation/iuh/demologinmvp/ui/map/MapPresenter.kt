package com.parkingreservation.iuh.demologinmvp.ui.map

import android.util.Log
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
import retrofit2.HttpException
import javax.inject.Inject

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
        loadUserVehicle()
    }

    private fun loadUserVehicle() {
        if (loggedIn()) {
            val id = (pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) as User).userID!!
            val token = TokenHandling.getTokenHeader(pref)
            subscription = vehicleService.getVehiclesOfUser(id, token)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            {
                                this.vehicle = it
                            },
                            {
                                if (it is HttpException) {
                                    when (it.response().code()) {
                                        401 -> pref.removeUser()
                                    }
                                }
                            })
        }
    }

    fun getUserVehicle(): Array<String> {
        val numbers: MutableList<String> = mutableListOf()
        this.vehicle.forEach({ vehicle ->
            numbers.add("${vehicle.vehicleTypeModel.typeName} -  ${vehicle.licensePlate}")
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
        if (loggedIn())
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
                .subscribe({
                    currentStation = it
                    view.addStationContent(it)
                    Log.i(TAG, "get station successfully")
                }, {
                    Log.w(TAG, "error while loading station from server + ${it.message}")
                    view.showError("Cannot load station")
                    view.addStationContent(null)
                })
    }

    fun loggedIn(): Boolean = pref.getData(USER, User::class.java) != null

    override fun bookParkingLot(station: String, vehiclePosition: Int, type: Int) {
        view.showLoading()
        val vehicleID = this.vehicle[vehiclePosition]
        val service = currentStation.services!![type]
        var hasService = false
        currentStation.stationVehicleTypes!!.forEach {
            if (it.vehicleTypeId == vehicleID.vehicleTypeModel.typeID) {
                hasService = true
                this.findServiceType(service, vehicleID, it)
                return@forEach
            }
        }
        if (!hasService)
            view.showError("There is no service support this vehicle")
    }

    private fun findServiceType(service: Service, vehicleID: VehicleModel, stationVehicleType: StationVehicleTypes) {
        val token = TokenHandling.getTokenHeader(pref)
        subscription = reservation.findServiceType(service.serviceID, currentStation.id, stationVehicleType.vehicleTypeId, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it != null) {
                        Log.i(TAG, "Station have service for this vehicle")
                        val res = getReservationInfo(it, vehicleID)
                        reserveParkingLot(res, token)
                    } else {
                        Log.w(TAG, "This service not support your vehicle, please try again")
                        view.showError("This service not support your vehicle, please try again")
                    }
                }, {
                    Log.e(TAG, "There is some thing error while find service Type $it")
                    if (it is HttpException) {
                        if (it.code() in 401..499)
                            view.showError("Check your network")
                    } else {
                        view.showError("This service not support your vehicle, please try again")
                    }
                })
    }

    private fun getReservationInfo(it: List<TicketTypeModels>, vehicleID: VehicleModel): Reservation {
        Log.i(TAG, "create a new reservation")
        val userID = (pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) as User).userID!!
        return Reservation(
                paid = false,
                totalPrice = 0,
                userID = userID,
                vehicleID = vehicleID.id,
                stationID = currentStation.id,
                ticketTypeModels = it
        )
    }

    private fun reserveParkingLot(res: Reservation, token: String) {
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
                            Log.e(TAG, "cant post a ticket ${it.message}")
                        }
                )
    }

}
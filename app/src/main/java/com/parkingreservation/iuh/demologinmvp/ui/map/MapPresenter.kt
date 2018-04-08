package com.parkingreservation.iuh.demologinmvp.ui.map

import android.util.Log
import com.google.android.gms.maps.model.Marker
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.LoginModel
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.model.Vehicle
import com.parkingreservation.iuh.demologinmvp.service.LoginService
import com.parkingreservation.iuh.demologinmvp.service.MapService
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.USER
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MapPresenter(mapView: MapContract.View) : BasePresenter<MapContract.View>(mapView), MapContract.Presenter {

    companion object {
        var TAG = MapPresenter::class.java.simpleName!!
    }

    @Inject
    lateinit var loginService: LoginService

    @Inject
    lateinit var mapService: MapService

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null

    override fun onViewCreated() {
        fakeUser()
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun fakeUser() {
        if (!userAlreadyExistOnLocal()) {
            val user = User("02417146-1c56-46ab-8a19-0a90d4b0d6f0", "User Name", "096281088497",
                    "user_97@gmail.com", "Owner Address", emptyArray())
            pref.putData(USER, user, User::class.java)
        }
    }


    override fun loadStationContent(marker: Marker) {
        subscription = mapService.getStationDetail(marker.title)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ data ->
                    view.addStationContent(data)
                    Log.i(TAG, "get station successfully")
                }, {
                    Log.w(TAG, "error while loading station from server + ${it.message}")
                    view.showError("Cannot load station")
                    view.addStationContent(null)
                })
    }

    private fun userAlreadyExistOnLocal(): Boolean = pref.getData(USER, User::class.java) != null


}
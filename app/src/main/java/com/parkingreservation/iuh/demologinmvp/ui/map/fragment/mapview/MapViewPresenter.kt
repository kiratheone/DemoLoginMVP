package com.parkingreservation.iuh.demologinmvp.ui.map.fragment.mapview

import android.util.Log
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.temp.Location
import com.parkingreservation.iuh.demologinmvp.service.MapService
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapViewPresenter(mapView: MapViewContract.View) : BasePresenter<MapViewContract.View>(mapView), MapViewContract.Presenter {

    companion object {
        var TAG = MapViewPresenter::class.java.simpleName!!
    }

    @Inject
    lateinit var mapService: MapService

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null

    override fun onViewCreated() {
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    override fun getNearbyStation(location: Location) {
        var serviceID = pref.getData(MySharedPreference.SharedPrefKey.SERVICE_TYPE, Int::class.java)
        serviceID = if(serviceID == null) 1 else serviceID
        mapService.findNearbyStation(location.lat.toString(), location.lng.toString(), serviceID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    data ->
                    view.loadNearbyStation(data)
                    Log.i(TAG, "loaded nearby data successfully")
                },{
                    Log.w(TAG, "some thing error, cant load data from server, check log for more detail")
                    Log.w(TAG, "${it.message}")
                })
    }

}
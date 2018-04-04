package com.parkingreservation.iuh.demologinmvp.ui.map.fragment.mapview

import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MapViewPresenter(mapView: MapViewContract.View) : BasePresenter<MapViewContract.View>(mapView) {

    companion object {
        var TAG = MapViewPresenter::class.java.simpleName!!
    }

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null

    override fun onViewCreated() {
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}
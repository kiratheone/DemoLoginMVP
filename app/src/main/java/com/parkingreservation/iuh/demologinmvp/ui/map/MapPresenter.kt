package com.parkingreservation.iuh.demologinmvp.ui.map

import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginContract
import io.reactivex.disposables.Disposable

class MapPresenter(mapView: MapContract.View) : BasePresenter<MapContract.View>(mapView), LoginContract.Presenter {

    private
    var subscription: Disposable? = null

    override fun onViewCreated() {
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    override fun signIn(name: String, pass: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
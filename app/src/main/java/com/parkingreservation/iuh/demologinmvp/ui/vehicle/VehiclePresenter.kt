package com.parkingreservation.iuh.demologinmvp.ui.vehicle

import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class VehiclePresenter(view: VehicleContract.View): BasePresenter<VehicleContract.View>(view), VehicleContract.Presenter {

    companion object {
        var TAG = VehiclePresenter::class.java.simpleName!!
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
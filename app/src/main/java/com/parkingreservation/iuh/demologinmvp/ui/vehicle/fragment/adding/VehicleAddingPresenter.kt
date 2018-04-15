package com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.adding

import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class VehicleAddingPresenter(view: VehicleAddingContract.View): BasePresenter<VehicleAddingContract.View>(view), VehicleAddingContract.Presenter {

    companion object {
        var TAG = VehicleAddingPresenter::class.java.simpleName
    }

    private
    var subscription: Disposable? = null

    @Inject
    lateinit var pref: MySharedPreference

    override fun onViewCreated() {
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}
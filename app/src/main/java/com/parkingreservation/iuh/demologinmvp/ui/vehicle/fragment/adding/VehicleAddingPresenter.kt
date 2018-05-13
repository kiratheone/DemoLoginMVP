package com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.adding

import android.util.Log
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.model.Vehicle
import com.parkingreservation.iuh.demologinmvp.model.VehicleModel
import com.parkingreservation.iuh.demologinmvp.model.VehicleTypes
import com.parkingreservation.iuh.demologinmvp.service.VehicleService
import com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.history.TicketHistoryPresenter
import com.parkingreservation.iuh.demologinmvp.ui.vehicle.VehicleTypeUtil
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.USER
import com.parkingreservation.iuh.demologinmvp.util.TokenHandling
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class VehicleAddingPresenter(view: VehicleAddingContract.View): BasePresenter<VehicleAddingContract.View>(view), VehicleAddingContract.Presenter {
    companion object {
        var TAG = VehicleAddingPresenter::class.java.simpleName
    }

    private
    var subscription: Disposable? = null

    @Inject
    lateinit var pref: MySharedPreference

    @Inject
    lateinit var vehicleService: VehicleService

    override fun saveVehicle(vehicle: VehicleModel) {
        view.showLoading()
        Log.i(TAG, "saving... vehicle of user")
        if(isLoggedIn()) {
            val id = getUserId()
            val token =  TokenHandling.getTokenHeader(pref)
            vehicle.driverID = id!!
            subscription = vehicleService.addVehicle(vehicle, token)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnTerminate { view.hideLoading() }
                    .subscribe(
                            {
                                Log.i(TAG, "add vehicle successfully")
                                view.showSuccess("Thêm xe thành công")
                            },
                            {
                                view.showError("oOps!!, there is some error from server, pls try again")
                                Log.w(TAG, "There is some thing error while creating Vehicle ${it.message}")
                            }
                    )
        } else {
            view.showError("Hey!!, You are not logged in yet")
            Log.w(TicketHistoryPresenter.TAG, "User are not logged in yet")
        }
    }

    fun getListVehicleType(): MutableList<VehicleTypes> {
        val list = mutableListOf<VehicleTypes>()
        val listVehicle = VehicleTypeUtil.getVehicleType()

        var i = 0
        for((key, value) in listVehicle) {
            ++i
            list.add(VehicleTypes(value.second, key, value.first))
        }

        return list
    }

    private fun getUserId(): String? {
        return (pref.getData(USER, User::class.java) as User).userID!!
    }


    override fun onViewCreated() {
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun isLoggedIn(): Boolean = pref.getData(USER, User::class.java) != null
}
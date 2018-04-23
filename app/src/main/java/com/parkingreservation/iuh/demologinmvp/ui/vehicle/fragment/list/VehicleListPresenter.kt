package com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.list

import android.util.Log
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.exception.AuthorizationException
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.service.VehicleService
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail.ProfilePresenter
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginPresenter
import com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.history.TicketHistoryPresenter
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.TokenHandling
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class VehicleListPresenter(view: VehicleListContract.View) : BasePresenter<VehicleListContract.View>(view), VehicleListContract.Presenter {

    companion object {
        var TAG = VehicleListPresenter::class.java.simpleName
    }

    @Inject
    lateinit var pref: MySharedPreference

    @Inject
    lateinit var vehicleService: VehicleService

    private
    var subscription: Disposable? = null


    override fun onViewCreated() {
        loadUserVehicle()
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun loadUserVehicle() {
        view.showLoading()
        Log.i(TAG, "loading user vehicle")
        if (isLoggedIn()) {
            val id = (pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) as User).userID!!
            loadVehicleIncludedToken(id)
        } else {
            view.showError("Hey!!, You are not logged in yet")
            Log.w(TicketHistoryPresenter.TAG, "User are not logged in yet")
        }
    }

    private fun loadVehicleIncludedToken(id: String) {
        try {
            val token = TokenHandling.getTokenHeader(pref)
            Log.i(TAG, "token name is: $token")
            loadVehicle(id, token)
        } catch (e: AuthorizationException) {
            view.showError(e.message + "User cant access this view")
            Log.w(TAG, e.message)
        }

    }

    private fun loadVehicle(id: String, token: String) {
        subscription = vehicleService.getVehiclesOfUser(id, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { vehicles ->
                            view.updateVehicle(vehicles)
                            Log.i(TAG, "update vehicle successfully")
                        },
                        {
                            view.showError("oOps!!, there is some error from server, pls try again")
                            Log.w(TAG, "There is some thing error while loading Ticket ${it.message}")
                        }
                )
    }

    private fun isLoggedIn(): Boolean = pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) != null
}
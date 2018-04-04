package com.parkingreservation.iuh.demologinmvp.ui.map

import android.util.Log
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.LoginModel
import com.parkingreservation.iuh.demologinmvp.service.LoginService
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginContract
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MapPresenter(mapView: MapContract.View) : BasePresenter<MapContract.View>(mapView), LoginContract.Presenter {

    companion object {
        var TAG = MapPresenter::class.java.simpleName!!
    }

    @Inject
    lateinit var loginService: LoginService

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null

    override fun onViewCreated() {
        loadUserHeader()

    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    override fun signIn(name: String, pass: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loadUserHeader() {
        if (userAlreadyExistOnLocal()) {
            Log.i(TAG, "User already logged in client")
            view.loadUserHeader(pref.getData(SharedPrefKey.USER, LoginModel::class.java)!!)

        } else {
            Log.i(TAG, "There is no user on client, so have to load user")
            subscription = loginService.getUser()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            { data ->
                                view.loadUserHeader(user = data[0])
                                pref.putData(SharedPrefKey.USER, data[0], LoginModel::class.java)
                            },
                            { Log.w(TAG, "error while loading user from db") }
                    )
        }
    }

    private fun userAlreadyExistOnLocal(): Boolean
            = pref.getData(SharedPrefKey.USER, LoginModel::class.java) != null


}
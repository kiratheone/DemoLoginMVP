package com.parkingreservation.iuh.demologinmvp.ui.login

import android.util.Log
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.LoginModel
import com.parkingreservation.iuh.demologinmvp.service.LoginService
import com.parkingreservation.iuh.demologinmvp.ui.map.MapPresenter
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenter(loginView: LoginContract.View) : BasePresenter<LoginContract.View>(loginView), LoginContract.Presenter {

    companion object {
        var TAG = MapPresenter::class.java.simpleName
    }

    @Inject
    lateinit var loginService: LoginService

    private
    var subscription: Disposable? = null

    @Inject
    lateinit var pref: MySharedPreference

    override fun onViewCreated() {
//        loadUser()
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    override fun signIn(name: String, pass: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /*private fun loadUser() {
        if (userAlreadyExistOnLocal()) {
            Log.i(TAG, "User already logged in client")
            val userPref = pref.getData(SharedPrefKey.USER, LoginModel::class.java) as LoginModel
            view.updateUser(List(1) { userPref })
        } else {
            Log.i(MapPresenter.TAG, "There is no user on client, so have to load user")
            subscription = loginService.getUser()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            { data ->
                                view.updateUser(data)
                                pref.putData(SharedPrefKey.USER, data[0], LoginModel::class.java)
                            },
                            { Log.w(TAG, "error while loading user from db") }
                    )
        }
    }*/

    private fun userAlreadyExistOnLocal(): Boolean
            = pref.getData(SharedPrefKey.USER, LoginModel::class.java) != null
}
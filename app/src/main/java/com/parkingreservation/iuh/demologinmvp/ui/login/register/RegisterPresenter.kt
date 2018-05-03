package com.parkingreservation.iuh.demologinmvp.ui.login.register

import android.util.Log
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.service.LoginService
import com.parkingreservation.iuh.demologinmvp.service.ProfileService
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegisterPresenter(view: RegisterContract.View) : BasePresenter<RegisterContract.View>(view), RegisterContract.Presenter {

    companion object {
        val TAG = RegisterPresenter::class.java.simpleName
    }

    @Inject
    lateinit var loginService: LoginService

    @Inject
    lateinit var profileService: ProfileService

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    override fun registerAccount(user: User) {
        view.showLoading()
        profileService.createDriver(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        {
                            if (it != null) {
                                Log.i(TAG, "register user successful")
                                view.showSuccess("register user successful")
                                view.onRegisterSuccess()
                            } else {
                                Log.i(TAG, "something error from user")
                                view.showError("error from server, pls try again later")
                            }
                        },
                        {
                            Log.e(TAG, "some thing error check: ${it.message}")
                            view.showError("oOps!!!, Something error while logging In, please check your network ")
                        }
                )
    }
}
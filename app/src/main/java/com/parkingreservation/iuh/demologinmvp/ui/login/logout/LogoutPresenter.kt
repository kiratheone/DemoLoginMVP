package com.parkingreservation.iuh.demologinmvp.ui.login.logout

import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import javax.inject.Inject

class LogoutPresenter(view: LogoutContract.View): BasePresenter<LogoutContract.View>(view), LogoutContract.Presenter {


    companion object {
        var TAG = LogoutPresenter::class.java.simpleName
    }

    @Inject
    lateinit var pref: MySharedPreference

    override fun signOut() {
        pref.removeUser()
        view.onSignOutCompleted()
    }
}
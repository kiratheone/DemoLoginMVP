package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit

import android.util.Log
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.LoginModel
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.service.ProfileService
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.USER
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.USER_PROFILE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditingProfilePresenter(view: EditingProfileContract.View) : BasePresenter<EditingProfileContract.View>(view)
        , EditingProfileContract.Presenter {

    companion object {
        var TAG = EditingProfilePresenter::class.java.simpleName
    }

    @Inject
    lateinit var profileService: ProfileService

    @Inject
    lateinit var pref: MySharedPreference
    private
    var subscription: Disposable? = null

    override fun onViewCreated() {
        loadProfile()
    }

    private fun loadProfile() {
        if(isLoggedIn()) {
            val id = (pref.getData(USER, User::class.java) as User).userID
            if (userAlreadyExistOnLocal()) {
                loadLocalProfile()
            } else {
                loadServerProfile(id)
            }
        } else {
            Log.w(TAG, "user are not logged in")
            view.showError("Hey!!, You are not logged in yet")
        }
    }

    private fun loadLocalProfile() {
        Log.i(TAG, "on local profile loading")
        val userPref = pref.getData(USER, User::class.java) as User
        view.transferProfile(userPref)
    }

    private fun loadServerProfile(id: String) {
        Log.i(TAG, "on server profile loading")
        view.showLoading()
        profileService.getDriver(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { data ->
                            view.transferProfile(data)
                            pref.putData(USER, data, User::class.java)
                            Log.i(TAG, "get user successfully")
                        },
                        {view.showError("oOps!!, there is some error from server, pls try again")}
                )
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun isLoggedIn(): Boolean = pref.getData(USER, User::class.java) != null
    private fun userAlreadyExistOnLocal(): Boolean = pref.getData(USER, LoginModel::class.java) != null
}
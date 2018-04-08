package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail

import android.util.Log
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.LoginModel
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.service.ProfileService
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfilePresenter(profileView: ProfileContract.View) : BasePresenter<ProfileContract.View>(profileView)
        , ProfileContract.Presenter {

    companion object {
        var TAG = ProfilePresenter::class.java.simpleName
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
            val id = (pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) as User).userId
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
        val userPref = pref.getData(MySharedPreference.SharedPrefKey.USER_PROFILE, User::class.java) as User
        view.transferProfile(userPref)
    }

    private fun loadServerProfile(id: String) {
        Log.i(TAG, "on server profile loading")
        profileService.getUser(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { data ->
                            view.transferProfile(data)
                            pref.putData(MySharedPreference.SharedPrefKey.USER_PROFILE, data, User::class.java)
                            Log.i(TAG, "get user successfully")
                        },
                        {view.showError("oOps!!, there is some error from server, pls try again")}
                )
    }


    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun isLoggedIn(): Boolean = pref.getData(MySharedPreference.SharedPrefKey.USER, LoginModel::class.java) != null
    private fun userAlreadyExistOnLocal(): Boolean = pref.getData(MySharedPreference.SharedPrefKey.USER_PROFILE, LoginModel::class.java) != null
}
package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail

import android.util.Log
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.exception.AuthorizationException
import com.parkingreservation.iuh.demologinmvp.model.LoginModel
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.service.ProfileService
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.TokenHandling
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
        view.showLoading()
        if (isLoggedIn()) {
            if (userAlreadyExistOnLocal()) {
                loadLocalProfile()
            } else {
            }
        } else {
            Log.w(TAG, "user are not logged in")
            view.showError("Hey!!, You are not logged in yet")
        }
        view.hideLoading()
    }

    private fun loadLocalProfile() {
        Log.i(TAG, "on local profile loading")
        val userPref = pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) as User
        view.transferProfile(userPref)
    }

    private fun loadServerProfile(id: String) {
        view.showLoading()
        Log.i(TAG, "on server profile loading")
        try {
            val token = TokenHandling.getTokenHeader(pref)
            saveDriverToPref(id, token)
        } catch (e: AuthorizationException) {
            view.showError(e.message + "User cant access this view")
            Log.w(TAG, e.message)
        }
    }

    private fun saveDriverToPref(id: String, token: String) {
        profileService.getDriver(id, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { data ->
                            view.transferProfile(data)
                            pref.putData(MySharedPreference.SharedPrefKey.USER, data, User::class.java)
                            Log.i(TAG, "get user successfully")
                        },
                        {
                            view.transferProfile(emptyUser())
                            view.showError("oOps!!, there is some error from server, pls try again")
                            Log.i(TAG, "error while loading profile ${it.message}")
                        }
                )
    }

    private fun emptyUser(): User {
        val l = view.getContexts().resources.getString(R.string.loading)
        return User(l, l, l, 0, emptyList(), l)
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun isLoggedIn(): Boolean = pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) != null
    private fun userAlreadyExistOnLocal(): Boolean = pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) != null
}
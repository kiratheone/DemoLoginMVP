package com.parkingreservation.iuh.demologinmvp.ui.login

import android.util.Base64
import android.util.Log
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.exception.AuthorizationException
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.model.UserAccessToken
import com.parkingreservation.iuh.demologinmvp.service.LoginService
import com.parkingreservation.iuh.demologinmvp.service.ProfileService
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail.ProfilePresenter
import com.parkingreservation.iuh.demologinmvp.ui.map.MapPresenter
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.TokenHandling
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

import javax.inject.Inject
import kotlin.concurrent.thread

class LoginPresenter(loginView: LoginContract.View) : BasePresenter<LoginContract.View>(loginView), LoginContract.Presenter {

    companion object {
        var TAG = MapPresenter::class.java.simpleName
    }

    @Inject
    lateinit var loginService: LoginService

    @Inject
    lateinit var profileService: ProfileService

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null


    override fun onViewCreated() {
//        loadUser()
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    override fun signIn(name: String, pass: String) {
        view.showLoading()

        val base = LoginUtil.LOGIN_CLIENT + ":" + LoginUtil.LOGIN_PRIVATE
        val authHeader = "Basic " + Base64.encodeToString(base.toByteArray(), Base64.NO_WRAP) // "Basic " required by Header

        loginService.signIn(authHeader, name, pass)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { data ->
                            if (data != null) {
                                this.saveUserTokenPref(data)
                                saveUserProfile(name)
                            }
                        },
                        {
                            Log.e(TAG, "Cannot login ${it.message}")
                            if (it is HttpException)
                                if (it.code() == 400)
                                    view.showError("User Name or Password is Wrong")
                            view.showError("oOps!!!, Something error while logging In, please check your network ")
                            pref.removeUser()
                        })
    }

    private fun saveUserTokenPref(data: UserAccessToken) {
        Log.i(TAG, "Saving... User Token Pref")
        pref.putData(MySharedPreference.SharedPrefKey.TOKEN, data, UserAccessToken::class.java)
    }

    private fun saveUserProfile(name: String) {
        Log.i(TAG, "Saving... user profile by user name: $name")

        try {
            val token = TokenHandling.getTokenHeader(pref)
            Log.i(TAG, "token name is: $token")
            saveDriverToPref(name, token)
        } catch (e: AuthorizationException) {
            view.showError(e.message + "User cant access this view")
            Log.w(ProfilePresenter.TAG, e.message)
        }
    }

    private fun saveDriverToPref(id: String, token: String) {
        profileService.getDriver(id, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { data ->
                            Log.i(TAG, "load user successful with user name: ${data.driverName}")
                            saveUserPref(data)
                            view.onLoginSuccessfully()
                            view.showSuccess("Đăng Nhập thành công")
                        },
                        {
                            it.printStackTrace()
                            Log.e(TAG, "Cannot get user from server after user logged In successfully, the reason is: ${it.message}")
                        }
                )
    }


    private fun saveUserPref(user: User) {
        pref.putData(MySharedPreference.SharedPrefKey.USER, user, User::class.java)
        Log.i(TAG, "Saving User Pref Successfully")
    }

}
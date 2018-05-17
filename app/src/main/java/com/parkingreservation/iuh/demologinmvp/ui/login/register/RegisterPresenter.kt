package com.parkingreservation.iuh.demologinmvp.ui.login.register

import android.util.Base64
import android.util.Log
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.exception.AuthorizationException
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.model.UserAccessToken
import com.parkingreservation.iuh.demologinmvp.service.LoginService
import com.parkingreservation.iuh.demologinmvp.service.ProfileService
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail.ProfilePresenter
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginPresenter
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginUtil
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.TokenHandling
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
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
        validationMail(user)
    }

    private fun validationMail(user: User) {
        val tk = "Bearer 122242be-636f-4ecb-ac87-da83ed3397dc"
        subscription = profileService.getDriver(user.email, tk)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            view.hideLoading()
                            view.showError("Email đã bị trùng")
                        },
                        {
                            validationPhone(user, tk)
                        }
                )
    }

    private fun validationPhone(user: User, tk: String) {
        profileService.getDriver(user.phoneNumber!!, tk)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            view.hideLoading()
                            view.showError(view.getContexts().getString(R.string.phone_exist))
                        },
                        {
                            createDriver(user)
                        }
                )
    }

    private fun createDriver(user: User) {
        subscription = profileService.createDriver(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        {
                            if (it != null) {
                                Log.i(TAG, "register user successful")
                                signIn(it.email, user.password!!)
                            } else {
                                Log.i(TAG, "something error from user")
                                view.showError("Đã xảy ra lỗi, xin thử lại")
                            }
                        },
                        {
                            Log.e(TAG, "some thing error check: ${it.message}")
                            view.showError("oOps!!!, Something error while logging In, please check your network ")
                        }
                )
    }

    fun signIn(name: String, pass: String) {
        val base = LoginUtil.LOGIN_CLIENT + ":" + LoginUtil.LOGIN_PRIVATE
        val authHeader = "Basic " + Base64.encodeToString(base.toByteArray(), Base64.NO_WRAP) // "Basic " required by Header

        subscription = loginService.signIn(authHeader, name, pass)
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
                            Log.e(LoginPresenter.TAG, "Cannot login ${it.message}")
                            if (it is HttpException)
                                if (it.code() == 400)
                                    view.showError("Tên Đăng Nhập hoặc Mật khẩu sai")
                            view.showError("oOps!!!, Something error while logging In, please check your network ")
                            pref.removeUser()
                            view.hideLoading()
                        })
    }

    private fun saveUserTokenPref(data: UserAccessToken) {
        Log.i(LoginPresenter.TAG, "Saving... User Token Pref")
        pref.putData(MySharedPreference.SharedPrefKey.TOKEN, data, UserAccessToken::class.java)
    }

    private fun saveUserProfile(name: String) {
        Log.i(LoginPresenter.TAG, "Saving... user profile by user name: $name")

        try {
            val token = TokenHandling.getTokenHeader(pref)
            Log.i(LoginPresenter.TAG, "token name is: $token")
            saveDriverToPref(name, token)
        } catch (e: AuthorizationException) {
            view.showError(e.message + "User cant access this view")
            Log.w(ProfilePresenter.TAG, e.message)
            view.hideLoading()
        }
    }

    private fun saveDriverToPref(id: String, token: String) {
        subscription = profileService.getDriver(id, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        { data ->
                            Log.i(LoginPresenter.TAG, "load user successful with user name: ${data.driverName}")
                            saveUserPref(data)
                            view.onRegisterSuccess()
                        },
                        {
                            it.printStackTrace()
                            Log.e(LoginPresenter.TAG, "Cannot get user from server after user logged In successfully, the reason is: ${it.message}")
                        }
                )
    }


    private fun saveUserPref(user: User) {
        pref.putData(MySharedPreference.SharedPrefKey.USER, user, User::class.java)
        Log.i(LoginPresenter.TAG, "Saving User Pref Successfully")
    }

}
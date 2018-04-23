package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit

import android.util.Log
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.LoginModel
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.service.ProfileService
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.USER
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.concurrent.thread

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

    override fun editDriver(driver: User) {
        view.showLoading()
        val id = getUserId()
        if (id != null) {
            driver.userID = id
            profileService.updateDriver(id, driver)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnTerminate { view.hideLoading() }
                    .subscribe(
                            { user ->
                              if(user != null)  {
                                  thread {
                                      updateUserProfilePref(user)
                                  }
                                  view.showSuccess("Edit user successfully")
                                  view.onEditSuccess()
                              }
                            },
                            {
                                view.showError("oOps!!, some thing error while updating, check your network")
                                Log.e(TAG, "error while updating: ${it.message}")
                            }
                    )
        } else {
            view.showError("oOps!!, some thing error while updating, please login again")
            Log.e(TAG, "User are not logged in yet")
        }
    }

    private fun updateUserProfilePref(user: User) {
        pref.putData(USER, user, User::class.java)
    }

    fun getUserId(): String? {
        return (pref.getData(USER, User::class.java) as User).userID
    }

    private fun loadProfile() {
        if (isLoggedIn()) {
            if (userAlreadyExistOnLocal()) {
                loadLocalProfile()
            } else {

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

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun isLoggedIn(): Boolean = pref.getData(USER, User::class.java) != null
    private fun userAlreadyExistOnLocal(): Boolean = pref.getData(USER, LoginModel::class.java) != null
}
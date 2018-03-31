package com.parkingreservation.iuh.demologinmvp.ui.login

import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.service.LoginService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenter(loginView: LoginContract.View) : BasePresenter<LoginContract.View>(loginView), LoginContract.Presenter {

    @Inject
    lateinit var loginService: LoginService

    private
    var subscription: Disposable? = null

    override fun onViewCreated() {
        loadUser()
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    override fun signIn(name: String, pass: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loadUser() {
        subscription = loginService.getUser()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {data -> view.updateUser(data)},
                        {}
                )
    }
}
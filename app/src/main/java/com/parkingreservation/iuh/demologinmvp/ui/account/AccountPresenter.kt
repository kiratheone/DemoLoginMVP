package com.parkingreservation.iuh.demologinmvp.ui.account

import com.parkingreservation.iuh.demologinmvp.base.BasePresenter

class AccountPresenter(view: AccountContract.View) : BasePresenter<AccountContract.View>(view), AccountContract.Presenter {

    companion object {
        var TAG = AccountPresenter::class.java.simpleName
    }

    override fun onViewCreated() {
        super.onViewCreated()
    }

    override fun onViewDestroyed() {
        super.onViewDestroyed()
    }
}
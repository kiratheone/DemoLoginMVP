package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.history

import android.util.Log
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.exception.AuthorizationException
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.service.TicketService
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail.ProfilePresenter
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginPresenter
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.USER
import com.parkingreservation.iuh.demologinmvp.util.TokenHandling
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TicketHistoryPresenter(view: TicketHistoryContract.View): BasePresenter<TicketHistoryContract.View>(view), TicketHistoryContract.Presenter {

    companion object {
        var TAG = TicketHistoryPresenter::class.java.simpleName
    }

    @Inject
    lateinit var ticketService: TicketService

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null

    override fun onViewCreated() {
        loadTicketHistory()
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun loadTicketHistory() {
        view.showLoading()
        Log.i(TAG, "loading ticket history")
        if(isLoggedIn()) {
            val id = (pref.getData(USER, User::class.java) as User).userID!!
           loadTicketIncludeToken(id)
        } else {
            view.showError("Hey!!, You are not logged in yet")
            Log.w(TAG, "User are not logged in yet")
        }
    }

    private fun loadTicketIncludeToken(id: String) {
        try {
            val token = TokenHandling.getTokenHeader(pref)
            Log.i(TAG, "token name is: $token")
            loadTicket(id, token)
        } catch (e: AuthorizationException) {
            view.showError(e.message + "User cant access this view")
            Log.w(TAG, e.message)
        }
    }

    private fun loadTicket(id: String, token: String) {
        ticketService.getUsedTicket(id, token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnTerminate { view.hideLoading() }
                .subscribe(
                        {data ->
                            view.loadHistoryTicket(data)
                            Log.i(TAG, "load ticket history successfully")
                        },
                        {
                            view.showError("oOps!!, there is some error from server, pls try again")
                            Log.w(TAG, "There is some thing error while loading Ticket ${it.message}")
                        }
                )
    }

    private fun isLoggedIn(): Boolean = pref.getData(USER, User::class.java) != null
}
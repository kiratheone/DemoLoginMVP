package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.detail

import android.util.Log
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.service.TicketService
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.USER
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TicketDetailPresenter(view: TicketDetailContract.View): BasePresenter<TicketDetailContract.View>(view), TicketDetailContract.Presenter {

    companion object {
        var TAG = TicketDetailPresenter::class.java.simpleName
    }

    @Inject
    lateinit var ticketService: TicketService

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null

    override fun onViewCreated() {
        loadTicketDetail()
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun loadTicketDetail() {
        Log.i(TAG, "loading ticket ")
        if(isLoggedIn()) {
            val id = (pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) as User).userId
            ticketService.getTicketByUserCurrentlyInUse(id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            {data ->
                                view.loadTicketDetail(data[0])
                                Log.i(TAG, "load ticket history successfully")
                            },
                            {
                                view.showError("oOps!!, there is some error from server, pls try again")
                            }
                    )
        } else {
            view.showError("Hey!!, You are not logged in yet")
        }
    }

    private fun isLoggedIn(): Boolean = pref.getData(USER, User::class.java) != null
}
package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.detail

import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class TicketDetailPresenter(view: TicketDetailContract.View): BasePresenter<TicketDetailContract.View>(view), TicketDetailContract.Presenter {

    companion object {
        var TAG = TicketDetailPresenter::class.java.simpleName
    }

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null

    override fun onViewCreated() {
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}
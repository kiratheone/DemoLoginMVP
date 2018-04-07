package com.parkingreservation.iuh.demologinmvp.ui.ticket

import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.ui.map.MapPresenter
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class TicketPresenter(view: TicketConstract.View): BasePresenter<TicketConstract.View>(view)
                        , TicketConstract.Presenter {
    companion object {
        var TAG = MapPresenter::class.java.simpleName
    }

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null


    override fun onViewCreated() {
        loadLastTicket()
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun loadLastTicket() {

    }

}
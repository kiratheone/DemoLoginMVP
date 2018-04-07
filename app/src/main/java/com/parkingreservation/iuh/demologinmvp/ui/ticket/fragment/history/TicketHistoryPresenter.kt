package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.history

import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.guest.models.TicketHistory
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class TicketHistoryPresenter(view: TicketHistoryContract.View): BasePresenter<TicketHistoryContract.View>(view), TicketHistoryContract.Presenter {

    companion object {
        var TAG = TicketHistoryPresenter::class.java.simpleName
    }

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
        var tickets = mutableListOf<TicketHistory>()
        tickets.add(TicketHistory("01/01/2017", "Bao Loc", "Xe 4 Banh"))
        tickets.add(TicketHistory("11/07/2017", "Thau Loc", "Xe 2 Banh"))
        tickets.add(TicketHistory("01/12/2007", "Bao Loc", "Xe 4 Banh"))
        tickets.add(TicketHistory("01/12/2007", "Bao Loc", "Xe 4 Banh"))
        tickets.add(TicketHistory("01/12/2007", "Bao Loc", "Xe 4 Banh"))
        tickets.add(TicketHistory("01/12/2007", "Bao Loc", "Xe 4 Banh"))
        tickets.add(TicketHistory("01/12/2007", "Bao Loc", "Xe 4 Banh"))
        view.loadHistoryTicket(tickets)
    }
}
package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.history

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.Tickets

class TicketHistoryContract {
    interface Presenter {
    }

    interface View: BaseView {
        fun loadHistoryTicket(tickets: Array<Tickets>)
    }
}
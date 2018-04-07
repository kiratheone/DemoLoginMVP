package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.detail

import com.parkingreservation.iuh.demologinmvp.base.BaseView

class TicketDetailContract {
    interface Presenter {

    }

    interface View: BaseView {
        fun loadTicketDetail()
    }
}
package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.checked

import android.graphics.Bitmap
import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.Tickets

class TicketCheckedContract {
    interface Presenter {
    }

    interface View: BaseView {
        fun loadTicketDetail(tickets: Array<Tickets>)
        fun setQrCodeView(bitmap: Bitmap)
    }
}
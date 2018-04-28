package com.parkingreservation.iuh.demologinmvp.ui.login.logout

import com.parkingreservation.iuh.demologinmvp.base.BaseView

class LogoutContract {
    interface Presenter {
        fun signOut()
    }

    interface View : BaseView {
        fun onSignOutCompleted()
    }
}
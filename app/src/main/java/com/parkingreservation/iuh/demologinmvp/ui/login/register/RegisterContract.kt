package com.parkingreservation.iuh.demologinmvp.ui.login.register

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.User

class RegisterContract {
    interface Presenter {
        fun registerAccount(user: User)
    }

    interface View: BaseView {
        fun onRegisterSuccess()
    }
}

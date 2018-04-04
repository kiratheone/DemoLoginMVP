package com.parkingreservation.iuh.demologinmvp.ui.map

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.LoginModel

class MapContract {
    interface Presenter {
        fun signIn(name: String, pass: String)
    }

    interface View : BaseView {
        fun loadUserHeader(user: LoginModel)

    }
}


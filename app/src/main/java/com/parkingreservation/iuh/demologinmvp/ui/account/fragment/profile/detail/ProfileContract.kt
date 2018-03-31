package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.Account

class ProfileContract {
    interface Presenter {

    }

    interface View : BaseView {
        fun transferProfile(profile: Account)
    }
}
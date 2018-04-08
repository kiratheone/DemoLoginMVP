package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.User

class ProfileContract {
    interface Presenter {

    }

    interface View : BaseView {
        fun transferProfile(profile: User)
    }
}
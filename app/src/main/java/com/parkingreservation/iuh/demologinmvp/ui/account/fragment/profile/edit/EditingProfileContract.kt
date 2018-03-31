package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.Account

class EditingProfileContract {
    interface Presenter {

    }

    interface View : BaseView {
        fun transferProfile(profile: Account)
    }
}
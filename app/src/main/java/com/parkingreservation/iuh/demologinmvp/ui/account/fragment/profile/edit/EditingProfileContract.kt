package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.User

class EditingProfileContract {
    interface Presenter {
//        fun editProfile(profile: User)
    }

    interface View : BaseView {
        /**
         *  pass profile from server or local to view
         */
        fun transferProfile(profile: User)
    }
}
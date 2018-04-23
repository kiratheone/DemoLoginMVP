package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.User

class EditingProfileContract {
    interface Presenter {
        fun editDriver(driver: User)
    }

    interface View : BaseView {
        /**
         *  pass profile from server or local to view
         */
        fun transferProfile(profile: User)

        /**
         * happening after the success
         * back to home
         */
        fun onEditSuccess()
    }
}
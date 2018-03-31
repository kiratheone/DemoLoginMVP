package com.parkingreservation.iuh.demologinmvp.ui.login

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.model.LoginModel

/**
* Created by Kushina on 27/03/2018.
*/

class LoginContract {
    interface Presenter {
        fun signIn(name: String, pass: String)
    }

    interface View : BaseView {
        /**
         *  Display an snackbar in the view
         */
        fun onLoginSuccessfully()

        /**
         *  Get name from user field
         */
        fun getUserName() : String

        /**
         * Get pas from password field
         */
        fun getPassword() : String

        /**
         * update the previous user by the specified ones
         * @param users the list of user
         */
        fun updateUser(users: List<LoginModel>)
    }
}


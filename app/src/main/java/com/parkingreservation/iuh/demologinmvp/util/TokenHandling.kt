package com.parkingreservation.iuh.demologinmvp.util

import com.parkingreservation.iuh.demologinmvp.exception.AuthorizationException
import com.parkingreservation.iuh.demologinmvp.model.UserAccessToken

class TokenHandling {
    companion object {
        fun getTokenHeader(pref: MySharedPreference): String {
            if (pref.getData(MySharedPreference.SharedPrefKey.TOKEN, UserAccessToken::class.java) != null) {
                val token = pref.getData(MySharedPreference.SharedPrefKey.TOKEN, UserAccessToken::class.java)?.access_token
                return "Bearer $token"
            }
            throw AuthorizationException("access denied")
        }
    }
}
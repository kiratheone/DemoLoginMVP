package com.parkingreservation.iuh.demologinmvp.util

import android.content.SharedPreferences
import android.util.Log
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.TOKEN
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.USER
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.USER_PROFILE
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.VEHICLES
import javax.inject.Inject


class MySharedPreference @Inject constructor(private val mSharedPreferences: SharedPreferences) {

    companion object {
        var TAG = MySharedPreference::class.java.simpleName!!
    }

    fun <T> putData(key: String, value: T, type: Class<T>) {
        mSharedPreferences.edit().putString(key, DataJsonConverter(type).toJson(value)).apply()
        Log.i(TAG, "put data to pref successfully")
    }

    fun <T> getData(key: String, type: Class<T>): T? {
        Log.i(TAG, "get data from pref with key = $key")
        return DataJsonConverter(type).toObject(mSharedPreferences.getString(key, ""))
    }

    fun removeUser() {
        Log.i(TAG, "remove authorization method")
        mSharedPreferences.edit().remove(USER).apply()
        mSharedPreferences.edit().remove(TOKEN).apply()
        mSharedPreferences.edit().remove(VEHICLES).apply()
        mSharedPreferences.edit().remove(USER_PROFILE).apply()
    }

    class SharedPrefKey {
        companion object {
            const val USER = "user"
            const val TOKEN = "token"
            const val VEHICLES = "vehicles"
            const val DATA_STORE = "data store"
            const val USER_PROFILE = "user_profile"
        }
    }
}

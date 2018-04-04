package com.parkingreservation.iuh.demologinmvp.util

import android.util.Log
import com.squareup.moshi.Moshi


class DataJsonConverter<T>(var typeParameterClass: Class<T>?) {

    private val moshi = Moshi.Builder().build()

    companion object {
        var TAG = DataJsonConverter::class.java.simpleName!!
    }

    fun toJson(value: T): String {
        return moshi.let { moshi.adapter(this.typeParameterClass).toJson(value) }
    }

    fun toObject(json: String): T? {
        return try {
            moshi.adapter(this.typeParameterClass).fromJson(json)
        } catch (e: Exception) {
            Log.w(TAG, "cannot parse json to Object, may be json null while using Pref")
            null
        }

    }
}
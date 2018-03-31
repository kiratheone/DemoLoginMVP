package com.parkingreservation.iuh.demologinmvp.base

import android.content.Context

/**
* Created by Kushina on 25/03/2018.
*/

interface BaseView {

    /**
     * start loading process
     */
    fun showLoading()

    /**
     * terminate loading process
     */
    fun hideLoading()

    /**
     *  activity context
     */
    fun getContexts() : Context
}

package com.parkingreservation.iuh.demologinmvp.base

import com.parkingreservation.iuh.demologinmvp.injection.component.DaggerPresenterInjector
import com.parkingreservation.iuh.demologinmvp.injection.component.PresenterInjector
import com.parkingreservation.iuh.demologinmvp.injection.module.ContextModule
import com.parkingreservation.iuh.demologinmvp.injection.module.NetworkModule
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginPresenter
import com.parkingreservation.iuh.demologinmvp.ui.map.MapPresenter
import com.parkingreservation.iuh.demologinmvp.ui.map.fragment.mapview.MapViewPresenter

/**
* Created by Kushina on 25/03/2018.
*/

abstract class BasePresenter<out V : BaseView>(protected val view: V) {

    private val injector: PresenterInjector = DaggerPresenterInjector
            .builder()
            .baseView(view)
            .contextModule(ContextModule)
            .networkModule(NetworkModule)
            .build()
    init {
        inject()
    }

    /**
     *  THis method may be called when the presenter is created
     */
    open fun onViewCreated(){}

    /**
     * This method may be called when the presenter view is destroyed
     */
    open fun onViewDestroyed(){}

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is LoginPresenter -> injector.inject(this)

            is MapPresenter -> injector.inject(this)
            is MapViewPresenter -> injector.inject(this)

//            is ProfilePresenter -> injector.inject(this)
//            is EditingProfilePresenter -> injector.inject(this)
        }
    }


}
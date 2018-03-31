package com.parkingreservation.iuh.demologinmvp.injection.component

import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.injection.module.ContextModule
import com.parkingreservation.iuh.demologinmvp.injection.module.NetworkModule
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
* Created by Kushina on 25/03/2018.
*/

@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class)])
interface PresenterInjector {

    fun inject(loginPresenter: LoginPresenter)

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun contextModule(contextModule: ContextModule): Builder
        fun networkModule(networkModule: NetworkModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }

}

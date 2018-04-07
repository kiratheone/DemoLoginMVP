package com.parkingreservation.iuh.demologinmvp.injection.component

import android.content.SharedPreferences
import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.injection.module.ContextModule
import com.parkingreservation.iuh.demologinmvp.injection.module.NetworkModule
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail.ProfilePresenter
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit.EditingProfilePresenter
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginPresenter
import com.parkingreservation.iuh.demologinmvp.ui.map.MapPresenter
import com.parkingreservation.iuh.demologinmvp.ui.map.fragment.mapview.MapViewPresenter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
* Created by Kushina on 25/03/2018.
*/

@Singleton
@Component(modules = [(ContextModule::class), (NetworkModule::class)])
interface PresenterInjector {

    fun inject(presenter: LoginPresenter)

    fun inject(presenter: MapPresenter)
    fun inject(presenter: MapViewPresenter)

    /**
     * using for get saved data from client
     */
    fun getSharedPreferences(): SharedPreferences

    @Component.Builder
    interface Builder {
        fun build(): PresenterInjector

        fun contextModule(contextModule: ContextModule): Builder
        fun networkModule(networkModule: NetworkModule): Builder

        @BindsInstance
        fun baseView(baseView: BaseView): Builder
    }

}

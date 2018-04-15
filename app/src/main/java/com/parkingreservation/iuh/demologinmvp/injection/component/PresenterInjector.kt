package com.parkingreservation.iuh.demologinmvp.injection.component

import android.content.SharedPreferences
import com.parkingreservation.iuh.demologinmvp.base.BaseView
import com.parkingreservation.iuh.demologinmvp.injection.module.ContextModule
import com.parkingreservation.iuh.demologinmvp.injection.module.NetworkModule
import com.parkingreservation.iuh.demologinmvp.ui.account.AccountPresenter
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail.ProfilePresenter
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit.EditingProfilePresenter
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginPresenter
import com.parkingreservation.iuh.demologinmvp.ui.map.MapPresenter
import com.parkingreservation.iuh.demologinmvp.ui.map.fragment.mapview.MapViewPresenter
import com.parkingreservation.iuh.demologinmvp.ui.ticket.TicketPresenter
import com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.detail.TicketDetailPresenter
import com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.history.TicketHistoryPresenter
import com.parkingreservation.iuh.demologinmvp.ui.vehicle.VehiclePresenter
import com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.adding.VehicleAddingPresenter
import com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.list.VehicleListPresenter
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

    fun inject(presenter: AccountPresenter)
    fun inject(presenter: ProfilePresenter)
    fun inject(presenter: EditingProfilePresenter)

    fun inject(presenter: TicketPresenter)
    fun inject(presenter: TicketDetailPresenter)
    fun inject(presenter: TicketHistoryPresenter)

    fun inject(presenter: VehiclePresenter)
    fun inject(presenter: VehicleListPresenter)
    fun inject(presenter: VehicleAddingPresenter)
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

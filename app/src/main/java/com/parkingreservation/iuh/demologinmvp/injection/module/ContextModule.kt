package com.parkingreservation.iuh.demologinmvp.injection.module

import android.app.Application
import android.content.Context
import com.parkingreservation.iuh.demologinmvp.base.BaseView
import dagger.Module
import dagger.Provides

/**
* Created by Kushina on 25/03/2018.
*/

@Module
@Suppress("unused")
object ContextModule {

    /**
     * Provides the Context
     * @param baseView the BaseView used to provides the context
     * @return the Context to be provided
     */
    @Provides
    @JvmStatic
    internal fun provideContext(baseView: BaseView): Context {
        return baseView.getContexts()
    }

    /**
     * Provides the Application Context
     * @param context Context in which the application is running
     * @return the Application Context to be provided
     */
    @Provides
    @JvmStatic
    internal fun provideApplication(context: Context): Application {
        return context.applicationContext as Application
    }
}
package com.parkingreservation.iuh.demologinmvp.injection.module

import com.parkingreservation.iuh.demologinmvp.service.*
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient
import com.google.android.gms.analytics.Logger.LogLevel
import com.google.android.gms.tagmanager.zzdj.setLogLevel
import okhttp3.logging.HttpLoggingInterceptor


/**
 * Module which provides all required dependencies about network
 */
@Module
@Suppress("unused")
object NetworkModule {

    private const val BASE_URL = "http://45.119.81.16:8080/parking_reservation_1.0-1.0.0/"

    /**
     * Using for log-in, log-out user
     * Provides the Login service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Login service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    /**
     *  Related to the getting location in a area
     *  @param retrofit the Retrofit object used to instantiate the service
     *  @return the Map service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideMapService(retrofit: Retrofit): MapService {
        return retrofit.create(MapService::class.java)
    }

    /**
     *  Related to the getting user profile
     *  @param retrofit the Retrofit object used to instantiate the service
     *  @return the Profile service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideProfileService(retrofit: Retrofit): ProfileService {
        return retrofit.create(ProfileService::class.java)
    }

    /**
     *  Related to the getting User Ticket
     *  @param retrofit the Retrofit object used to instantiate the service
     *  @return the Ticket service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideTicketService(retrofit: Retrofit): TicketService {
        return retrofit.create(TicketService::class.java)
    }

    /**
     *  using for add/get/list vehicle of user
     *  @param retrofit the Retrofit object used to instantiate the service
     *  @return the Vehicle service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideVehicleService(retrofit: Retrofit): VehicleService {
        return retrofit.create(VehicleService::class.java)
    }


    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder().addInterceptor(logging).build()
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()

    }
}
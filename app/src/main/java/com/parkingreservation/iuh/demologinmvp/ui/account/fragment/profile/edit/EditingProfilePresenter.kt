package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit

import android.content.Context
import com.parkingreservation.iuh.demologinmvp.base.BaseFragment
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.service.ProfileService
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail.ProfileContract
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail.ProfilePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditingProfilePresenter(profileEditingView: EditingProfileContract.View) : BasePresenter<EditingProfileContract.View>(profileEditingView)
                    , ProfileContract.Presenter{
    @Inject
    lateinit var profileService: ProfileService

    private
    var subscription: Disposable? = null

    override fun onViewCreated() {
        loadProfile()
    }

    private fun loadProfile() {
        profileService.getUserProfile()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {data -> view.transferProfile(data)},
                        {}
                )
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }
}
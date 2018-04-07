package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit

import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.Account
import com.parkingreservation.iuh.demologinmvp.service.ProfileService
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail.ProfileContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditingProfilePresenter(profileEditingView: EditingProfileContract.View) : BasePresenter<EditingProfileContract.View>(profileEditingView)
                    , EditingProfileContract.Presenter{

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

    override fun editProfile(profile: Account) {
        // post profile
        loadProfile()
    }




}
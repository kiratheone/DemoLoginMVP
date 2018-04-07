package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseFragment
import com.parkingreservation.iuh.demologinmvp.databinding.FragmentProfileBinding
import com.parkingreservation.iuh.demologinmvp.model.Account

class ProfileFragment : BaseFragment<ProfilePresenter>(), ProfileContract.View {

    companion object {
        private var fragment = ProfileFragment()
        @JvmStatic
        fun getInstance() = fragment
    }

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        binding.profile = Account("1", "2", "3", "4")

        val view = binding.root
        return view
    }

    override fun transferProfile(profile: Account) {
        binding.profile = profile
    }

    override fun getContexts(): Context {
        return this.baseActivity
    }

    override fun showError(string: String) {
    }

    override fun showSuccess(string: String) {
    }

    override fun instantiatePresenter(): ProfilePresenter {
        return ProfilePresenter(this)
    }


}
package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseFragment
import com.parkingreservation.iuh.demologinmvp.databinding.FragmentProfileBinding
import com.parkingreservation.iuh.demologinmvp.model.User

class ProfileFragment : BaseFragment<ProfilePresenter>(), ProfileContract.View {

    companion object {
        private var fragment = ProfileFragment()
        @JvmStatic
        fun getInstance() = fragment
    }

    lateinit var binding: FragmentProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        val view = binding.root
        presenter.onViewCreated()
        return view
    }

    override fun transferProfile(profile: User) {
        binding.profile = profile
    }

    override fun getContexts(): Context {
        return this.baseActivity
    }

    override fun showError(string: String) {
        showStatus(string)
    }

    override fun showSuccess(string: String) {
        showStatus(string)
    }

    private fun showStatus(s: String) {
        Toast.makeText(getContexts(), s, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.progressVisibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressVisibility = View.GONE
    }

    override fun instantiatePresenter(): ProfilePresenter {
        return ProfilePresenter(this)
    }


}
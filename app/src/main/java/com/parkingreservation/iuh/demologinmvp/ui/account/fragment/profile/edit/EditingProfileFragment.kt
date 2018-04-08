package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseFragment
import com.parkingreservation.iuh.demologinmvp.databinding.FragmentEditingProfileBinding
import com.parkingreservation.iuh.demologinmvp.model.User

class EditingProfileFragment : BaseFragment<EditingProfilePresenter>(), EditingProfileContract.View {

    companion object {
        private var fragment = EditingProfileFragment()
        @JvmStatic
        fun getInstance() = fragment
    }

    lateinit var binding: FragmentEditingProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_editing_profile, container, false)

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

    override fun instantiatePresenter(): EditingProfilePresenter {
        return EditingProfilePresenter( this)
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
}
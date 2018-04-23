package com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
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

    @BindView(R.id.edt_input_username)
    lateinit var name: EditText

    @BindView(R.id.edt_input_phone)
    lateinit var phone: EditText

    @BindView(R.id.edt_input_mail)
    lateinit var mail: EditText

    @BindView(R.id.edt_input_location)
    lateinit var address: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_editing_profile, container, false)
        ButterKnife.bind(this, binding.root)
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

    override fun onEditSuccess() {
        fragmentManager?.popBackStack()
    }

    override fun showLoading() {
        binding.progressVisibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressVisibility = View.GONE
    }

    @OnClick(R.id.save)
    fun save() {
        presenter.editDriver(User(
                email =  this.mail.text.toString(),
                address =  this.phone.text.toString(),
                driverName = this.name.text.toString(),
                phoneNumber = this.phone.text.toString()
        ))
    }

    @OnClick(R.id.cancel)
    fun cancel() {
        this.baseActivity.finish()
    }
}
package com.parkingreservation.iuh.demologinmvp.ui.login.register

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseActivity
import com.parkingreservation.iuh.demologinmvp.databinding.ActivityRegisterBinding
import com.parkingreservation.iuh.demologinmvp.model.User

class RegisterActivity : BaseActivity<RegisterPresenter>(), RegisterContract.View {

    @BindView(R.id.input_name)
    lateinit var inputUserName: EditText

    @BindView(R.id.input_password)
    lateinit var inputPassword: EditText

    @BindView(R.id.input_email)
    lateinit var inputEmail: EditText

    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        ButterKnife.bind(this)

        configToolbar()
    }

    @OnClick(R.id.btn_register)
    fun register() {
        presenter.registerAccount(User(
            password = inputPassword.text.toString(),
            email = inputEmail.text.toString(),
            driverName = inputUserName.text.toString()
        ))
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRegisterSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getContexts(): Context {
        return this
    }

    override fun instantiatePresenter(): RegisterPresenter {
        return RegisterPresenter(this)
    }

}

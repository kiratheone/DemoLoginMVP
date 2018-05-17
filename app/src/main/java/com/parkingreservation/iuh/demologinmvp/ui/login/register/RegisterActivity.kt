package com.parkingreservation.iuh.demologinmvp.ui.login.register

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseActivity
import com.parkingreservation.iuh.demologinmvp.databinding.ActivityRegisterBinding
import com.parkingreservation.iuh.demologinmvp.model.User
import android.util.Patterns
import android.text.TextUtils


class RegisterActivity : BaseActivity<RegisterPresenter>(), RegisterContract.View {

    @BindView(R.id.input_name)
    lateinit var inputUserName: EditText

    @BindView(R.id.input_password)
    lateinit var inputPassword: EditText

    @BindView(R.id.input_email)
    lateinit var inputEmail: EditText

    @BindView(R.id.input_phone)
    lateinit var inputPhone: EditText

    @BindView(R.id.coordinator)
    lateinit var coordinatorLayout: CoordinatorLayout

    lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        ButterKnife.bind(this)

        configToolbar()
    }

    @OnClick(R.id.btn_register)
    fun register() {
        val pass = inputPassword.text.toString()
        val mail = inputEmail.text.toString()
        val phone = inputPhone.text.toString()

        when {
            !isValidPass(pass) -> showStatus(getString(R.string.pass_not_valid))
            !isValidEmail(mail) -> showStatus(getString(R.string.email_not_valid))
            !isValidPhone(phone) -> showStatus(getString(R.string.phone_not_valid))
            else -> registerAccount()
        }

    }

    private fun registerAccount() {
        presenter.registerAccount(User(
                password = inputPassword.text.toString(),
                email = inputEmail.text.toString(),
                driverName = inputUserName.text.toString(),
                phoneNumber = inputPhone.text.toString()
        ))
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun isValidPhone(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches()
    }

    private fun isValidPass(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && target.length >= 6
    }

    override fun showError(string: String) {
        showStatus(string)
    }

    override fun showSuccess(string: String) {
        showStatus(string)
    }

    private fun showStatus(s: String) {
        Snackbar.make(coordinatorLayout, s, Snackbar.LENGTH_LONG).show()
    }

    override fun onRegisterSuccess() {
        Toast.makeText(this, "Đăng kí thành công", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun hideLoading() {
    }

    override fun showLoading() {
    }

    override fun getContexts(): Context {
        return this
    }

    override fun instantiatePresenter(): RegisterPresenter {
        return RegisterPresenter(this)
    }

}

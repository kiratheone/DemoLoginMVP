package com.parkingreservation.iuh.demologinmvp.ui.login

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseActivity
import com.parkingreservation.iuh.demologinmvp.databinding.ActivityLoginBinding
import com.parkingreservation.iuh.demologinmvp.model.LoginModel
import com.parkingreservation.iuh.demologinmvp.ui.login.register.RegisterActivity

class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View {

    @BindView(R.id.input_name)
    lateinit var inputUserName: EditText

    @BindView(R.id.input_password)
    lateinit var inputPassword: EditText

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        ButterKnife.bind(this)

        binding.progressVisibility = View.GONE
        configToolbar()
        presenter.onViewCreated()
    }

    override fun updateUser(users: List<LoginModel>) {
        binding.user = users[0]
    }

    @OnClick(R.id.btn_signIn)
    fun signIn() {
        val userName = getUserName()
        val password = getPassword()
        presenter.signIn(userName, password)
    }

    @OnClick(R.id.tv_sign_up)
    fun signUp() {
        finish()
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun onLoginSuccessfully() {
        finish()
    }

    override fun getUserName(): String {
        return inputUserName.text.toString()
    }

    override fun getPassword(): String {
        return inputPassword.text.toString()
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


    override fun instantiatePresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun getContexts(): Context {
        return this
    }

}

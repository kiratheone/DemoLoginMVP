package com.parkingreservation.iuh.demologinmvp.ui.login

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.widget.EditText
import butterknife.BindView
import butterknife.OnClick
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseActivity
import com.parkingreservation.iuh.demologinmvp.databinding.ActivityLoginBinding
import com.parkingreservation.iuh.demologinmvp.model.LoginModel

class LoginActivity : BaseActivity<LoginPresenter>(), LoginContract.View {


    @BindView(R.id.input_name)
    private
    lateinit var inputUserName: EditText

    @BindView(R.id.input_password)
    private
    lateinit var inputPassword: EditText

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        presenter.onViewCreated()
    }

    override fun updateUser(users: List<LoginModel>) {
        binding.user = users[0]
    }

    override fun onLoginSuccessfully() {

    }

    override fun getUserName(): String {
        return inputUserName.text.toString()
    }

    override fun getPassword(): String {
        return inputPassword.text.toString()
    }


    override fun instantiatePresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun getContexts(): Context { return this }

    @OnClick(R.id.btn_signIn)
    private fun signIn() {
        val userName = getUserName()
        val password = getPassword()
        presenter.signIn(userName, password)
    }
}

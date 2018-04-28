package com.parkingreservation.iuh.demologinmvp.ui.login.logout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseActivity
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginActivity

class LogoutActivity : BaseActivity<LogoutPresenter>(), LogoutContract.View {


    override fun onSignOutCompleted() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)
    }

    public fun signOut(v: View) {
        presenter.signOut()
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
    }

    override fun hideLoading() {
    }


    override fun getContexts(): Context {
        return this
    }

    override fun instantiatePresenter(): LogoutPresenter {
        return LogoutPresenter(this)
    }

}

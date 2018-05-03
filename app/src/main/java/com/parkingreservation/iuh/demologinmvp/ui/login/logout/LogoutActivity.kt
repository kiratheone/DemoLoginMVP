package com.parkingreservation.iuh.demologinmvp.ui.login.logout

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseActivity
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginActivity
import android.os.Build


class LogoutActivity : BaseActivity<LogoutPresenter>(), LogoutContract.View {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout)
        toolbar = findViewById(R.id.toolbar)
        configToolbar()
    }

    override fun onSignOutCompleted() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private lateinit var b: AlertDialog
    public fun signOut(view: View) {
        var alertDialogBuilder: AlertDialog.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert)
        } else {
            AlertDialog.Builder(this)
        }
        alertDialogBuilder.setMessage(resources.getString(R.string.signout_confirm))


        alertDialogBuilder.setPositiveButton(R.string.signout_accept, { _, _ ->
            presenter.signOut()
            b.dismiss()
        })
        alertDialogBuilder.setNegativeButton(R.string.signout_reject, { _, _ ->
            b.dismiss()
        })
        b = alertDialogBuilder.create()
        b.show()
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

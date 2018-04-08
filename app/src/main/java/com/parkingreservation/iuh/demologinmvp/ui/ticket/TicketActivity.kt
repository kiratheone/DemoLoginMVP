package com.parkingreservation.iuh.demologinmvp.ui.ticket

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import butterknife.BindView
import butterknife.ButterKnife
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseActivity
import com.parkingreservation.iuh.demologinmvp.databinding.ActivityTicketBinding
import com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.detail.TicketDetailFragment
import com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.history.TicketHistoryFragment

class TicketActivity : BaseActivity<TicketPresenter>(), TicketConstract.View {


    @BindView(R.id.ticket_nav_bottom)
    lateinit var bottomBar: BottomNavigationView

    private var currentFragment = TicketDetailFragment.getInstance() as Fragment

    lateinit var binding: ActivityTicketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_ticket)
        ButterKnife.bind(this)
        presenter.onViewCreated()
        setUpBottomBar()
        transactionFragment()
    }

    private fun setUpBottomBar(){
        bottomBar.setOnNavigationItemSelectedListener { item ->
            currentFragment = when (item.itemId) {
                R.id.ticket -> TicketDetailFragment.getInstance()
                R.id.ticket_history -> TicketHistoryFragment.getInstance()
                else -> TicketDetailFragment.getInstance()
            }
            transactionFragment()
            true
        }
    }

    private fun transactionFragment(){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, currentFragment)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            transaction.commitNowAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }

    override fun showError(string: String) {
    }

    override fun showSuccess(string: String) {
    }

    override fun getContexts(): Context {
        return this
    }

    override fun instantiatePresenter(): TicketPresenter {
        return TicketPresenter(this)
    }
}

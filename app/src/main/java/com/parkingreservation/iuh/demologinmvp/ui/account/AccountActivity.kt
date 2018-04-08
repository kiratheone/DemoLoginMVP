package com.parkingreservation.iuh.demologinmvp.ui.account

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import butterknife.BindView
import butterknife.ButterKnife
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseActivity
import com.parkingreservation.iuh.demologinmvp.databinding.ActivityAccountBinding

class AccountActivity : BaseActivity<AccountPresenter>(),AccountContract.View {

    companion object {
        var TAG = AccountActivity::class.java.simpleName
    }

    @BindView(R.id.viewpager)
    lateinit var viewPager: ViewPager

    @BindView(R.id.tabs)
    lateinit var tabs: TabLayout

    private val accountAdapter = AccountPagerAdapter(this.supportFragmentManager)

    lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_account)
        binding.adapter = accountAdapter

        ButterKnife.bind(this)

        configToolbar()
        setUpPager()
        presenter.onViewCreated()
    }

    private fun setUpPager() {
        tabs.setupWithViewPager(viewPager)
    }



    override fun showError(string: String) {
    }

    override fun showSuccess(string: String) {
    }

    override fun getContexts(): Context {
       return this
    }

    override fun instantiatePresenter(): AccountPresenter {
        return AccountPresenter(this)
    }
}

package com.parkingreservation.iuh.demologinmvp.ui.vehicle

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseActivity
import com.parkingreservation.iuh.demologinmvp.databinding.ActivityVehicleBinding

class VehicleActivity: BaseActivity<VehiclePresenter>(), VehicleContract.View {

    companion object {
        val TAG = VehicleActivity::class.java.simpleName
    }

    @BindView(R.id.viewpager)
    lateinit var viewPager: ViewPager

    @BindView(R.id.tabs)
    lateinit var tabs: TabLayout

     lateinit var adapter: VehiclePagerAdapter

    lateinit var binding: ActivityVehicleBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_vehicle)
        adapter = VehiclePagerAdapter(this.supportFragmentManager)
        binding.adapter = adapter

        ButterKnife.bind(this)

        configToolbar()
        setUpPager()
        presenter.onViewCreated()
    }

    private fun setUpPager() {
        tabs.setupWithViewPager(viewPager)
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

    override fun instantiatePresenter(): VehiclePresenter {
        return VehiclePresenter(this)
    }

}
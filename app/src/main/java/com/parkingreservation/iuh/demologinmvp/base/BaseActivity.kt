package com.parkingreservation.iuh.demologinmvp.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import butterknife.BindView
import com.parkingreservation.iuh.demologinmvp.R

/**
* Created by Kushina on 25/03/2018.
*/
abstract class BaseActivity<P : BasePresenter<BaseView>> : BaseView, AppCompatActivity(), BaseFragment.Callback, BaseV4Fragment.Callback {
    protected lateinit var presenter: P

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = instantiatePresenter()
    }

    protected fun configToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Instantiates the presenter the Activity is based on.
     */
    protected abstract fun instantiatePresenter(): P


    override fun onFragmentAttached() { }

    override fun onFragmentDetached(tag: String) { }
}

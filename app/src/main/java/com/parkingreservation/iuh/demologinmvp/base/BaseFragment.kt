package com.parkingreservation.iuh.demologinmvp.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

/**
* Created by Kushina on 26/03/2018.
*/

abstract class BaseFragment<P : BasePresenter<BaseView>> : BaseView, Fragment() {

    protected lateinit var baseActivity: BaseActivity<*>
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = instantiatePresenter()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is BaseActivity<*>) {
            baseActivity = context
            context.onFragmentAttached()
        }
    }

    /**
     * Instantiates the presenter the Activity is based on.
     */
    protected abstract fun instantiatePresenter(): P

    override fun showLoading() { }

    override fun hideLoading() { }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}
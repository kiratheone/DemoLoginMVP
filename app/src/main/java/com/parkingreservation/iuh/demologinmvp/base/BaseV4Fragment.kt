package com.parkingreservation.iuh.demologinmvp.base

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View

abstract class BaseV4Fragment<P : BasePresenter<BaseView>> : BaseView, Fragment() {

    protected lateinit var baseActivity: BaseActivity<*>
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.setDefaultUncaughtExceptionHandler { _, _ -> Log.e("Alert", "Lets See if it Works !!!") }
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

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }
}
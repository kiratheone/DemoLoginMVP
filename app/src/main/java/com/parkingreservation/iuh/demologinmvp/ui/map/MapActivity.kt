package com.parkingreservation.iuh.demologinmvp.ui.map

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseActivity
import com.parkingreservation.iuh.demologinmvp.util.NavbarSelectionType

class MapActivity : BaseActivity<MapPresenter>(), MapContract.View {

    companion object {
        const val TAG = "MapsActivity"
        var navItemIndex = 0
        var CURRENT_TAG = NavbarSelectionType.HOME.tag
    }

    @BindView(R.id.nav_view)
    lateinit var navigation: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var mHandler: Handler


    override fun getContexts(): Context {
        return this
    }

    override fun instantiatePresenter(): MapPresenter {
        return MapPresenter(this)
    }
}

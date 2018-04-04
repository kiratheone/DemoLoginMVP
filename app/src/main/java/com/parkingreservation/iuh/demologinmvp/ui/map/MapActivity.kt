package com.parkingreservation.iuh.demologinmvp.ui.map

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseActivity
import com.parkingreservation.iuh.demologinmvp.databinding.ActivityMapBinding
import com.parkingreservation.iuh.demologinmvp.model.LoginModel
import com.parkingreservation.iuh.demologinmvp.ui.account.AccountActivity
import com.parkingreservation.iuh.demologinmvp.util.NavbarSelectionType

class MapActivity : BaseActivity<MapPresenter>(), MapContract.View {

    companion object {
        const val TAG = "MapsActivity"
        var navItemIndex = 0
        var CURRENT_TAG = NavbarSelectionType.HOME.tag
    }

    @BindView(R.id.nav_view)
    lateinit var navigation: NavigationView

    @BindView(R.id.tv_bs_title)
    lateinit var tvBsTitle: TextView

    @BindView(R.id.tv_bs_description)
    lateinit var tvBsDes: TextView

    @BindView(R.id.drawer_layout)
    lateinit var drawer: DrawerLayout

    private lateinit var tvName: TextView
    private lateinit var tvWebsite: TextView
    private lateinit var ivProfile: ImageView

    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
        bindingNavView()
        presenter.onViewCreated()
    }

    private fun bindingNavView() {
        val navHeader = navigation.getHeaderView(0)

        tvName = navHeader.findViewById(R.id.tv_user_name)
        ivProfile = navHeader.findViewById(R.id.iv_profile)
        tvWebsite = navHeader.findViewById(R.id.tv_website)

        navigation.setNavigationItemSelectedListener{item: MenuItem ->
            when(item.itemId) {
                R.id.nav_home -> {
                    navItemIndex = NavbarSelectionType.HOME.index
                    CURRENT_TAG = NavbarSelectionType.HOME.tag
                }
                R.id.nav_ticket -> {
                    navItemIndex = NavbarSelectionType.TICKET.index
                    CURRENT_TAG = NavbarSelectionType.TICKET.tag
//                    startActivity(Intent(this, TicketActivity::class.java))
                }
                R.id.nav_account -> {
                    navItemIndex = NavbarSelectionType.ACCOUNT.index
                    CURRENT_TAG = NavbarSelectionType.ACCOUNT.tag
                    startActivity(Intent(this, AccountActivity::class.java))
                }
                R.id.nav_notifications -> {
                    navItemIndex = NavbarSelectionType.NOTIFICATION.index
                    CURRENT_TAG = NavbarSelectionType.NOTIFICATION.tag
//                    startActivity(Intent(this, NotificationActivity::class.java))
                }
                else -> {
                    navItemIndex = NavbarSelectionType.HOME.index
                    CURRENT_TAG = NavbarSelectionType.HOME.tag
                }
            }
            /* close navbar */
            drawer.closeDrawers()

            changeCheckedItem(item)
            item.isChecked = true
            true}
    }

    private fun changeCheckedItem(item: MenuItem) = !item.isChecked

    override fun loadUserHeader(user: LoginModel) {
        Log.i(TAG, "set navigation header user profile")
        tvName.text = user.name
        tvWebsite.text = user.email
        navigation.menu.getItem(NavbarSelectionType.NOTIFICATION.index).setActionView(R.layout.menu_dot)
        Glide.with(this).load(R.mipmap.ic_launcher).thumbnail(0.5f).apply(RequestOptions.circleCropTransform()).into(ivProfile)
    }

    override fun getContexts(): Context {
        return this
    }

    override fun instantiatePresenter(): MapPresenter {
        return MapPresenter(this)
    }

    // event

}

package com.parkingreservation.iuh.demologinmvp.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.NavigationView
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatSpinner
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.util.DirectionConverter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.mahc.custombottomsheetbehavior.BottomSheetBehaviorGoogleMapsLike
import com.mahc.custombottomsheetbehavior.MergedAppBarLayout
import com.mahc.custombottomsheetbehavior.MergedAppBarLayoutBehavior
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseActivity
import com.parkingreservation.iuh.demologinmvp.databinding.ActivityMapBinding
import com.parkingreservation.iuh.demologinmvp.ui.account.AccountActivity
import com.parkingreservation.iuh.demologinmvp.ui.map.fragment.mapview.MapViewFragment
import com.parkingreservation.iuh.demologinmvp.ui.map.fragment.servicepack.ServicePackFragment
import com.parkingreservation.iuh.demologinmvp.ui.ticket.TicketActivity
import com.parkingreservation.iuh.demologinmvp.util.NavbarSelectionType
import com.parkingreservation.iuh.demologinmvp.util.StringLengthHandler
import com.parkingreservation.iuh.demologinmvp.model.Station
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginActivity
import com.parkingreservation.iuh.demologinmvp.ui.vehicle.VehicleActivity

class MapActivity : BaseActivity<MapPresenter>(), MapContract.View {

    companion object {
        var TAG = MapActivity::class.java.simpleName
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
        ButterKnife.bind(this)

        bindingNavView()
        setUpRelativeView()
        createServicePack()
        presenter.onViewCreated()

        /*  make sure this is the first times activity was called */
        if (savedInstanceState == null) {
            navItemIndex = 0
            CURRENT_TAG = NavbarSelectionType.HOME.tag
            loadNavView()
        }
        sheetBehavior.state = BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN
    }

    private fun bindingNavView() {
        mHandler = Handler()
        val navHeader = navigation.getHeaderView(0)

        tvName = navHeader.findViewById(R.id.tv_user_name)
        ivProfile = navHeader.findViewById(R.id.iv_profile)
        tvWebsite = navHeader.findViewById(R.id.tv_website)

        navigation.setNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_home -> {
                    navItemIndex = NavbarSelectionType.HOME.index
                    CURRENT_TAG = NavbarSelectionType.HOME.tag
                }
                R.id.nav_ticket -> {
                    navItemIndex = NavbarSelectionType.TICKET.index
                    CURRENT_TAG = NavbarSelectionType.TICKET.tag
                    startActivity(Intent(this, TicketActivity::class.java))
                }
                R.id.nav_account -> {
                    navItemIndex = NavbarSelectionType.ACCOUNT.index
                    CURRENT_TAG = NavbarSelectionType.ACCOUNT.tag
                    startActivity(Intent(this, AccountActivity::class.java))
                }
                R.id.nav_vehicle -> {
                    navItemIndex = NavbarSelectionType.VEHICLE.index
                    CURRENT_TAG = NavbarSelectionType.VEHICLE.tag
                    startActivity(Intent(this, VehicleActivity::class.java))
                }
                R.id.nav_notifications -> {
                    navItemIndex = NavbarSelectionType.NOTIFICATION.index
                    CURRENT_TAG = NavbarSelectionType.NOTIFICATION.tag
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                else -> {
                    navItemIndex = NavbarSelectionType.HOME.index
                    CURRENT_TAG = NavbarSelectionType.HOME.tag
                }
            }
            closeDrawer()
            changeCheckedItem(item)
            item.isChecked = true
            true
        }
    }

    private fun changeCheckedItem(item: MenuItem) = !item.isChecked

    override fun loadUserHeader(user: User) {
        Log.i(TAG, "set navigation header user profile")
        tvName.text = user.driverName
        tvWebsite.text = user.email
        navigation.menu.getItem(NavbarSelectionType.NOTIFICATION.index).setActionView(R.layout.menu_dot)
        Glide.with(this).load(R.mipmap.ic_launcher).thumbnail(0.5f).apply(RequestOptions.circleCropTransform()).into(ivProfile)
    }


    private lateinit var currentFragment: android.app.Fragment
    private var currentMarker: Marker? = null
    private lateinit var mHandler: Handler

    @SuppressLint("CommitTransaction")
    private fun loadNavView() {
        setNavPicked()
        if (fragmentManager.findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers()
            return
        }
        val pendingFragment = Runnable {
            currentFragment = getFragment()
            if (currentFragment is MapViewFragment) {

                (currentFragment as MapViewFragment).setOnMarkerClick(object : MapEvents.OnParkingMakerEvents {
                    override fun onMarkerClickListener(marker: Marker) {
                        currentMarker = marker
                        images.shuffle()
                        binding.adapter = ParkingLotCoverPagerAdapter(getContexts(), images.toIntArray())
                        presenter.loadStationContent(marker)
                    }
                })

                (currentFragment as MapViewFragment).setOnMapSelected(object : MapEvents.OnParkingMapSelection {
                    override fun onMapSelected() {
                        Log.i(TAG, "On map view selected, remove all item on map")
                        sheetBehavior.state = BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN
                        currentMarker = null
                    }
                })

                (currentFragment as MapViewFragment).setOnNavBarClick(object : MapEvents.OnNavBarEvent {
                    @SuppressLint("RtlHardcoded")
                    override fun onNavBarClickListener() {
                        openDrawer()
                    }
                })
            }
            val transaction = fragmentManager.beginTransaction()
            transaction.setCustomAnimations(android.R.animator.fade_in,
                    android.R.animator.fade_out)
            transaction.replace(R.id.replace_fragment, currentFragment, CURRENT_TAG)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                transaction.commitNowAllowingStateLoss()
            } else {
                transaction.commit()
            }
        } // on Map Event
        mHandler.post(pendingFragment)
        closeDrawer()
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun getFragment(): android.app.Fragment {
        return when (navItemIndex) {
            NavbarSelectionType.HOME.index -> MapViewFragment.getInstance()!!
            else -> MapViewFragment.getInstance()!!
        }
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

    override fun addStationContent(station: Station?) {
        if (station != null) {
            binding.station = StringLengthHandler.getText(station.name)
            sheetBehavior.state = BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED
            mergedBehavior.setToolbarTitle(StringLengthHandler.getText(station.name))
        }
    }

    override fun getContexts(): Context {
        return this
    }

    override fun instantiatePresenter(): MapPresenter {
        return MapPresenter(this)
    }

    private lateinit var servicePack: ServicePackFragment
    private fun createServicePack() {
        servicePack = ServicePackFragment()
    }

    private fun setNavPicked() {
        navigation.menu.getItem(navItemIndex).isChecked = true
    }

    private fun closeDrawer() {
        drawer.closeDrawers()
    }

    @SuppressLint("RtlHardcoded")
    private fun openDrawer() {
        drawer.openDrawer(Gravity.LEFT)
    }

    override fun onResume() {
        Log.d(TAG, "On Resume map view")
        navigation.menu.getItem(0).isChecked = true // set back to home
        navItemIndex = NavbarSelectionType.HOME.index
        CURRENT_TAG = NavbarSelectionType.HOME.tag
        super.onResume()
    }

    /* station detail */
    @BindView(R.id.pager)
    lateinit var viewPager: ViewPager

    private lateinit var sheetBehavior: BottomSheetBehaviorGoogleMapsLike<View>
    private lateinit var mergedBehavior: MergedAppBarLayoutBehavior
    private fun setUpRelativeView() {
        setupParkingDetailBottomSheet()
        setUpMergeAppBar()
    }

    private fun setupParkingDetailBottomSheet() {
        val coordLayout = findViewById<CoordinatorLayout>(R.id.coordinator_layout)
        val bottomSheet = coordLayout.findViewById<View>(R.id.bottom_sheet)
        sheetBehavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet)
        sheetBehavior.isHideable = true
    }

    private fun setUpMergeAppBar() {
        val mergedAppbar = findViewById<MergedAppBarLayout>(R.id.merged_appbar_layout)
        mergedBehavior = MergedAppBarLayoutBehavior.from(mergedAppbar)
        mergedBehavior.setNavigationOnClickListener { sheetBehavior.state = BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED }
    }

    @OnClick(R.id.float_button)
    fun showService() {
        servicePack.show(supportFragmentManager, servicePack.tag)
    }

    @OnClick(R.id.moreInfo)
    fun moreInfo() {
        sheetBehavior.state = BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT
    }

    @OnClick(R.id.float_button_gps)
    fun gps() {
        if (currentFragment is MapViewFragment)
            (currentFragment as MapViewFragment)._gps()
    }

    @OnClick(R.id.float_button_gps)
    fun backToMap() {
        sheetBehavior.state = BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED
    }

    @OnClick(R.id.bt_booking)
    fun bookParkingLot() {
        val builder = AlertDialog.Builder(this)
        createVehiclesDialog(builder)
        builder.create().show()
    }

    @SuppressLint("InflateParams")
    private fun createVehiclesDialog(builder: AlertDialog.Builder) {

        val view = layoutInflater.inflate(R.layout.dialog_vehicles, null)
        builder.setTitle("Choose Your Vehicle")

        val sp = view.findViewById<AppCompatSpinner>(R.id.sp_vehicles)
        val adapterSp = ArrayAdapter(this, android.R.layout.simple_spinner_item, presenter.getUserVehicle())
        adapterSp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val spTypes= view.findViewById<AppCompatSpinner>(R.id.sp_ticket_types)
        val adapterSpTypes = ArrayAdapter(this, android.R.layout.simple_spinner_item, presenter.getTicketTypes())
        adapterSpTypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        builder.setPositiveButton(R.string.booking) { dialog, _ ->
            presenter.bookParkingLot(currentMarker!!.title, sp.selectedItemPosition, spTypes.selectedItemPosition)
            dialog.dismiss()
        }
        builder.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }

        sp.adapter = adapterSp
        spTypes.adapter = adapterSpTypes

        builder.setView(view)
    }

    private var currentPolylineDirection: Polyline? = null
    @OnClick(R.id.bt_direction, R.id.layout_direction)
    fun directWays() {
        if (currentMarker != null) {
            val lastKnowLocation = (currentFragment as MapViewFragment).getLastKnowLocation()
            if (lastKnowLocation != null) {
                val origin = LatLng(lastKnowLocation.latitude, lastKnowLocation.longitude)
                val destination = currentMarker!!.position
                GoogleDirection.withServerKey(resources.getString(R.string.google_maps_key))
                        .from(origin)
                        .to(destination)
                        .transportMode(TransportMode.DRIVING)
                        .execute(object : DirectionCallback {
                            override fun onDirectionSuccess(direction: Direction?, rawBody: String?) {
                                Log.d(TAG, "on Direction Success $rawBody")
                                if (direction != null && currentFragment is MapViewFragment) {
                                    if (direction.isOK) {
                                        if (currentPolylineDirection != null) // remove old line
                                            currentPolylineDirection!!.remove()
                                        currentPolylineDirection =
                                                drawDirectionWays((currentFragment as MapViewFragment).mMap
                                                        , direction, applicationContext)
                                    } else {
                                        Log.d(TAG, "status of direction way NOT OK")
                                    }
                                } else {
                                    Log.d(TAG, "direction null")
                                }
                            }

                            override fun onDirectionFailure(t: Throwable?) {
                                Log.d(TAG, "onDirectionFailure")
                            }
                        })
            }
            sheetBehavior.state = BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED
        }
    }

    private val WIDTH_WAYS = 8
    fun drawDirectionWays(map: GoogleMap, direction: Direction?, context: Context): Polyline {
        Log.i(TAG, "in direct way function draw a line from your location to destination")
        val route = direction!!.routeList[0]

        val directionPositionList = route.legList[0].directionPoint
        val polyline = map.addPolyline(DirectionConverter.createPolyline(context
                , directionPositionList, WIDTH_WAYS, ContextCompat.getColor(context, R.color.colorAccent)))
        val southwest = route.bound.southwestCoordination.coordination
        val northeast = route.bound.northeastCoordination.coordination
        val bounds = LatLngBounds(southwest, northeast)
        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10))
        return polyline
    }

    private val images = mutableListOf(R.drawable.z1, R.drawable.z2,
            R.drawable.z3, R.drawable.z4, R.drawable.z5, R.drawable.z6
            , R.drawable.z3, R.drawable.z8, R.drawable.z10, R.drawable.z11, R.drawable.z13
            , R.drawable.z14, R.drawable.z15, R.drawable.z16, R.drawable.z17)
}

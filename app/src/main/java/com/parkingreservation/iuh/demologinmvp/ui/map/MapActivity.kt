package com.parkingreservation.iuh.demologinmvp.ui.map

import android.annotation.SuppressLint
import android.content.Context
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
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AlertDialog
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.*
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
import com.parkingreservation.iuh.demologinmvp.model.*
import com.parkingreservation.iuh.demologinmvp.ui.account.AccountActivity
import com.parkingreservation.iuh.demologinmvp.ui.map.fragment.mapview.MapViewFragment
import com.parkingreservation.iuh.demologinmvp.ui.map.fragment.servicepack.ServicePackFragment
import com.parkingreservation.iuh.demologinmvp.ui.ticket.TicketActivity
import com.parkingreservation.iuh.demologinmvp.util.NavbarSelectionType
import com.parkingreservation.iuh.demologinmvp.util.StringLengthHandler
import com.parkingreservation.iuh.demologinmvp.ui.login.LoginActivity
import com.parkingreservation.iuh.demologinmvp.ui.login.logout.LogoutActivity
import com.parkingreservation.iuh.demologinmvp.ui.vehicle.VehicleActivity

class MapActivity : BaseActivity<MapPresenter>(), MapContract.View {

    companion object {
        val TAG = MapActivity::class.java.simpleName
        var navItemIndex = 0
        var CURRENT_TAG = NavbarSelectionType.HOME.tag
        const val WIDTH_WAYS = 8
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

        binding.layoutManager = LinearLayoutManager(getContexts())
        binding.layoutManagerComment = LinearLayoutManager(getContexts())

        bindingNavView()
        setUpRelativeView()
        createServicePack()

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
                    if (!presenter.loggedIn())
                        moveLoginPage()
                    else
                        startActivity(Intent(this, TicketActivity::class.java))
                }
                R.id.nav_account -> {
                    navItemIndex = NavbarSelectionType.ACCOUNT.index
                    CURRENT_TAG = NavbarSelectionType.ACCOUNT.tag
                    if (!presenter.loggedIn())
                        moveLoginPage()
                    else
                        startActivity(Intent(this, AccountActivity::class.java))
                }
                R.id.nav_vehicle -> {
                    navItemIndex = NavbarSelectionType.VEHICLE.index
                    CURRENT_TAG = NavbarSelectionType.VEHICLE.tag
                    if (!presenter.loggedIn())
                        moveLoginPage()
                    else
                        startActivity(Intent(this, VehicleActivity::class.java))
                }
                R.id.nav_notifications -> {
                    navItemIndex = NavbarSelectionType.NOTIFICATION.index
                    CURRENT_TAG = NavbarSelectionType.NOTIFICATION.tag
                    if (!presenter.loggedIn())
                        startActivity(Intent(this, LoginActivity::class.java))
                    else
                        startActivity(Intent(this, LogoutActivity::class.java))
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

    private fun moveLoginPage() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun changeCheckedItem(item: MenuItem) = !item.isChecked

    override fun loadUserHeader(user: User?) {
        Log.i(TAG, "set navigation header user profile")
        tvName.text = user?.driverName ?: ""
        tvWebsite.text = user?.email ?: ""
//        navigation.menu.getItem(NavbarSelectionType.NOTIFICATION.index).setActionView(R.layout.menu_dot)
        Glide.with(this).load(R.mipmap.ic_launcher_main).thumbnail(0.5f).apply(RequestOptions.circleCropTransform()).into(ivProfile)
    }

    override fun onStationImageLoaded(images: List<String>) {
        binding.coverPageAdapter = ParkingLotCoverPagerAdapter(getContexts(), images)
    }

    override fun onStationCommentLoaded(comments: List<Comment>) {
        binding.stationCommentAdapter = StationCommentAdapter(getContexts(), comments)
    }


    private lateinit var currentFragment: android.app.Fragment
    private var currentMarker: Marker? = null
    private lateinit var mHandler: Handler

    @SuppressLint("CommitTransaction")
    private fun loadNavView() {
        setNavPicked()
        if (fragmentManager.findFragmentByTag(CURRENT_TAG) != null) {
            closeDrawer()
            return
        }

        currentFragment = getFragment()
        if (currentFragment is MapViewFragment) {

            (currentFragment as MapViewFragment).setOnMarkerClick(object : MapEvents.OnParkingMakerEvents {
                override fun onMarkerClickListener(marker: Marker) {
                    currentMarker = marker
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
            station.name = StringLengthHandler.getText(station.name)
            binding.station = station
            mergedBehavior.setToolbarTitle(StringLengthHandler.getText(station.name))

            sheetBehavior.state = BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED
        }
    }

    override fun onLoadTicketTypesSuccess(s: MutableList<StationServiceModel>) {
        binding.adapterService = StationServiceAdapter(this, s)
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
        presenter.onViewCreated()
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

    private lateinit var bottomSheet: NestedScrollView

    private fun setupParkingDetailBottomSheet() {
        val coordLayout = findViewById<CoordinatorLayout>(R.id.coordinator_layout)
        bottomSheet = coordLayout.findViewById(R.id.bottom_sheet)
        sheetBehavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet)
        sheetBehavior.isHideable = true
    }

    private fun setUpMergeAppBar() {
        val mergedAppbar = findViewById<MergedAppBarLayout>(R.id.merged_appbar_layout)
        mergedBehavior = MergedAppBarLayoutBehavior.from(mergedAppbar)
        mergedBehavior.setNavigationOnClickListener {
            bottomSheet.scrollTo(0, 0)
            sheetBehavior.state = BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED
        }
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

    @OnClick(R.id.back_to_map)
    fun backToMap() {
        sheetBehavior.state = BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED
    }

    @OnClick(R.id.bt_booking)
    fun bookParkingLot() {
        if (presenter.isStationValidate()) {
            val builder = AlertDialog.Builder(this)
            createVehiclesDialog(builder)
            builder.create().show()
        }

    }

    private fun createVehiclesDialog(builder: AlertDialog.Builder) {
        //spinner create for choose vehicle user
        val view = layoutInflater.inflate(R.layout.dialog_vehicles, null)
        builder.setTitle("Choose Your Vehicle")

        val userVehicleDropdown = view.findViewById<AppCompatSpinner>(R.id.sp_vehicles)
        val adapterSp = ArrayAdapter(this, android.R.layout.simple_spinner_item, presenter.getUserVehicle())
        adapterSp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        // spinner for choose service
        val spTypes = view.findViewById<RecyclerView>(R.id.rv_ticket_type)
        val ticketType = presenter.getTicketTypes(0) // first time get position 0
        val adapterSpTypes = TicketTypeAdapter(this, ticketType)

        builder.setPositiveButton(R.string.booking) { dialog, _ ->
            val ticketTypes = mutableListOf<TicketTypeModels>()
            val typeM = adapterSpTypes.serviceModels
            for ((key, value) in adapterSpTypes.checkItem) {
                if (typeM[key]!!.isNotEmpty()) {
                    Log.d(TAG, "$key: ${ticketType[key]!![value]}")
                    ticketTypes.add(typeM[key]!![value])
                }
            }
            when {
                adapterSpTypes.checkItem.size <= 0 -> showStatus(resources.getString(R.string.empty_ticket_service))
                else -> presenter.bookParkingLot(currentMarker!!.title, userVehicleDropdown.selectedItem.toString(), ticketTypes)
            }

            dialog.dismiss()
        }
        builder.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }

        userVehicleDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                adapterSpTypes.serviceModels = presenter.getTicketTypes(position)
                adapterSpTypes.notifyDataSetChanged()
            }
        }

        userVehicleDropdown.adapter = adapterSp
        spTypes.adapter = adapterSpTypes
        spTypes.layoutManager = LinearLayoutManager(getContexts())
        builder.setView(view)
    }

    private var currentPolylineDirection: Polyline? = null
    @OnClick(R.id.bt_direction)
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
                                                drawDirectionWays((currentFragment as MapViewFragment).mMap!!
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

}

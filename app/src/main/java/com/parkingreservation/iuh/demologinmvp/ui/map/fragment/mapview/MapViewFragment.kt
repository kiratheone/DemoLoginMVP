package com.parkingreservation.iuh.demologinmvp.ui.map.fragment.mapview

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import butterknife.BindView
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseFragment
import com.parkingreservation.iuh.demologinmvp.ui.map.MapEvents
import com.parkingreservation.iuh.demologinmvp.ui.map.PermissionUtils

class MapViewFragment : BaseFragment<MapViewPresenter>()
        , MapViewContract.View
        , OnMapReadyCallback
        , GoogleMap.OnMyLocationClickListener
        , GoogleMap.OnMyLocationButtonClickListener
        , PlaceSelectionListener
        , GoogleMap.OnCameraIdleListener {

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 11
        val TAG = MapViewFragment::class.java.simpleName
        private val instance = null
        @JvmStatic
        fun getInstance() = when (instance) { null -> MapViewFragment()
            else -> instance
        }
    }

    /* event handling */
    private lateinit var onParkingEvent: MapEvents.OnParkingMakerEvents
    private lateinit var onMapSelected: MapEvents.OnParkingMapSelection
    private lateinit var onNavbarClick: MapEvents.OnNavBarEvent

    /* Resource*/
    @SuppressLint("ResourceType")
    @BindView(R.integer.map_zoom_level)
    private var MAP_ZOOM_LEVEL = 15f as Float

    @SuppressLint("ResourceType")
    @BindView(R.string.map_last_lat_location)
    private var LAST_LAT_LOCATION = ""

    @SuppressLint("ResourceType")
    @BindView(R.string.map_last_lng_location)
    private var LAST_LNG_LOCATION = ""


    lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView

    private val DEFAULT_LOCATION = LatLngBounds(LatLng(-33.880490, 151.184363), LatLng(-33.858754, 151.229596))

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_map_view, container, false) as View
        mapView = rootView.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.onResume()

        setUpAutoComplete()
        setUpClientLocation()
        mapView.getMapAsync(this)
        return rootView
    }

    // set up auto complete searching
    private lateinit var autoCompleteFindPlace: PlaceAutocompleteFragment

    private fun setUpAutoComplete() {
        val typeFilter = AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .setCountry("VN")
                .build()

        autoCompleteFindPlace = fragmentManager?.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment
        autoCompleteFindPlace.setFilter(typeFilter)
        autoCompleteFindPlace.setBoundsBias(DEFAULT_LOCATION)
        autoCompleteFindPlace.setOnPlaceSelectedListener(this)

        changeIconSearchView()
    }

    private fun changeIconSearchView() {
        val navBarMenu = (autoCompleteFindPlace.view as LinearLayout).getChildAt(0) as ImageView
        navBarMenu.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_menu, null))
        navBarMenu.setOnClickListener { onNavbarClick.onNavBarClickListener() }
    }

    private lateinit var lastKnowLocation: Location
    private lateinit var clientLocation: FusedLocationProviderClient
    @SuppressLint("MissingPermission")
    private fun setUpClientLocation() {
        clientLocation = LocationServices.getFusedLocationProviderClient(getContexts())
        clientLocation.lastLocation.addOnSuccessListener { location: Location? ->
            when {
                location != null -> {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude),
                            MAP_ZOOM_LEVEL * 1.0f))
                    lastKnowLocation = location
                }
                else -> // move to HCM city
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(10.7546664, 106.4150419),
                            MAP_ZOOM_LEVEL * 1.0f))
            }
        }
    }


    fun setOnMarkerClick(event: MapEvents.OnParkingMakerEvents) {
        this.onParkingEvent = event
    }

    fun setOnMapSelected(event: MapEvents.OnParkingMapSelection) {
        this.onMapSelected = event
    }

    fun setOnNavbarClick(event: MapEvents.OnNavBarEvent) {
        this.onNavbarClick = event
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            Log.d(TAG, "google map not null")
            mMap = map

            enableMyLocation()
            mMap.uiSettings.isMyLocationButtonEnabled = false
            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)
            mMap.setOnMarkerClickListener { marker ->
                onParkingEvent.onMarkerClickListener(marker)
                true
            }
            mMap.setOnMapClickListener { onMapSelected.onMapSelected() }
            mMap.setOnCameraIdleListener(this)

            setPositionFindMyLocation()
        } else {
            Log.d(TAG, "google map is null")
        }
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getContexts(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this.activity!!, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true)
        } else {
            // Access to the location has been granted to the app.
            mMap.isMyLocationEnabled = true
        }
    }
    @SuppressLint("ObsoleteSdkInt")
    private fun setPositionFindMyLocation() {
        val locationButton = (mapView.findViewById<View>(Integer.parseInt("1")).parent as View).findViewById<View>(Integer.parseInt("2"))
        // and next place it, on bottom right (as Google Maps app)
        val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
        // position on right bottom
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        layoutParams.setMargins(0, 0, 30, resources.getDimensionPixelSize(R.dimen.bottom_sheet_peek_height) * 3 / 2 as Int)
    }

    fun _gps() {
        val locationManager = getContexts().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val selfLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        val selfLoc = LatLng(selfLocation.latitude, selfLocation.longitude)
        val update = CameraUpdateFactory.newLatLngZoom(selfLoc, MAP_ZOOM_LEVEL)
        lastKnowLocation = selfLocation
        mMap.animateCamera(update)

    }

    override fun getContexts(): Context {
        return this.context!!
    }

    override fun instantiatePresenter(): MapViewPresenter {
        return MapViewPresenter(this)
    }


    override fun onMyLocationClick(p0: Location) {
    }

    override fun onMyLocationButtonClick(): Boolean {
        return true
    }

    override fun onPlaceSelected(p0: Place?) {
    }

    override fun onError(p0: Status?) {
    }

    override fun onCameraIdle() {
    }

}
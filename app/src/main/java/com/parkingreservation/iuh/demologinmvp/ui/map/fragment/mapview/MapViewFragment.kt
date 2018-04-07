package com.parkingreservation.iuh.demologinmvp.ui.map.fragment.mapview

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
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
import com.google.android.gms.maps.model.MarkerOptions
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseV4Fragment
import com.parkingreservation.iuh.demologinmvp.ui.map.MapEvents
import com.parkingreservation.iuh.guest.models.MapResult

@Suppress("CAST_NEVER_SUCCEEDS")
class MapViewFragment : BaseV4Fragment<MapViewPresenter>()
        , MapViewContract.View
        , OnMapReadyCallback
        , PlaceSelectionListener
        , GoogleMap.OnCameraIdleListener {

    companion object {
        const val REQUEST_FINE_LOCATION = 21
        val TAG = MapViewFragment::class.java.simpleName!!
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
//    @SuppressLint("ResourceType")
//    @BindView(R.integer.map_zoom_level)
        var MAP_ZOOM_LEVEL = 14f

//    @SuppressLint("ResourceType")
//    @BindView(R.string.map_last_lat_location)
//    lateinit var LAST_LAT_LOCATION: String
//
//    @SuppressLint("ResourceType")
//    @BindView(R.string.map_last_lng_location)
//    lateinit var LAST_LNG_LOCATION: String


    lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView

    private val default_location = LatLngBounds(LatLng(-33.880490, 151.184363), LatLng(-33.858754, 151.229596))

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
    private lateinit var autoCompleteFindPlace : PlaceAutocompleteFragment

    private fun setUpAutoComplete() {
        val typeFilter = AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_REGIONS)
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .setCountry("VN")
                .build()

        autoCompleteFindPlace = fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as PlaceAutocompleteFragment
        autoCompleteFindPlace.setFilter(typeFilter)
        autoCompleteFindPlace.setBoundsBias(default_location)
        autoCompleteFindPlace.setOnPlaceSelectedListener(this)

        changeAutoCompleteIcon()
    }

    private fun changeAutoCompleteIcon() {
        val navBarMenu = (autoCompleteFindPlace.view as? LinearLayout)?.getChildAt(0) as? ImageView
        navBarMenu?.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_menu, null))
        navBarMenu?.setOnClickListener { onNavbarClick.onNavBarClickListener() }
    }

    private lateinit var lastKnowLocation: Location
    private lateinit var clientLocation: FusedLocationProviderClient
    @SuppressLint("MissingPermission")
    private fun setUpClientLocation() {
        clientLocation = LocationServices.getFusedLocationProviderClient(getContexts())
        clientLocation.lastLocation.addOnSuccessListener { location: Location? ->
            when {
                location != null -> {
                    lastKnowLocation = location
                    moveToLocation(LatLng(location.latitude, location.longitude))
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

    fun setOnNavBarClick(event: MapEvents.OnNavBarEvent) {
        this.onNavbarClick = event
    }

    override fun onMapReady(map: GoogleMap?) {
        if (map != null) {
            Log.d(TAG, "google map not null")
            mMap = map
            setPositionFindMyLocation()
            if (checkPermissions()) {
                mMap.setOnCameraIdleListener(this)
                mMap.uiSettings.isMyLocationButtonEnabled = false
                mMap.setOnMapClickListener { onMapSelected.onMapSelected() }

                mMap.setOnMarkerClickListener { marker ->
                    onParkingEvent.onMarkerClickListener(marker)
                    true
                }
            } else {
                Log.d(TAG, "Have no permission to continue")
            }
        } else {
            Log.d(TAG, "google map is null")
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
        layoutParams.setMargins(0, 0, 30, resources.getDimensionPixelSize(R.dimen.bottom_sheet_peek_height) * 3 / 2)
    }

    @SuppressLint("MissingPermission")
    fun _gps() {
        if (checkPermissions()) {
            val locationManager = getContexts().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val selfLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            val selfLoc = LatLng(selfLocation.latitude, selfLocation.longitude)
            lastKnowLocation = selfLocation
            moveToLocation(selfLoc)
        }
    }

    private fun checkPermissions(): Boolean {
        return if (ContextCompat.checkSelfPermission(getContexts(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            true
        } else {
            requestPermissions()
            false
        }
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this.activity!!,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_FINE_LOCATION)
    }

    override fun getContexts(): Context {
        return this.activity
    }

    override fun instantiatePresenter(): MapViewPresenter {
        return MapViewPresenter(this)
    }


    fun getLastKnowLocation(): Location? {
        return lastKnowLocation
    }

    /*          Event         */
    override fun onPlaceSelected(place: Place?) {
        Log.d(TAG, "on Place selected")
        if (place != null)
            moveToLocation(place.latLng)
    }

    private fun moveToLocation(latLng: LatLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, MAP_ZOOM_LEVEL * 1.0f))
    }


    override fun onError(p0: Status?) {
    }

    override fun onCameraIdle() {
        val pos = mMap.cameraPosition.target
        val zoomLevel = mMap.cameraPosition.zoom
        Log.d(TAG, "Center location is lat = " + pos.latitude + ", long = {}" + pos.longitude + " zoom level = " + zoomLevel)
        if (zoomLevel >= MAP_ZOOM_LEVEL) {
            Log.i(TAG, "map is high enough for loading station")
            presenter.getNearbyStation(com.parkingreservation.iuh.demologinmvp.model.temp.Location(pos.latitude, pos.longitude))
        } else {
            Log.i(TAG, "there is too high to load station")
            mMap.clear()
        }
    }

    override fun loadNearbyStation(mapResult: MapResult) {
        mapResult.results.onEach {
            val mOption = MarkerOptions().position(LatLng(it.geometry.location.lat, it.geometry.location.lng)).title(it.name)
            mMap.addMarker(mOption)
        }
    }

    override fun showError(string: String) {
    }

    override fun showSuccess(string: String) {
    }


    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }


}
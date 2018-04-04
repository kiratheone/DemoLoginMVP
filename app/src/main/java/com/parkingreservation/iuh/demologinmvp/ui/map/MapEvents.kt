package com.parkingreservation.iuh.demologinmvp.ui.map

import com.google.android.gms.maps.model.Marker

/**
 *  pass all of event from mapviewfragment to mapacitivty
 *  the events on mapview be processed in activity
 */
class MapEvents {

    interface OnParkingMakerEvents {
        /**
         *
         */
        fun onMarkerClickListener(marker: Marker)
    }


    interface OnParkingMapSelection {
        /**
         *
         */
        fun onMapSelected()
    }

    interface OnNavBarEvent {
        /**
         * this event occur in case user click on hamburger button on search bar
         */
        fun onNavBarClickListener()
    }
}
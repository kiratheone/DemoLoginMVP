package com.parkingreservation.iuh.demologinmvp.ui.vehicle

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.adding.VehicleAddingFragment
import com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.list.VehicleListFragment

class VehiclePagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    companion object {
        const val NUMBER_VIEW_PAGER = 2
        var TAG = VehiclePagerAdapter::class.java.simpleName

    }
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> VehicleListFragment.getInstance()
            1 -> VehicleAddingFragment.getInstance()
            else -> VehicleListFragment.getInstance()
        }
    }

    override fun getCount(): Int {
        return NUMBER_VIEW_PAGER
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Your Vehicle"
            1 -> "Add New Vehicle"
            else -> "Your Vehicle"
        }
    }
}
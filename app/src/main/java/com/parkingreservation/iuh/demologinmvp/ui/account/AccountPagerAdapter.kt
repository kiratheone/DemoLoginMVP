package com.parkingreservation.iuh.demologinmvp.ui.account

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail.ProfileFragment
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit.EditingProfileFragment

class AccountPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    companion object {
        const val NUMBER_VIEW_PAGER = 2
        var TAG = AccountPagerAdapter::class.java.simpleName

    }
    override fun getItem(position: Int): Fragment {
      return when(position) {
           0 -> ProfileFragment.getInstance()
           1 -> EditingProfileFragment.getInstance()
           else -> ProfileFragment.getInstance()
       }
    }

    override fun getCount(): Int {
        return NUMBER_VIEW_PAGER
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Your Profile"
            1 -> "Edit Profile"
            else -> "Your Profile"
        }
    }
}
package com.parkingreservation.iuh.demologinmvp.ui.account

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.detail.ProfileFragment
import com.parkingreservation.iuh.demologinmvp.ui.account.fragment.profile.edit.EditingProfileFragment

class AccountPagerAdapter(val context: Context, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

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
            0 -> this.context.resources.getString(R.string.profile)
            1 -> this.context.resources.getString(R.string.profile_edit)
            else -> this.context.resources.getString(R.string.profile)
        }
    }
}
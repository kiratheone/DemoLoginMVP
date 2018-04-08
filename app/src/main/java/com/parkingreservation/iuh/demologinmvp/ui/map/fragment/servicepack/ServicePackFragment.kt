package com.parkingreservation.iuh.demologinmvp.ui.map.fragment.servicepack

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.parkingreservation.iuh.demologinmvp.model.ServicePack
import android.support.design.widget.BottomSheetBehavior
import com.parkingreservation.iuh.demologinmvp.R
import java.lang.reflect.Field


class ServicePackFragment : BottomSheetDialogFragment() {

    var behavior : BottomSheetBehavior<*>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_service_pack, container, false)
        val gv = rootView.findViewById<GridView>(R.id.gv_pack)
        val list = convertEnumToList()
        gv.adapter = ServicePackAdapter(context!!, list)

        try {
            val mBehaviorField = rootView.javaClass.getDeclaredField("mBehavior") as Field
            mBehaviorField.isAccessible = true
            behavior = mBehaviorField.get(rootView) as BottomSheetBehavior<*>
            behavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
        return rootView
    }

    private fun convertEnumToList(): List<ServicePack> {
        val list = ArrayList<ServicePack>()
        enumValues<ServicePackSelection>().toList().forEach { sp: ServicePackSelection ->
            list.add(ServicePack(sp.imgUrl, sp.nameA))
        }
        return list
    }

}
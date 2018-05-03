package com.parkingreservation.iuh.demologinmvp.ui.map.fragment.servicepack

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.GridView
import com.parkingreservation.iuh.demologinmvp.model.ServicePack
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.util.Log
import android.widget.AdapterView
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import java.lang.reflect.Field


class ServicePackFragment : BottomSheetDialogFragment() {

    var behavior: BottomSheetBehavior<*>? = null

    companion object {
        var TAG = ServicePackFragment::class.java.simpleName!!
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val pref = MySharedPreference(this.context!!.getSharedPreferences(MySharedPreference.SharedPrefKey.DATA_STORE,
                Context.MODE_PRIVATE))

        val rootView = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        rootView.setContentView(R.layout.fragment_service_pack)
        val gv = rootView.findViewById<GridView>(R.id.gv_pack)
        val list = convertEnumToList()


        gv!!.adapter = ServicePackAdapter(context!!, list)
        gv.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Log.d(TAG, "position = $position")
            pref.putData(MySharedPreference.SharedPrefKey.SERVICE_TYPE, position + 1, Int::class.java)
            this.dismiss()
        }
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
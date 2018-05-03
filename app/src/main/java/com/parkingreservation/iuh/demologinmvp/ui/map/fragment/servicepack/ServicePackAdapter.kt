package com.parkingreservation.iuh.demologinmvp.ui.map.fragment.servicepack

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.model.ServicePack

class ServicePackAdapter(private val mContext: Context, private val packs: List<ServicePack>) : BaseAdapter() {


    override fun getView(pos: Int, convertView: View?, groupView: ViewGroup?): View? {
        val holder: ViewHolder
        var conView = convertView
        if (conView == null) {
            holder = ViewHolder()
            conView = LayoutInflater.from(mContext).inflate(R.layout.service_pack_adapter, groupView, false)
            holder.name = conView.findViewById(R.id.tv_service_pack_name)
            holder.imgUrl = conView.findViewById(R.id.iv_service_pack_icon)
            conView.tag = holder
        } else {
            holder = conView.tag as ViewHolder
        }

        val currentPack = packs[pos]
        holder.name.text = mContext.resources.getString(currentPack.name)
        holder.imgUrl.setImageDrawable(ContextCompat.getDrawable(mContext, currentPack.imgUrl))

        return conView
    }

    override fun getItem(p0: Int): Any {
        return packs[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return packs.size
    }

    private class ViewHolder {
        lateinit var imgUrl: ImageView
        lateinit var name: TextView
    }
}
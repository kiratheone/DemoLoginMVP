package com.parkingreservation.iuh.demologinmvp.ui.map

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.parkingreservation.iuh.demologinmvp.R

class ParkingLotCoverPagerAdapter(private val context: Context, private var images: IntArray) : PagerAdapter() {

    override fun isViewFromObject(view: View, objects: Any): Boolean {
        return view == `objects` as RelativeLayout
    }

    fun setImages(images: IntArray){
        this.images = images
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(this.context).inflate(R.layout.pager_item, container, false)
        val imageView = itemView.findViewById<ImageView>(R.id.iv_pager_item)
        Glide.with(context).load(images[position]).into(imageView)
        container.addView(itemView)
        return itemView
    }

    override fun getItemPosition(`object`: Any): Int {
        return R.drawable.cheese_3
    }
}
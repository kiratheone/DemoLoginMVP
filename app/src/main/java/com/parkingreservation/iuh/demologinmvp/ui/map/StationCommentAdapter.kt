package com.parkingreservation.iuh.demologinmvp.ui.map

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import butterknife.ButterKnife
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.databinding.AdapterStationCommentBinding
import com.parkingreservation.iuh.demologinmvp.model.Comment

class StationCommentAdapter(private val context: Context, private val comments: List<Comment> )
    : RecyclerView.Adapter<StationCommentAdapter.RecyclerHolder>(){

    lateinit var binding: AdapterStationCommentBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_station_comment, parent, false)
        return RecyclerHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.comments.size
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(this.comments[position])
    }



    class RecyclerHolder(val binding: AdapterStationCommentBinding): RecyclerView.ViewHolder(binding.root) {

        /**
         * binding data to view
         */

        fun bind(comment: Comment) {
            this.binding.comment = comment
            ButterKnife.bind(this, binding.root)
            binding.executePendingBindings()
        }
    }
}
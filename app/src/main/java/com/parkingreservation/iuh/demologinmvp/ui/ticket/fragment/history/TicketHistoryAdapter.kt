package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.history

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.github.florent37.expansionpanel.ExpansionLayout
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.guest.models.TicketHistory

class TicketHistoryAdapter(private val tickets: MutableList<TicketHistory>) : RecyclerView.Adapter<TicketHistoryAdapter.RecyclerHolder>() {

    private var expandCollections : ExpansionLayoutCollection = ExpansionLayoutCollection()

    companion object {
        var TAG = TicketHistoryAdapter::class.java.simpleName
    }

    init {
        expandCollections.openOnlyOne(true)
    }

    override fun getItemCount(): Int {
        Log.d(TAG,"check item count = " +  this.tickets.size.toString())
        return this.tickets.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
        return RecyclerHolder.buildFor(parent)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind()
        expandCollections.add(holder.getExpansionLayouts())
    }


    class RecyclerHolder(item: View) : RecyclerView.ViewHolder(item) {

        private var expansionLayout : ExpansionLayout
        init {
            ButterKnife.bind(this, item)
            expansionLayout = item.findViewById(R.id.expansionLayout)
        }

        companion object {
            fun buildFor(viewGroup: ViewGroup?): RecyclerHolder {
                return RecyclerHolder(LayoutInflater.from(viewGroup?.context).inflate(R.layout.ticket_history_adapter, viewGroup, false))
            }
        }

        fun bind() {
            expansionLayout.collapse(false)
        }

        fun getExpansionLayouts(): ExpansionLayout?{
            return expansionLayout
        }
    }
}
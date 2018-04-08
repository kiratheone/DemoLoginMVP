package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.history

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.github.florent37.expansionpanel.ExpansionLayout
import com.github.florent37.expansionpanel.viewgroup.ExpansionLayoutCollection
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.databinding.TicketHistoryAdapterBinding
import com.parkingreservation.iuh.demologinmvp.model.Ticket

class TicketHistoryAdapter (val context: Context): RecyclerView.Adapter<TicketHistoryAdapter.RecyclerHolder>() {

    private var expandCollections : ExpansionLayoutCollection = ExpansionLayoutCollection()
    private var tickets: List<Ticket> = listOf()

    companion object {
        val TAG = TicketHistoryAdapter::class.java.simpleName
    }

    init {
        expandCollections.openOnlyOne(true)
    }

    override fun getItemCount(): Int {
        Log.d(TAG,"check item count = " +  this.tickets.size.toString())
        return this.tickets.size
    }


    lateinit var binding: TicketHistoryAdapterBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder {
         binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.ticket_history_adapter, parent, false)
        return RecyclerHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(tickets[position])
        expandCollections.add(holder.getExpansionLayouts())
    }

    fun updateTickets(tickets: List<Ticket>) {
        this.tickets = tickets
        notifyDataSetChanged()
    }

    class RecyclerHolder(val binding: TicketHistoryAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

        @BindView(R.id.expansionLayout)
        lateinit var expansionLayout : ExpansionLayout

        fun bind(ticket: Ticket) {
            this.binding.ticket = ticket
            expansionLayout.collapse(false)
        }

        fun getExpansionLayouts(): ExpansionLayout?{
            return expansionLayout
        }
    }
}

/*

class TicketHistoryAdapter(private val context: Context) : RecyclerView.Adapter<TicketHistoryAdapter.RecyclerHolder>() {

    private var expandCollections : ExpansionLayoutCollection = ExpansionLayoutCollection()
    private var tickets: List<Ticket> = listOf()
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
        val binding: TicketHistoryAdapterBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.ticket_history_adapter, parent, false)
        return RecyclerHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(tickets[position])
    }


    class RecyclerHolder(binding: TicketHistoryAdapterBinding) : RecyclerView.ViewHolder(binding.root) {

        private var expansionLayout : ExpansionLayout
        init {
            ButterKnife.bind(this, item)
            expansionLayout = item.findViewById(R.id.expansionLayout)
        }

        fun bind(ticket: Ticket) {
            expansionLayout.collapse(false)
            binding.ticket = ticket
        }
    }
}
*
 */
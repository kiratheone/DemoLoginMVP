package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.history

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseFragment
import com.parkingreservation.iuh.demologinmvp.databinding.FragmentTicketHistoryBinding
import com.parkingreservation.iuh.demologinmvp.model.Ticket

class TicketHistoryFragment: BaseFragment<TicketHistoryPresenter>(), TicketHistoryContract.View {

    companion object {
        private var fragment = TicketHistoryFragment()
        @JvmStatic
        fun getInstance() = fragment
    }

    lateinit var binding: FragmentTicketHistoryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticket_history, container, false)
        presenter.onViewCreated()
        val view = binding.root
        return view
    }

    override fun showError(string: String) {
    }

    override fun loadHistoryTicket(tickets: Array<Ticket>) {
       binding.adapter = TicketHistoryAdapter(tickets.toMutableList())
    }

    override fun showSuccess(string: String) {
    }

    override fun getContexts(): Context {
         return this.baseActivity
    }

    override fun instantiatePresenter(): TicketHistoryPresenter {
        return TicketHistoryPresenter(this)
    }

}
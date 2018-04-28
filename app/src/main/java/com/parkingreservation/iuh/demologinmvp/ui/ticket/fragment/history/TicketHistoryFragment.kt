package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.history

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.ButterKnife
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseFragment
import com.parkingreservation.iuh.demologinmvp.databinding.FragmentTicketHistoryBinding
import com.parkingreservation.iuh.demologinmvp.model.Tickets

class TicketHistoryFragment : BaseFragment<TicketHistoryPresenter>(), TicketHistoryContract.View {

    companion object {
        private var fragment = TicketHistoryFragment()
        @JvmStatic
        fun getInstance() = fragment
    }

    lateinit var binding: FragmentTicketHistoryBinding
    lateinit var adapter: TicketHistoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticket_history, container, false)
        binding.layoutManager = LinearLayoutManager(this.context!!)
        ButterKnife.bind(this, binding.root)

        presenter.onViewCreated()

        val view = binding.root
        return view
    }

    override fun showError(string: String) {
        showStatus(string)
    }

    override fun showSuccess(string: String) {
        showStatus(string)
    }

    private fun showStatus(s: String) {
        Toast.makeText(getContexts(), s, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.progressVisibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressVisibility = View.GONE
    }

    override fun loadHistoryTicket(tickets: Array<Tickets>) {
        adapter = TicketHistoryAdapter(this.context!!, tickets)
        binding.adapter = adapter
    }


    override fun getContexts(): Context {
        return this.baseActivity
    }

    override fun instantiatePresenter(): TicketHistoryPresenter {
        return TicketHistoryPresenter(this)
    }

}
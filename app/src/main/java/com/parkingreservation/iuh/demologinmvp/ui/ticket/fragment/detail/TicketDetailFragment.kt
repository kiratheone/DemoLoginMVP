package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.detail

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseFragment
import com.parkingreservation.iuh.demologinmvp.databinding.FragmentTicketDetailBinding
import com.parkingreservation.iuh.demologinmvp.model.Ticket

class TicketDetailFragment: BaseFragment<TicketDetailPresenter>(), TicketDetailContract.View {


    companion object {
        private var fragment = TicketDetailFragment()
        @JvmStatic
        fun getInstance() = fragment
    }

    lateinit var binding: FragmentTicketDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticket_detail, container, false)

        val view = binding.root

        presenter.onViewCreated()
        return view
    }

    override fun loadTicketDetail(ticket: Ticket) {
    }

    override fun showError(string: String) {
        showStatus(string)
    }

    override fun showSuccess(string: String) {
        showStatus(string)
    }

    override fun getContexts(): Context {
         return this.baseActivity
    }

    private fun showStatus(s: String) {
        Toast.makeText(getContexts(), s, Toast.LENGTH_LONG).show()
    }

    override fun instantiatePresenter(): TicketDetailPresenter {
        return TicketDetailPresenter(this)
    }

}
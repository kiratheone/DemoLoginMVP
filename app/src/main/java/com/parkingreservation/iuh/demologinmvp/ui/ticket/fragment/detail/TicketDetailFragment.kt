package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.detail

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseFragment
import com.parkingreservation.iuh.demologinmvp.databinding.FragmentTicketDetailBinding

class TicketDetailFragment: BaseFragment<TicketDetailPresenter>(), TicketDetailContract.View {


    companion object {
        private var fragment = TicketDetailFragment()
        @JvmStatic
        fun getInstance() = fragment
    }

    lateinit var binding: FragmentTicketDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticket_detail, container, false)
        presenter.onViewCreated()
        val view = binding.root
        return view
    }

    override fun loadTicketDetail() {
    }

    override fun showError(string: String) {
    }

    override fun showSuccess(string: String) {
    }

    override fun getContexts(): Context {
         return this.baseActivity
    }

    override fun instantiatePresenter(): TicketDetailPresenter {
        return TicketDetailPresenter(this)
    }

}
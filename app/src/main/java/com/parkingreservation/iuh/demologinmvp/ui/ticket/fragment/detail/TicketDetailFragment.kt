package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.detail

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatSpinner
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseFragment
import com.parkingreservation.iuh.demologinmvp.databinding.FragmentTicketDetailBinding
import com.parkingreservation.iuh.demologinmvp.model.Tickets

class TicketDetailFragment : BaseFragment<TicketDetailPresenter>(), TicketDetailContract.View {


    companion object {
        @SuppressLint("StaticFieldLeak")
        private var fragment = TicketDetailFragment()

        @JvmStatic
        fun getInstance() = fragment
    }

    @BindView(R.id.im_qr_code)
    lateinit var qrCodeView: AppCompatImageView

    @BindView(R.id.sp_holding_tickets)
    lateinit var holdingSpinner: AppCompatSpinner

    lateinit var binding: FragmentTicketDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ButterKnife.bind(this.baseActivity)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ticket_detail, container, false)
        val view = binding.root

        ButterKnife.bind(this, view)
        presenter.onViewCreated()
        return view
    }

    override fun loadTicketDetail(tickets: Array<Tickets>) {
        val adapterSp = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, presenter.getHoldingTickets())
        adapterSp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holdingSpinner.adapter = adapterSp
        holdingSpinner.setSelection(0)
        holdingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                binding.ticket = tickets[position]
                presenter.generateQrCode(tickets[position].qRCode)
            }
        }
    }

    override fun setQrCodeView(bitmap: Bitmap) {
        qrCodeView.setImageBitmap(bitmap)
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


    override fun getContexts(): Context {
        return this.baseActivity
    }

    override fun instantiatePresenter(): TicketDetailPresenter {
        return TicketDetailPresenter(this)
    }

}
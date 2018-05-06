package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.checked

import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.Tickets
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.service.TicketService
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.USER
import com.parkingreservation.iuh.demologinmvp.util.TokenHandling
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TicketCheckedPresenter(view: TicketCheckedContract.View) : BasePresenter<TicketCheckedContract.View>(view), TicketCheckedContract.Presenter {

    companion object {
        var TAG = TicketCheckedPresenter::class.java.simpleName
    }

    @Inject
    lateinit var ticketService: TicketService

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null

    private var holdingTickets = emptyArray<Tickets>()

    override fun onViewCreated() {
        loadTicketDetail()
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun loadTicketDetail() {
        view.showLoading()
        Log.i(TAG, "loading ticket ")
        if (isLoggedIn()) {
            val id = (pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) as User).userID!!
            val token = TokenHandling.getTokenHeader(pref)
            subscription = ticketService.getCheckedTicket(id, token)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnTerminate { view.hideLoading() }
                    .subscribe(
                            {
                                data ->
                                holdingTickets = data.filter { it.status!!.toLowerCase() != "used" && it.status.toLowerCase() != "expired" }.toTypedArray()
                                view.loadTicketDetail(holdingTickets)
                                Log.i(TAG, "load ticket history successfully")
                            },
                            {
                                view.showError("oOps!!, there is some error from server, pls try again")
                            }
                    )
        } else {
            view.showError("Hey!!, You are not logged in yet")
        }
    }

    private fun isLoggedIn(): Boolean = pref.getData(USER, User::class.java) != null

    fun generateQrCode(code: String) {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.QR_CODE, 250, 250)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            view.setQrCodeView(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    fun getHoldingTickets(): Array<String>{
        val list = mutableListOf<String>()
        holdingTickets.forEach {
            list.add(it.stationName + " - " + it.createdDate.split(" ")[1])
        }
        return list.toTypedArray()
    }
}
package com.parkingreservation.iuh.demologinmvp.ui.ticket.fragment.detail

import android.util.Log
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.parkingreservation.iuh.demologinmvp.base.BasePresenter
import com.parkingreservation.iuh.demologinmvp.model.User
import com.parkingreservation.iuh.demologinmvp.service.TicketService
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference
import com.parkingreservation.iuh.demologinmvp.util.MySharedPreference.SharedPrefKey.Companion.USER
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TicketDetailPresenter(view: TicketDetailContract.View): BasePresenter<TicketDetailContract.View>(view), TicketDetailContract.Presenter {

    companion object {
        var TAG = TicketDetailPresenter::class.java.simpleName
    }

    @Inject
    lateinit var ticketService: TicketService

    @Inject
    lateinit var pref: MySharedPreference

    private
    var subscription: Disposable? = null

    override fun onViewCreated() {
        loadTicketDetail()
        generateQrCode()
    }

    override fun onViewDestroyed() {
        subscription?.dispose()
    }

    private fun loadTicketDetail() {
        view.showLoading()
        Log.i(TAG, "loading ticket ")
        if(isLoggedIn()) {
            val id = (pref.getData(MySharedPreference.SharedPrefKey.USER, User::class.java) as User).userID
            ticketService.getCurrentTicket(id)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .doOnTerminate { view.hideLoading() }
                    .subscribe(
                            {data ->
                                view.loadTicketDetail(data[0])
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

    private fun generateQrCode() {
        val text = "14121234"
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            view.setQrCodeView(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }

    }
}
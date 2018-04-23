package com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.adding

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseFragment
import com.parkingreservation.iuh.demologinmvp.databinding.FragmentVehicleAddingBinding
import com.parkingreservation.iuh.demologinmvp.model.Vehicle
import com.parkingreservation.iuh.demologinmvp.model.VehicleTypes

class VehicleAddingFragment : BaseFragment<VehicleAddingPresenter>(), VehicleAddingContract.View {

    companion object {
        val TAG = VehicleAddingFragment::class.java.simpleName

        @SuppressLint("StaticFieldLeak")
        private var fragment = VehicleAddingFragment()

        @JvmStatic
        fun getInstance() = fragment
    }

    @BindView(R.id.edt_vehicle_license)
    lateinit var edtLicense: EditText

    @BindView(R.id.edt_vehicle_license_plate)
    lateinit var edtLicensePlate: EditText


    @BindView(R.id.spinner)
    lateinit var spinner: Spinner

    lateinit var binding: FragmentVehicleAddingBinding
    lateinit var adapter: VehicleAddingAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vehicle_adding, container, false)
        ButterKnife.bind(this, binding.root)

        adapter = VehicleAddingAdapter(this.activity!!, presenter.getListVehicleType())
        binding.adapter = adapter
        presenter.onViewCreated()

        val view = binding.root
        return view
    }

    override fun showError(string: String) {
        showStatus(string)
    }

    override fun showSuccess(string: String) {
        showStatus(string)
        this.baseActivity.finish()
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

    override fun instantiatePresenter(): VehicleAddingPresenter {
        return VehicleAddingPresenter(this)
    }

    @OnClick(R.id.save)
    fun save() {
        presenter.saveVehicle(Vehicle(
                vehicleTypeID = spinner.selectedItemPosition,
                licensePlate = edtLicensePlate.text.toString(),
                name =  edtLicense.text.toString()
        ))
    }

    @OnClick(R.id.cancel)
    fun cancel() {
        this.baseActivity.finish()
    }

}
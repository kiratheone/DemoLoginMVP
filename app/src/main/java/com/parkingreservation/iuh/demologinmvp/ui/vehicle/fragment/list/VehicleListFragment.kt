package com.parkingreservation.iuh.demologinmvp.ui.vehicle.fragment.list

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.base.BaseFragment
import com.parkingreservation.iuh.demologinmvp.databinding.FragmentVehicleListBinding
import com.parkingreservation.iuh.demologinmvp.model.VehicleModel

class VehicleListFragment: BaseFragment<VehicleListPresenter>(),VehicleListContract.View {
    companion object {
        val TAG = VehicleListFragment::class.java.simpleName

        @SuppressLint("StaticFieldLeak")
        private var fragment = VehicleListFragment()
        @JvmStatic
        fun getInstance() = fragment
    }

    @BindView(R.id.rv_vehicle_list)
    lateinit var recyclerView: RecyclerView

    lateinit var adapter: VehicleListAdapter
    lateinit var binding: FragmentVehicleListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_vehicle_list, container, false)
        ButterKnife.bind(this, binding.root)

        binding.layoutManager = LinearLayoutManager(getContexts())

        presenter.onViewCreated()
        val view = binding.root
        return view
    }

    override fun requestRemoveVehicle(vehicle: VehicleModel) {
        presenter.removeVehicle(vehicle)
    }

    override fun getContexts(): Context {
        return this.baseActivity
    }

    override fun instantiatePresenter(): VehicleListPresenter {
        return VehicleListPresenter(this)
    }

    override fun showError(string: String) {
        showStatus(string)
    }

    override fun showSuccess(string: String) {
        showStatus(string)
    }

    override fun updateVehicle(vehicles: List<VehicleModel>) {
        adapter = VehicleListAdapter(getContexts(), vehicles.toMutableList(), VehicleListFragment.fragment)
        binding.adapter = adapter
    }

    override fun showLoading() {
        binding.progressVisibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progressVisibility = View.GONE
    }

    private fun showStatus(s: String) {
        Toast.makeText(getContexts(), s, Toast.LENGTH_LONG).show()
    }


}
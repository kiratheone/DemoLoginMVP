package com.parkingreservation.iuh.demologinmvp.ui.map

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import butterknife.BindView
import butterknife.ButterKnife
import com.parkingreservation.iuh.demologinmvp.R
import com.parkingreservation.iuh.demologinmvp.databinding.AdapterTicketTypeBinding
import com.parkingreservation.iuh.demologinmvp.model.TicketTypeModels

class TicketTypeAdapter(private val context: Context, var serviceModels: LinkedHashMap<String, List<TicketTypeModels>>)
    : RecyclerView.Adapter<TicketTypeAdapter.RecyclerHolder>() {

    lateinit var binding: AdapterTicketTypeBinding

    var checkItem: HashMap<String, Int> = hashMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketTypeAdapter.RecyclerHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_ticket_type, parent, false)
        return RecyclerHolder(binding, context, checkItem)
    }

    override fun getItemCount(): Int {
        return this.serviceModels.size
    }


    override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
        holder.bind(this.serviceModels.keys.toTypedArray()[position], this.serviceModels.values.toTypedArray()[position])
    }

    class RecyclerHolder(val binding: AdapterTicketTypeBinding, val context: Context, var checkItem: HashMap<String, Int>) : RecyclerView.ViewHolder(binding.root) {

        /**
         * binding data to view
         */

        @BindView(R.id.cb_ticket_types)
        lateinit var cb: AppCompatCheckBox

        @BindView(R.id.sp_ticket_types)
        lateinit var sp: AppCompatSpinner

        fun bind(service: String, types: List<TicketTypeModels>) {
            this.binding.service = service
            ButterKnife.bind(this, binding.root)
            sp.isEnabled = false
            cb.isChecked = false
            if(service.toLowerCase() != "Đỗ xe".toLowerCase()) cb.isEnabled = false
            val lstType = mutableListOf<String>()
            types.forEach {
                lstType.add("${it.vehicleTypeName} - ${it.price}")
            }

            val adapterSp = ArrayAdapter(context, android.R.layout.simple_spinner_item, lstType)
            adapterSp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            sp.adapter = adapterSp

            cb.setOnClickListener {
                sp.isEnabled = cb.isChecked
                if (cb.isChecked) {
                    sp.setSelection(0)
                    checkItem[cb.text.toString()] = 0
                } else {
                    checkItem.remove(cb.text.toString())
                }
            }

            sp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(sp.isEnabled)
                        checkItem[cb.text.toString()] = position
                }
            }

            binding.executePendingBindings()
        }
    }
}
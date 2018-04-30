package com.parkingreservation.iuh.demologinmvp.ui.map

import com.parkingreservation.iuh.demologinmvp.R

class StationServiceUtil {
    companion object {
        fun getCarImg(): Map<String, Int> {
            var a = HashMap<String, Int>()
            a["Đỗ Xe"] = R.drawable.ic_service_pack_parking
            a["Rửa Xe"] = R.drawable.ic_service_pack_washing
            a["Sửa Xe"] = R.drawable.ic_service_pack_repair
            a["Đổ Xăng"] = R.drawable.ic_service_pack_charging
            return a
        }
    }

}
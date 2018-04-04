package com.parkingreservation.iuh.demologinmvp.ui.map

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import com.parkingreservation.iuh.demologinmvp.R

class PermissionUtils {


    companion object {
        fun requestPermission(activity: FragmentActivity, requestId: Int, permission: String, finishActivity: Boolean) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                PermissionUtils.RationalDialog.newInstance(requestId, finishActivity)
                        .show(activity.fragmentManager, "Dialog")

            } else {
                ActivityCompat.requestPermissions(activity, arrayOf<String>(permission), requestId)
            }
        }


        fun isPermissionGranted(grantPermissions: Array<out String>, grantResult: IntArray, permission: String): Boolean {
            for (index in 0 until grantPermissions.size) {
                if (permission.equals(grantPermissions[index])) {
                    return grantResult[index] == PackageManager.PERMISSION_GRANTED 
                }
            }
            return false 
        }


    }

    class RationalDialog : DialogFragment() {
        companion object {
            private val ARGUMENT_PERMISSION_REQUEST_CODE = "request code"
            private val ARGUMENT_FINISH_ACTIVITY = "finish"
            private var mFinishActivity = false

            // instance for dialog
            fun newInstance(requestCode: Int, finishActivity: Boolean): RationalDialog {
                val arguments = Bundle()
                arguments.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode)
                arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity)
                val dialog = RationalDialog() 
                dialog.arguments = arguments 
                return dialog
            }

        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val arguments = arguments
            val requestCode = arguments.getInt(ARGUMENT_PERMISSION_REQUEST_CODE)
            mFinishActivity = arguments.getBoolean(ARGUMENT_FINISH_ACTIVITY)
            return AlertDialog.Builder(activity)
                    .setMessage(R.string.permission_rationale_location)
                    .setPositiveButton(android.R.string.ok, { dialog, which ->
                        // After click on Ok, request the permission.
                        ActivityCompat.requestPermissions(activity,
                                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                requestCode)
                        // Do not finish the Activity while requesting permission.
                        mFinishActivity = false
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .create()
        }

        override fun onDismiss(dialog: DialogInterface?) {
            super.onDismiss(dialog)
            if (mFinishActivity) {
                Toast.makeText(activity,
                        R.string.permission_required_toast,
                        Toast.LENGTH_SHORT)
                        .show()
                activity.finish()
            }
        }
    }

    class PermissionDeniedDialog : DialogFragment() {
        companion object {
            const val ARGUMENT_FINISH_ACTIVITY = "finish"

            fun newInstance(finishActivity: Boolean): PermissionDeniedDialog {
                val argument = Bundle()
                argument.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity)
                val permissionDeniedDialog = PermissionDeniedDialog()
                permissionDeniedDialog.arguments = argument
                return permissionDeniedDialog
            }
        }

        var mFinishActivity = false

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            mFinishActivity = arguments.getBoolean(ARGUMENT_FINISH_ACTIVITY)
            return AlertDialog.Builder(activity)
                    .setMessage(R.string.location_permission_denied)
                    .setPositiveButton(android.R.string.ok, null)
                    .create()
        }

        override fun onDismiss(dialog: DialogInterface?) {
            super.onDismiss(dialog)
            if (mFinishActivity){
                Toast.makeText(activity,R.string.permission_required_toast,Toast.LENGTH_LONG).show()
                activity.finish()
            }
        }

    }


}
package com.bomb.common.utils

import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions.RxPermissions


object PermissionsHelp {
    const val FAILURE = "failure"
    const val FAILURE_NOT_ASK = "failure_not_ask"

    fun requestPermissions(
        fragmentActivity: FragmentActivity,
        onGranted: () -> Unit,
        onDenied: (fail: String) -> Unit,
        vararg permissions: String
    ) {
        val rxPermission = RxPermissions(fragmentActivity)
        rxPermission.requestEach(*permissions).subscribe { permission ->
            when {
                permission.granted -> onGranted()
                permission.shouldShowRequestPermissionRationale -> onDenied(FAILURE)
                else -> onDenied(FAILURE_NOT_ASK)
            }
        }
    }
}
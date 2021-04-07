package com.bomb.common.utils

import androidx.fragment.app.FragmentActivity
import com.bomb.common.net.log
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
        val size = permissions.size
        var index = 0
        var success = true
        try {
            rxPermission.requestEach(*permissions).subscribe { permission ->
                index++
                when {
                    permission.granted -> {

                    }
                    permission.shouldShowRequestPermissionRationale -> {
                        success = false
                    }
                    else ->{
                        success = false
                    }
                }
                if (index == size) {
                    if (success) {
                        onGranted()
                    } else {
                        onDenied(FAILURE)
                    }
                }

            }
        }catch (e:Exception){
            log("PermissionsHelp----->$e")
        }

    }
}
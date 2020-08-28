package com.bomb.plus.utils

import android.Manifest
import androidx.fragment.app.FragmentActivity
import com.bomb.common.utils.PermissionsHelp

object PermissionsUtils {

    val storageStr = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val storageString = arrayOf(storageStr)

    /**
     * 请求存储权限
     */
    fun requestStorage(
        fragmentActivity: FragmentActivity,
        onGranted: () -> Unit,
        onDenied: (fail: String) -> Unit
    ) {
        PermissionsHelp.requestPermissions(fragmentActivity, onGranted, onDenied,
            storageStr
        )
    }
}
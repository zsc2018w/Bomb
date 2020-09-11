package com.bomb.plus.utils

import android.Manifest
import androidx.fragment.app.FragmentActivity
import com.bomb.common.utils.PermissionsHelp

object PermissionsUtils {

    val storageStr = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val ss=Manifest.permission.READ_EXTERNAL_STORAGE
    val storageString = arrayOf(storageStr,ss)

    val recordAudioStr = Manifest.permission.RECORD_AUDIO
    val recordAudioString= arrayOf(recordAudioStr)

    /**
     * 请求存储权限
     */
    fun requestStorage(
        fragmentActivity: FragmentActivity,
        onGranted: () -> Unit,
        onDenied: (fail: String) -> Unit
    ) {
        PermissionsHelp.requestPermissions(fragmentActivity, onGranted, onDenied,
            *storageString
        )
    }


    fun requestRecordAudio(
        fragmentActivity: FragmentActivity,
        onGranted: () -> Unit,
        onDenied: (fail: String) -> Unit
    ) {
        PermissionsHelp.requestPermissions(fragmentActivity, onGranted, onDenied,
            *recordAudioString
        )
    }
}
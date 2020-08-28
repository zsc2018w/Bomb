package com.bomb.common.utils

import android.annotation.SuppressLint
import android.widget.Toast
import com.bomb.common.core.ProHelper

object ToastUtils {
    var toast: Toast? = null

    @SuppressLint("ShowToast")
    fun show(str: String?) {
        if (str.isNullOrBlank()) {
            return
        }
        if (toast == null) {
            toast = Toast.makeText(ProHelper.mApp, "", Toast.LENGTH_SHORT)
        }
        toast?.apply {
            setText(str)
            show()
        }
    }
}
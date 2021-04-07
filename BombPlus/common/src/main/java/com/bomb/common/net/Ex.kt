package com.bomb.common.net

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.bomb.common.net.exception.ApiException

typealias Error = suspend (ne: ApiException) -> Unit
typealias Fail = suspend (throwable: Throwable) -> Unit
typealias Complete = suspend () -> Unit

fun Any.log(str: String) {
    Log.d("BombPlus", "---->$str")
}

inline fun <reified T> Activity.toNextPage(paramsIntent: Intent? = null) {
    val mIntent = paramsIntent ?: Intent()
    mIntent.setClass(this, T::class.java)
    startActivity(mIntent)
}




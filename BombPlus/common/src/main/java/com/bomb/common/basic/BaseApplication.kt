package com.bomb.common.basic


import android.app.Application
import android.content.Context
import com.bomb.common.core.ProHelper
import com.bomb.common.utils.ActivityManager

open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        ProHelper.init(this)
        ActivityManager.init(this)
    }


}
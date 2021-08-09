package com.bomb.common.basic


import android.app.Application
import android.content.Context
import android.os.Debug
import com.bomb.common.core.ProHelper
import com.bomb.common.utils.ScreenManager

open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        Debug.startMethodTracing("tracefilename")
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        ProHelper.init(this)
        ScreenManager.init(this)
    }


}
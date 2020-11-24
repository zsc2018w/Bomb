package com.bomb.common.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.bomb.common.net.log
import java.util.*

object ActivityManager : Application.ActivityLifecycleCallbacks {
    private val activityList = Stack<Activity>()

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        log("入栈---onCreate-->${activity.javaClass.simpleName}")
        activityList.push(activity)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        log("出栈---onDestroy-->${activity.javaClass.simpleName}")
        activityList.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }


}
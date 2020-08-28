package com.bomb.common.core

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

object ProHelper {

    lateinit var mApp: Application

    /**
     * 初始化
     */
    fun init(application: Application) {
        mApp = application
    }

    /**
     * 获取字符串
     */
    fun getString(strRes: Int): String {
        return mApp.getString(strRes)
    }

    /**
     * 获取字符串数组
     */
    fun getStringArray(arrayId: Int): Array<String> {
        return mApp.resources.getStringArray(arrayId)
    }

    /**
     * 获取Color
     */
    fun getColor(color: Int): Int {
        return ContextCompat.getColor(mApp, color)
    }

    /**
     * 获取图片
     */
    fun getDrawable(drawable: Int): Drawable {
        return ContextCompat.getDrawable(mApp, drawable)!!
    }


}
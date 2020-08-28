package com.bomb.plus.test

import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.bomb.common.basic.BaseViewModel
import com.bomb.common.net.log
import com.bomb.common.net.http.HttpUtils
import com.bomb.plus.service.TestService


class TViewModel : BaseViewModel() {

    private val ts: TestService by lazy {
        HttpUtils.getInstance().createService(TestService::class.java)
    }
    val liveData = MutableLiveData<String>()

    fun getTestData() {
     /*   launch({ ts.getOne() },
            success = {
                liveData.postValue(it)
                log("vm--success---Thread---{${Thread.currentThread().name}}-----isMain----{${Looper.getMainLooper()== Looper.myLooper()}}")
            },error = {
                log("vm--error---Thread---{${Thread.currentThread().name}}-----isMain----{${Looper.getMainLooper()== Looper.myLooper()}}")
            },complete = {
                log("vm--complete---Thread---{${Thread.currentThread().name}}-----isMain----{${Looper.getMainLooper()== Looper.myLooper()}}")
            })
    }*/
    }
}
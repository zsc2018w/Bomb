package com.bomb.plus.gank.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bomb.common.basic.BaseViewModel
import com.bomb.common.net.log
import com.bomb.plus.gank.bean.GankBean
import com.bomb.plus.gank.bean.GankTypeBean
import com.bomb.plus.gank.repository.GankRepo

class GankViewModel : BaseViewModel() {

    val gankTypeliveData = MutableLiveData<GankTypeBean>()

    val gankGirlliveData = MutableLiveData<GankBean>()

    val gankArticleliveData = MutableLiveData<GankBean>()

    private val gankRepo: GankRepo by lazy {
        GankRepo(viewModelScope, errorLiveData)
    }


    fun getGankTypeData() {
        gankRepo.requestGankType(gankTypeliveData)
    }


    fun getGankGirlData(isRefresh: Boolean = false) {
        gankRepo.requestGirlData(isRefresh, gankGirlliveData)
    }

    fun getGankArticleData(isRefresh: Boolean = false, type: String) {
        gankRepo.requestArticleData(isRefresh, type, gankArticleliveData)
    }

}
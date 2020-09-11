package com.bomb.plus.gank.repository

import androidx.lifecycle.MutableLiveData
import com.bomb.common.basic.BaseRepository
import com.bomb.common.net.exception.ApiException
import com.bomb.common.net.http.HttpUtils
import com.bomb.common.net.log
import com.bomb.common.utils.ToastUtils
import com.bomb.plus.core.ServiceConfigEnum
import com.bomb.plus.gank.GankService
import com.bomb.plus.gank.bean.GankBean
import com.bomb.plus.gank.bean.GankTypeBean
import kotlinx.coroutines.CoroutineScope

class GankRepo(
    coroutineScope: CoroutineScope,
    errorLiveData: MutableLiveData<ApiException>
) : BaseRepository(coroutineScope, errorLiveData) {

    private var girlPage = 1
    private var articlePage = 1

    private val gankService: GankService by lazy {
        HttpUtils.getInstance(ServiceConfigEnum.NORAML.value).createService(GankService::class.java)
    }

    fun requestGankType(liveData: MutableLiveData<GankTypeBean>) {
        launch({
            gankService.requestGankType()
        }, success = {
            liveData.value = it
        }, error = {
            errorLiveData.value = it
        })
    }


    fun requestGirlType() {
        launch({
            gankService.requestDirlType()
        }, success = {

        }, error = {

        })
    }

    fun requestGirlData(isRefresh: Boolean, liveData: MutableLiveData<GankBean>) {
        if (isRefresh) {
            girlPage = 1
        } else {
            girlPage++
        }
        requestGirlData(girlPage, liveData)
    }

    fun requestArticleData(isRefresh: Boolean, type: String, liveData: MutableLiveData<GankBean>) {
        if (isRefresh) {
            articlePage = 1
        } else {
            articlePage++
        }
        requestArticleData(type, articlePage, liveData)
    }


    private fun requestGirlData(page: Int, liveData: MutableLiveData<GankBean>) {
        launch({
            gankService.requestGirlData(page)
        }, success = {
            log("getItem----postValue")
            it.refresh = page == 1
            liveData.value = it

        }, error = {
            ToastUtils.show(it.errMsg)
        })
    }

    private fun requestArticleData(type: String, page: Int, liveData: MutableLiveData<GankBean>) {
        launch({
            gankService.requestAticleData(type, page)
        }, success = {
            it.refresh = page == 1
            liveData.value = it
        }, error = {
            ToastUtils.show(it.errMsg)
        })
    }


}
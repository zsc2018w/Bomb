package com.bomb.plus.eye.repository

import androidx.lifecycle.MutableLiveData
import com.bomb.common.basic.BaseRepository
import com.bomb.common.net.exception.ApiException
import com.bomb.common.net.http.HttpUtils
import com.bomb.common.utils.ToastUtils
import com.bomb.plus.eye.bean.HomeBean
import com.bomb.plus.eye.EyeService
import com.bomb.plus.core.ServiceConfigEnum
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MainRepo(
    coroutineScope: CoroutineScope,
    errorLiveData: MutableLiveData<ApiException>
) : BaseRepository(coroutineScope, errorLiveData) {

    private val eyeService: EyeService by lazy {
        HttpUtils.getInstance(ServiceConfigEnum.EYE.value).createService(EyeService::class.java)
    }

    private var bannerHomeBean: HomeBean? = null
    private var nextPageUrl: String? = ""

    fun requestHomeData(liveData: MutableLiveData<HomeBean>) {
        launch({
            var firstHomeData: HomeBean? = null
            flow {
                val firstHomeBean = eyeService.requestFirstHomeData(1)
                val bannerItemList = firstHomeBean.issueList[0].itemList
                bannerItemList.filter { item ->
                    item.type == "banner2" || item.type == "horizontalScrollCard"
                }.forEach { item ->
                    //移除 item
                    bannerItemList.remove(item)
                }
                bannerHomeBean = firstHomeBean
                val homeBean = eyeService.requestHomeMoreData(firstHomeBean.nextPageUrl)
                val newBannerItemList = homeBean.issueList[0].itemList

                newBannerItemList.filter { item ->
                    item.type == "banner2" || item.type == "horizontalScrollCard"
                }.forEach { item ->
                    //移除 item
                    newBannerItemList.remove(item)
                }
                bannerHomeBean!!.issueList[0].count = bannerHomeBean!!.issueList[0].itemList.size
                bannerHomeBean?.issueList!![0].itemList.addAll(newBannerItemList)
                emit(bannerHomeBean)
            }.collect {
                firstHomeData = it
            }
            firstHomeData
        }, success = {
            nextPageUrl = it?.nextPageUrl
            liveData.postValue(it)
        }, error = {
            errorLiveData.value = it
            ToastUtils.show(it.errMsg)
        }, complete = {

        })
    }


    fun requestHomeMoreData(liveData: MutableLiveData<HomeBean>) {
        launch({
            var homeData: HomeBean?=null
            flow {
                emit(eyeService.requestHomeMoreData(nextPageUrl!!))
            }.map {
                val newItemList = it.issueList[0].itemList

                newItemList.filter { item ->
                    item.type == "banner2" || item.type == "horizontalScrollCard"
                }.forEach { item ->
                    //移除 item
                    newItemList.remove(item)
                }
                it
            }.collect {
                homeData = it
            }
            homeData

        }, success = {
            nextPageUrl = it?.nextPageUrl
            liveData.postValue(it)
        }, error = {

        })
    }


}
package com.bomb.plus.gank.repository

import androidx.lifecycle.MutableLiveData
import com.bomb.common.basic.BaseRepository
import com.bomb.common.net.exception.ApiException
import com.bomb.common.net.http.HttpUtils
import com.bomb.common.utils.ToastUtils
import com.bomb.plus.eye.bean.HomeBean
import com.bomb.plus.eye.EyeService
import com.bomb.plus.core.ServiceConfigEnum
import com.bomb.plus.gank.GankService
import com.bomb.plus.gank.bean.GankGirlBean
import kotlinx.coroutines.CoroutineScope

class GirlRepo(
    coroutineScope: CoroutineScope,
    errorLiveData: MutableLiveData<ApiException>
) : BaseRepository(coroutineScope, errorLiveData) {

    private val gankService: GankService by lazy {
        HttpUtils.getInstance(ServiceConfigEnum.NORAML.value).createService(GankService::class.java)
    }


    fun requestGirlData(page: Int, liveData: MutableLiveData<GankGirlBean>) {
        launch({
            gankService.requestGirlData(page)
        }, success = {
            liveData.value = it
        }, error = {
            ToastUtils.show(it.errMsg)
        })
    }

}
package com.bomb.plus.eye.repository

import androidx.lifecycle.MutableLiveData
import com.bomb.common.basic.BaseRepository
import com.bomb.common.net.exception.ApiException
import com.bomb.common.net.http.HttpUtils
import com.bomb.common.utils.ToastUtils
import com.bomb.plus.eye.bean.EyeBean
import com.bomb.plus.eye.EyeService
import com.bomb.plus.core.ServiceConfigEnum
import kotlinx.coroutines.CoroutineScope

class VideoDetailsRepo(
    coroutineScope: CoroutineScope,
    errorLiveData: MutableLiveData<ApiException>
) : BaseRepository(coroutineScope, errorLiveData) {

    private val eyeService: EyeService by lazy {
        HttpUtils.getInstance(ServiceConfigEnum.EYE.value).createService(EyeService::class.java)
    }


    fun requestRelatedVideoData(id: Long, liveData: MutableLiveData<EyeBean.Issue>) {
        launch({
            eyeService.getRelatedData(id)
        }, success = {
            liveData.value = it
        }, error = {
           ToastUtils.show(it.errMsg)
        })

    }


}
package com.bomb.plus.eye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bomb.common.basic.BaseViewModel
import com.bomb.plus.eye.bean.EyeBean
import com.bomb.plus.eye.repository.VideoDetailsRepo

class VideoDetailsViewModel : BaseViewModel() {

    val reletedLiveData = MutableLiveData<EyeBean.Issue>()

   private  val detailsRepo: VideoDetailsRepo by lazy {
        VideoDetailsRepo(viewModelScope, errorLiveData)
    }


    fun getReletedVideoData(id: Long) {
        detailsRepo.requestRelatedVideoData(id, reletedLiveData)
    }

}
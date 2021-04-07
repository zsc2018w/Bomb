package com.bomb.plus.eye.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bomb.common.basic.BaseViewModel
import com.bomb.plus.eye.bean.EyeBean
import com.bomb.plus.eye.repository.MainRepo

class HomeViewModel : BaseViewModel() {

     val homeFirstData = MutableLiveData<EyeBean>()
     val homeMoreData = MutableLiveData<EyeBean>()

    private val mainRepository: MainRepo by lazy {
        MainRepo(viewModelScope, errorLiveData)
    }


    fun getHomeData() {
        mainRepository.requestHomeData(homeFirstData)
    }

    fun getMoreData() {
        mainRepository.requestHomeMoreData(homeMoreData)
    }


}
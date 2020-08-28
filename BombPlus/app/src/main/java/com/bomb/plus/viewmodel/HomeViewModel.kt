package com.bomb.plus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bomb.common.basic.BaseViewModel
import com.bomb.plus.bean.HomeBean
import com.bomb.plus.repository.MainRepo

class HomeViewModel : BaseViewModel() {

     val homeFirstData = MutableLiveData<HomeBean>()
     val homeMoreData = MutableLiveData<HomeBean>()

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
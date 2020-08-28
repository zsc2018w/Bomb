package com.bomb.common.basic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bomb.common.net.exception.ApiException


/**
 * AndroidViewModel 只是一个 持有应用上下文的 vm 并无其他差别
 * 一般用途 根单例 基本一致 继承 AndroidViewModel 必须添加 参数为Application的构造器 否则反射实例会抛出异常
 */
abstract class BaseViewModel : ViewModel() {

     val errorLiveData = MutableLiveData<ApiException>()


}
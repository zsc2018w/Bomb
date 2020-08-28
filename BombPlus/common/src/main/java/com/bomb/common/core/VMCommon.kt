package com.bomb.common.core

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.bomb.common.basic.BaseViewModel

/**
 * 如果vm 需要透传参数 怎么办？
 * 实现 ViewModelProvider.Factory  进行拓展 （其实一般并不需要 感觉需要的 挺 orphan）
 */
object VMCommon {

    /**
     * Activity 或者 Fragment 中获取 vm
     */
    fun <T : BaseViewModel> getVM(viewModelStoreOwner: ViewModelStoreOwner, cls: Class<T>): T? {
        return ViewModelProvider(viewModelStoreOwner).get(cls)
    }

    /**
     * 获取拓展的带参数的 vm
     */
    fun <T : BaseViewModel> getVM(viewModelStoreOwner: ViewModelStoreOwner, factory: ViewModelProvider.Factory, cls: Class<T>): T? {
        return ViewModelProvider(viewModelStoreOwner, factory).get(cls)
    }

    /**
     * 直接反射创建无参数实例 其实跟 new 没啥区别
     */
    fun <T : BaseViewModel> getVM(cls: Class<T>): T? {
        return ViewModelProvider.NewInstanceFactory().create(cls)
    }


}
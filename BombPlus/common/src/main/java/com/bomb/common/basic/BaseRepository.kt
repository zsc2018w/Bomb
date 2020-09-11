package com.bomb.common.basic

import androidx.lifecycle.MutableLiveData
import com.bomb.common.net.Complete
import com.bomb.common.net.launch.LaunchFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.bomb.common.net.Error
import com.bomb.common.net.exception.ApiException
import com.bomb.common.net.log


open class BaseRepository(
    private val coroutineScope: CoroutineScope,
    val errorLiveData: MutableLiveData<ApiException>
) {

    /**
     * 所有网络请求在 viewModelScope
     * 页面销毁 自动调用 ViewModel #onCleared 方法取消所有协程
     * Lifecycle onDestroy  -->  ViewModelStore clear  -->  ViewModel clear
     */
    private fun launchUI(block: suspend CoroutineScope.() -> Unit) = coroutineScope.launch {
        block()
    }

    fun <T> launch(
        block: suspend CoroutineScope.() -> T,
        success: suspend (T) -> Unit,
        error: Error? = null,
        complete: Complete? = null
    ) {
        launchUI {
            LaunchFactory.handleException({
                withContext(Dispatchers.IO) {
                    block()
                }
            }, success = {
                success(it)
            }, error = {
                error?.invoke(it)
                log("http--error--${it.errMsg}")
            }, complete = {
                complete?.invoke()
            })
        }
    }

    fun postError(){

    }

}
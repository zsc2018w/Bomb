package com.bomb.common.net.launch

import com.bomb.common.net.Complete
import com.bomb.common.net.Error
import com.bomb.common.net.Fail
import com.bomb.common.net.exception.ExceptionHandle
import com.bomb.common.utils.ToastUtils
import kotlinx.coroutines.*

object LaunchFactory {

    /**
     * 开始协程执行任务
     * 主线程回调
     */
    fun <T> launchTask(block: suspend () -> T, success: (T) -> Unit, fail: Fail? = null): Job {
        return GlobalScope.launch {
            runCatching {
                withContext(Dispatchers.IO) {
                    block()
                }
            }.onSuccess {
                withContext(Dispatchers.Main) {
                    success(it)
                }
            }.onFailure {
                withContext(Dispatchers.Main) {
                    fail?.invoke(it)
                }
            }
        }
    }

    /**
     * 普通请求调用
     * 需自己处理 job
     * 主线程回调
     */
    fun <T> launch(
        block: suspend CoroutineScope.() -> T,
        success: (T) -> Unit,
        error: Error? = null,
        complete: Complete? = null
    ): Job {
        return GlobalScope.launch {
            handleException({
                withContext(Dispatchers.IO) {
                    block()
                }
            }, success = {
                withContext(Dispatchers.Main) {
                    success(it)
                }
            }, error = {
                withContext(Dispatchers.Main) {
                    error?.invoke(it)
                    toast(it.errMsg)
                }
            }, complete = {
                withContext(Dispatchers.Main) {
                    complete?.invoke()
                }

            })
        }
    }

    /**
     * 统一处理Exception
     */
    suspend fun <T> handleException(
        block: suspend CoroutineScope.() -> T,
        success: suspend (T) -> Unit,
        error: Error,
        complete: Complete
    ) {
        coroutineScope {
            try {
                success(block())
            } catch (e: Throwable) {
                e.printStackTrace()
                error.invoke(ExceptionHandle.handleException(e))
            } finally {
                complete.invoke()
            }
        }
    }

    private fun toast(str: String) {
        ToastUtils.show(str)
    }

}
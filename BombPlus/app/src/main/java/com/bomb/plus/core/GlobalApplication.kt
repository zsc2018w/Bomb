package com.bomb.plus.core


import com.bomb.common.basic.BaseApplication
import com.bomb.common.net.http.HttpUtils
import com.bomb.common.utils.TestTimeMonitor
import com.bomb.plus.eye.EyeHttpConfig
import com.bomb.plus.eye.video.DisplayManager
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout



class GlobalApplication : BaseApplication() {
    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ -> ClassicsHeader(context) }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ -> ClassicsFooter(context) }
    }
    override fun onCreate() {
        TestTimeMonitor.get().start("App-create")
        super.onCreate()
        initOkHttp()
        DisplayManager.init(this)
        TestTimeMonitor.get().end("App-create")
    }

    private fun initOkHttp() {
        HttpUtils.getInstance(ServiceConfigEnum.EYE.value).apply {
            setOkHttpClient(
                getOkHttpBuilder()
                    .addInterceptor(EyeHttpConfig.getParameterInterceptor())
                   // .addInterceptor(EyeHttpConfig.getHeaderInterceptor())
                    .build()
            )
        }
    }


}
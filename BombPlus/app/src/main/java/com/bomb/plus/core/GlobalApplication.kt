package com.bomb.plus.core


import com.bomb.common.basic.BaseApplication
import com.bomb.common.net.http.HttpUtils
import com.bomb.common.net.log
import com.bomb.common.utils.TestTimeMonitor
import com.bomb.plus.R
import com.bomb.plus.eye.EyeHttpConfig
import com.bomb.plus.eye.video.DisplayManager
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import java.util.HashMap


class GlobalApplication : BaseApplication() {
    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            ClassicsHeader(context)

        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context)
        }
    }

    override fun onCreate() {
        TestTimeMonitor.get().start("App-create")
        super.onCreate()
        initOkHttp()
        DisplayManager.init(this)
        initX5Kernel()
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

    /**
     * 初始化x5内核
     */
    private fun initX5Kernel() {
        // 在调用TBS初始化、创建WebView之前进行如下配置
        val map = HashMap<String, Any>()
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true)
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true)
        QbSdk.initTbsSettings(map)

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核
        val cb = object : QbSdk.PreInitCallback {

            override fun onViewInitFinished(finish: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                log("x5 onViewInitFinished is $finish")
            }

            override fun onCoreInitFinished() {

            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, cb)

    }


}
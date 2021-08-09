package com.bomb.common.net.http

import com.bomb.common.net.log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class HttpUtils private constructor() {
    //retrofit
    private var mRetrofit: Retrofit? = null
    //okHttpClient
    private var okHttpClient: OkHttpClient? = null

    companion object {
        private val instanceMap = HashMap<String, HttpUtils>()
        private const val TIMEOUT: Long = 10

        @JvmStatic
        @Synchronized
        fun getInstance(key: String = ""): HttpUtils {
            return instanceMap[key] ?: HttpUtils().apply {
                instanceMap[key] = this
            }
        }


    }

    fun setOkHttpClient(client: OkHttpClient) {
        this.okHttpClient = client
    }

    fun getOkHttpBuilder(): OkHttpClient.Builder {
        val logInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            log(it)
        })
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            //.addInterceptor(addQueryParameterInterceptor())
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
    }

    private fun initOkHttp() {
        if (okHttpClient == null) {
            okHttpClient = getOkHttpBuilder().build()
        }
    }

    private fun initRetrofit() {
        initOkHttp()
        if (mRetrofit == null) {
            mRetrofit = Retrofit.Builder()
                //因为baseurl 不能为 空 随便传入一个
                .baseUrl("https://blog.csdn.net/")
                .client(okHttpClient!!)
                //转换字符串返回这个必须要放在上面否则解析异常 估计没走到它   nb  nb
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                //  .addConverterFactory(LenientGsonConverterFactory.create())
                .build()
        }
    }


    fun <T> createService(cls: Class<T>): T {
        initRetrofit()
        return mRetrofit!!.create(cls)
    }


}
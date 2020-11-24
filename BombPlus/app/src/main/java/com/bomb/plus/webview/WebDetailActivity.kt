package com.bomb.plus.webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View

import com.bomb.common.basic.BaseActivity
import com.bomb.common.net.log
import com.bomb.common.utils.StatusBarUtil
import com.bomb.plus.R
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

import kotlinx.android.synthetic.main.activity_web_detail.*

class WebDetailActivity : BaseActivity() {

    private var url: String = ""

    companion object {

        fun startActivity(context: Context, url: String) {
            val intent = Intent()
            intent.setClass(context, WebDetailActivity::class.java)
            intent.putExtra("URL", url)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_web_detail
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        StatusBarUtil.darkModel(this, true)
        initIntent()
        initConfig()
        initClient()
        loadUrl(url)
        statusLayout.showLoading()
        iv_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initIntent() {
        url = intent.getStringExtra("URL") ?: ""
    }

    private fun loadUrl(url: String) {
        webView.loadUrl(url)
    }

    private fun initConfig() {
        // webView.settings.javaScriptEnabled = true
        webView.settings?.apply {
            //支持通过JS打开新窗口
            javaScriptCanOpenWindowsAutomatically = true
            builtInZoomControls = true
            mixedContentMode = mixedContentMode
            displayZoomControls = false
            // userAgentString = getZNTUA(webview.getSettings().getUserAgentString())
            setSupportZoom(true)
            setRenderPriority(com.tencent.smtt.sdk.WebSettings.RenderPriority.HIGH)
            blockNetworkImage = true
            domStorageEnabled = true
            setAppCacheMaxSize((1024 * 1024 * 8).toLong())
            //设置缓冲路径
            val appCachePath = cacheDir.absolutePath
            setAppCachePath(appCachePath)
            allowFileAccess = true
            //开启APP缓存
            setAppCacheEnabled(true)
            databaseEnabled = true
            //根据网络状态加载缓冲，有网：走默认设置；无网络：走加载缓冲
            cacheMode = com.tencent.smtt.sdk.WebSettings.LOAD_DEFAULT
            /*  if (NetworkManager.getInstance().isNetworkAvailable()) {
                webview.getSettings().setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_DEFAULT)
            } else {
                webview.getSettings()
                    .setCacheMode(com.tencent.smtt.sdk.WebSettings.LOAD_CACHE_ELSE_NETWORK)
            }*/
            //支持显示PC宽屏页面的全部内容
            useWideViewPort = true
            loadWithOverviewMode = true
        }


    }

    private fun initClient() {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(webview: WebView?, url: String?, bitmap: Bitmap?) {
                super.onPageStarted(webview, url, bitmap)
                log("onPageStarted-->$url")
            }

            override fun onPageFinished(webview: WebView?, url: String?) {
                super.onPageFinished(webview, url)
                log("onPageFinished-->$url")
                statusLayout.showContent()
                if (webview != null && !webview.title.isNullOrBlank()) {
                    setTitle(webview.title)
                }
            }

            override fun onReceivedError(
                webview: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(webview, request, error)
                log("onReceivedError-->${error?.description ?: "0"}")
            }
        }
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(webview: WebView?, process: Int) {
                super.onProgressChanged(webview, process)
                if (process == 100) {
                    progressBar.visibility = View.GONE
                } else {
                    progressBar.progress = process
                    progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setTitle(str: String) {
        tvTitle.text = str
    }
}
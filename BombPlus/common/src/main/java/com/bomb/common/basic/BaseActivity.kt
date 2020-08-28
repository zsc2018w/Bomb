package com.bomb.common.basic

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE
import kotlinx.coroutines.GlobalScope


abstract class BaseActivity : FragmentActivity() {

    private var loadingDialog: ZLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getLayoutId() != 0) {
            setContentView(getLayoutId())
        }
        initView()

    }

    open fun getLayoutId(): Int {
        return 0
    }

    abstract fun initView()


    open fun <T> toNextPage(cls: Class<T>) {
        val intent = Intent()
        intent.setClass(this, cls)
        startActivity(intent)
    }


    fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = ZLoadingDialog(this)
            loadingDialog!!.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                .setLoadingColor(Color.BLACK)//颜色
                .setHintText("加载中...")
        }
        loadingDialog?.show()
    }

    fun closeLoading(){
        loadingDialog?.dismiss()
    }

}
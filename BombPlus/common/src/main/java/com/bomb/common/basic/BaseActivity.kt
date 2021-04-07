package com.bomb.common.basic

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.bomb.common.widget.LoadingDialog

abstract class BaseActivity : FragmentActivity() {

    private var loadingDialog: LoadingDialog? = null

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




    fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog(this).setCancel(
                cancelable = false,
                canceledOnTouchOutside = false
            )
        }
        loadingDialog?.show()
    }

    fun closeLoading() {
        loadingDialog?.dismiss()
    }

}
package com.bomb.plus.splash


import com.bomb.common.basic.BaseActivity
import com.bomb.common.net.log
import com.bomb.common.net.toNextPage
import com.bomb.common.utils.PermissionsHelp
import com.bomb.common.utils.ToastUtils
import com.bomb.plus.main.MainActivity
import com.bomb.plus.utils.PermissionsUtils
import com.bomb.plus.R


class SplashActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        requestPermissions()
    }

    private fun requestPermissions() {
        PermissionsUtils.requestStorage(this, onGranted = {
            log("success")
            toNextPage<MainActivity>()
           // toNextPage(TestActivity::class.java)
            finish()
        }, onDenied = {
            log("$it")
            if (PermissionsHelp.FAILURE == it) {
                requestPermissions()
            } else {
                ToastUtils.show("game over")
            }

        })
    }

}

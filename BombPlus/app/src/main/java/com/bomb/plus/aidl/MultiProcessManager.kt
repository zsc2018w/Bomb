package com.bomb.plus.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.bomb.common.core.ProHelper
import com.bomb.common.net.log
import com.bomb.common.utils.ProcessUtil

object MultiProcessManager {
    private var mContext: Context? = null
    private var mPkeName: String? = null
    private var mIsBound: Boolean = false
    private var mProcessManager: IProcessManager? = null

    fun register(context: Context) {
        this.mContext = context
        mPkeName = context.packageName
        bindService()

    }

    fun unRegister() {
        if (mIsBound) {
            mContext?.unbindService(serviceConnection)
        }
    }

    fun  onPost(msg: String){
        mProcessManager?.post(msg)
    }

    private fun bindService() {
        val intent = Intent()
        intent.component = ComponentName(mPkeName!!, ProcessManagerService::class.java.name)
        mIsBound =
            mContext?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE) ?: false
    }


    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mProcessManager = IProcessManager.Stub.asInterface(service)
            mProcessManager?.register(callBack)
        }

    }


    private val callBack = object : IProcessCallBack.Stub() {
        override fun onPost(msg: String?) {
            log("ipc_--------->onPost---->$msg")
        }

        override fun processName(): String {
            return ProcessUtil.getCurrentProcessName(ProHelper.mApp) ?: "哈哈"
        }

    }

}
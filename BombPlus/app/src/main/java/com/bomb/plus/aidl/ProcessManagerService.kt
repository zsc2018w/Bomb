package com.bomb.plus.aidl

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.RemoteCallbackList
import com.bomb.common.net.log
import com.bomb.plus.core.GlobalApplication
import java.security.Permission

class ProcessManagerService : Service() {

    private val processCallBackList = RemoteCallbackList<IProcessCallBack>()

    override fun onBind(intent: Intent?): IBinder? {
        val permission = checkCallingOrSelfPermission("test.permission.yz")
        if (permission == PackageManager.PERMISSION_GRANTED) {
           log("ipc_权限验证通过")
        }
        if(permission == PackageManager.PERMISSION_DENIED){
            log("ipc_权限验证失败")
        }
        return mBinder
    }


    override fun onCreate() {
        super.onCreate()
        (application as GlobalApplication).logProcess()
    }

    private val mBinder = object : IProcessManager.Stub() {
        override fun register(callback: IProcessCallBack?) {
            log("ipc_---->ProcessManagerService---->register----${callback?.processName()}")
            processCallBackList.register(callback)
        }

        override fun unregister(callback: IProcessCallBack?) {
            log("ipc_---->ProcessManagerService---->unregister----${callback?.processName()}")
            processCallBackList.unregister(callback)
        }

        override fun post(msg: String?) {
            log("ipc_---->ProcessManagerService---->post----$msg")
            val count: Int = processCallBackList.beginBroadcast()

            for (i in 0 until count) {
                val item= processCallBackList.getBroadcastItem(i)
                log("ipc_---->ProcessManagerService---->post---->${item.processName()}----$msg")
                item.onPost(msg)

            }

            processCallBackList.finishBroadcast()
        }

    }

}
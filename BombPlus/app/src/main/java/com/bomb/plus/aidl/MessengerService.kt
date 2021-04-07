package com.bomb.plus.aidl

import android.app.Service
import android.content.Intent
import android.os.*
import com.bomb.common.net.log
import java.lang.Exception

class MessengerService : Service() {


    private val mMessenger = Messenger(MessengerHandler())

    private class MessengerHandler : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message) {

            when (msg.what) {
                FROM_CLIENT -> {
                    msg.data.classLoader=javaClass.classLoader
                    log("ipc_收到client消息-->${msg.data}")
                    log("ipc_${msg.data.getString("c1")}")
                    log("ipc_${msg.data.getParcelable<Book>("c2")}")

                    val messenger = msg.replyTo
                    val message = Message()
                    val bundle = Bundle()
                    bundle.putString("b1", "我已收到 over  over")
                    message.data = bundle
                    try {
                        messenger.send(message)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }

                }
                else -> {
                    super.handleMessage(msg)
                }

            }
        }
    }



    override fun onBind(intent: Intent): IBinder {
        return mMessenger.binder
    }


    companion object {
        const val TAG = "MessengerService"

        const val FROM_CLIENT = 1
    }

}
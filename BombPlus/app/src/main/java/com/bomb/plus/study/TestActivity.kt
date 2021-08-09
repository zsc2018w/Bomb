package com.bomb.plus.study

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.lifecycle.Observer
import com.bomb.bus.v1.LiveEventBusV1
import com.bomb.common.net.log

import com.bomb.plus.R
import com.bomb.plus.aidl.*
import com.bomb.plus.study.ipc.TUser
import com.bomb.plus.test.Ts

import kotlinx.android.synthetic.main.activity_test2.*
import java.io.*
import java.lang.Exception
import kotlin.concurrent.thread

class TestActivity : AppCompatActivity() {

    private var num1 = 0
    private var num2 = 0

    private var dNum = 0
    private var cNum = 0
    private var path = "/data/user/0/com.bomb.plus/files/cache.txt"

    val array: IntArray = intArrayOf(1, 2, 3, 4, 5)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)


        txxx.setOnClickListener {

        }

        LiveEventBusV1.getDefault("event1", String::class.java).observe(this, Observer {

            log("event_--->$it----${dNum++}")
        })
        LiveEventBusV1.getDefault("event2", String::class.java).observe(this, Observer {
            log("event_--->$it----${cNum++}")
        })

        val a1 = Ts.A1
        log("enum--->$a1")

        val a2 = Ts.A2
        log("enum--->$a2")

        bt1.setOnClickListener {
            val a1 = Ts.A1

            log("enum--->$a1")
            val a2 = Ts.A2

            log("enum--->$a2")
            //toOos()
            //   aidl?.addBook(Book("哈哈哈","666"))

        }
        bt2.setOnClickListener {
            //toOis()


            //val list=aidl?.bookList
            // log("ipc_----${list}")
        }


/*        val intent = Intent(this, MessengerService::class.java)

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)*/

        val intent = Intent(this, BookService::class.java)
        bindService(intent, aidlConnection, Context.BIND_AUTO_CREATE)


        //MultiProcessManager.onPost("测试消息")

        val uid = Process.myUid()
        val uid2 = packageManager.getPackagesForUid(uid)

        log("ipc_uid=$uid")
        log("ipc_uid2=${uid2?.get(0)}")

    }


    private val clientMessenger = Messenger(ClientHandler())

    private class ClientHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                0 -> {
                    log("ipc_收到server消息")
                    log("ipc_${msg.data.getString("b1")}")
                }
            }
        }
    }


    private var mMessenger: Messenger? = null
    private val serviceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            log("ipc_链接开始")
            mMessenger = Messenger(service)
            val message = Message.obtain(null, 1)
            val bundle = Bundle()
            bundle.putString("c1", "测试内容")
            bundle.putParcelable("c2", Book("张三", "888"))
            message.data = bundle
            message.replyTo = clientMessenger
            try {
                mMessenger?.send(message)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        override fun onServiceDisconnected(name: ComponentName?) {

        }


    }


    var aidl: IBookManager? = null

    private val aidlConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            aidl = IBookManager.Stub.asInterface(service)

            val list = aidl?.bookList
            log("ipc_onServiceConnected")
            for (index in 0 until 10) {
                thread {
                    aidl?.checkStatus(index)
                }
            }

            log("ipc_----${list}")
        }

    }

    private fun toOos() {
        val file = File(filesDir, "cache.txt")
        // if (!file.exists()) {
        file.createNewFile()
        //}
        log("${file.absolutePath}")
        path = file.absolutePath
        val tUser = TUser("张三", "sex")
        log(tUser.toString())
        val output = ObjectOutputStream(FileOutputStream(path))
        output.writeObject(tUser)
        output.close()
    }

    private fun toOis() {
        val input = ObjectInputStream(FileInputStream(path))
        val user = input.readObject() as TUser
        input.close()
        log(user.toString())

    }


    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)

    }

}

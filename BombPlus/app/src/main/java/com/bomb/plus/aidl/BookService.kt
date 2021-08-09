package com.bomb.plus.aidl

import android.app.Service
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.bomb.common.net.log
import java.util.concurrent.CopyOnWriteArrayList

class BookService : Service() {


    val mBookList = CopyOnWriteArrayList<Book>()

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        mBookList.add(Book("西游记", "888"))
        mBookList.add(Book("红楼梦", "999"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        log("检查状态---  BookService  onStartCommand--")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    private val mBinder = object : IBookManager.Stub() {


        override fun checkStatus(num: Int) {
            log("检查状态---start-->$num")
            Thread.sleep(200)
            log("检查状态---end-->$num")
        }

        override fun addBook(book: Book?) {
            mBookList.add(book)
        }


        override fun getBookList(): MutableList<Book> {
            return mBookList
        }

    }


}
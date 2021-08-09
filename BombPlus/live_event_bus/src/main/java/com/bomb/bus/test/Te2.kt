package com.bomb.bus.test

import android.util.Log
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread


class Te2 constructor(name: String, age: String) {


    constructor(name: String) : this() {

    }

    constructor() : this("222", "") {
        Log.d("bomb", "请把罗奎插入到前面----2222222")
    }

    init {
        Log.d("bomb", "请把罗奎插入到前面----1111111")
    }


    fun join() {

        Log.d("thread__", "开始")
        val t0 = thread(false) {
            Log.d("thread__", "thread0----run")

        }

        val t1 = thread {
            t0.join()
            Log.d("thread__", "thread1----run")

        }

        val t2 = thread {
            t1.join()
            Log.d("thread__", "thread2----run")
        }
        Log.d("thread__", "t0开始")
        t0.start()
    }

    val lock = Object()

    val lock2 = Object()

    var t0 = false
    var t1 = false

    fun waitNotify() {

        val co = false
        val t0 = thread(false) {
            // synchronized(lock) {


            synchronized(lock2) {
                Log.d("thread__", "开发人员开始开发")

                lock2.notify()
            }


        }
        val t1 = thread(false) {
            synchronized(lock) {

                Log.d("thread__", "产品经理规划新需求")

                //lock.notify()
            }
        }
        val t2 = thread(false) {
            synchronized(lock2) {

                Log.d("thread__", "测试人员休息会")

                lock2.wait()

                Log.d("thread__", "测试人员测试新功能")
            }
        }
        Log.d("thread__", "早上好")
        Log.d("thread__", "测试人员开始上班")
        t2.start()
        Log.d("thread__", "产品经理开始上班")
        t1.start()
        Log.d("thread__", "开发人员开始上班")
        t0.start()

    }


    fun executors() {
        val exe = Executors.newSingleThreadExecutor()

        val t0 = thread(false) {
            Log.d("thread__", "产品规划新功能")
        }
        val t1 = thread(false) {
            Log.d("thread__", "开发新功能")
        }
        val t2 = thread(false) {
            Log.d("thread__", "测试新功能")
        }

        Log.d("thread__", "早上好")
        Log.d("thread__", "测试人员开始上班")
        exe.submit(t0)
        Log.d("thread__", "产品经理开始上班")
        exe.submit(t1)
        Log.d("thread__", "开发人员开始上班")
        exe.submit(t2)
    }


    companion object{

        var r1 = false

        @JvmStatic
        var r2 = false
    }



    fun condition() {
        val lock = ReentrantLock()
        val c1 = lock.newCondition()
        val c2 = lock.newCondition()


        val t0 = thread(false) {
            lock.lock()
            Log.d("thread__", "产品经理规划新需求")
            r1=true
            c1.signal()

            lock.unlock()
        }

        val t1 = thread(false) {
            lock.lock()
            if (!r1) {
                Log.d("thread__", "开发人员先休息会等产品规划")
                c1.await()
            }
            Log.d("thread__", "开发人员开发")
            c2.signal()
            lock.unlock()
        }
        val t2 = thread(false) {
            lock.lock()
            if(!r2){
                Log.d("thread__", "测试人员等待开发完成")
                c2.await()
            }
            Log.d("thread__", "测试人员测试新功能")
            lock.unlock()
        }



        Log.d("thread__", "早上好")
        Log.d("thread__", "测试人员开始上班")
        t1.start()
        Log.d("thread__", "产品经理开始上班")
        t0.start()
        Log.d("thread__", "开发人员开始上班")
        t2.start()
    }
}
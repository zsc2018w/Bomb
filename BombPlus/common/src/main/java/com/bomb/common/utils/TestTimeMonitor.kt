package com.bomb.common.utils

import com.bomb.common.net.log
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

/**
 * 执行任务打点
 * 耗时统计
 */
class TestTimeMonitor private constructor() {

    companion object {
        private val ttm = TestTimeMonitor()
        @JvmStatic
        fun get(): TestTimeMonitor {
            return ttm
        }
    }

    private val monitorMap = HashMap<String, Long>()
    private val timeMap = LinkedHashMap<String, Long>()


    fun start(tag: String) {
        monitorMap[tag] = getSystemTime()
    }

    fun end(tag: String) {
        monitorMap[tag]?.let {
            val time = getSystemTime() - it
            timeMap[tag] = time
            log("耗时---》end---》$tag---->$time")
        }
    }

    private fun getSystemTime(): Long {
        return System.currentTimeMillis()
    }
/*
    //此处可进行本地写入 保存耗时日志
    fun finish(){
        for ((k,v) in timeMap){
           // Log.d("耗时---》","$k--->$v")
        }
    }*/

}
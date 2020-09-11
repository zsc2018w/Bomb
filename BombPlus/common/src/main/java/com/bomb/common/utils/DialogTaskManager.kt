package com.bomb.common.utils

import android.app.Dialog


import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.bomb.common.net.log
import kotlin.Comparator


/**
 * 弹窗管理
 * 页面单例
 */
class DialogTaskManager private constructor(val activity: FragmentActivity) : LifecycleObserver {
    init {
        activity.lifecycle.addObserver(this)
    }

    private var index = 0
    private var currentDialog: Dialog? = null
    private val taskInfoList = arrayListOf<TaskInfo>()
    private var isResume: Boolean = false
    private var isEnable: Boolean = true
    private var eventList = arrayListOf<String>()

    companion object {
        private val instanceMap = HashMap<FragmentActivity, DialogTaskManager>()
        @JvmStatic
        fun getInstance(activity: FragmentActivity): DialogTaskManager {
            return instanceMap[activity] ?: DialogTaskManager(activity).apply {
                log("${activity.javaClass.simpleName}--->newInstance")
                instanceMap[activity] = this
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun onResume() {
        log("${activity.javaClass.simpleName}--->onResume")
        isResume = true
        if (currentDialog == null) {
            startShowDialog()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private fun onPause() {
        isResume = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        taskInfoList.clear()
        instanceMap.remove(activity)
        activity.lifecycle.removeObserver(this)
    }

    fun addDialog(
        dialog: Dialog,
        priority: Int = 0,
        iCheckTaskStatus: ICheckTaskStatus? = null
    ): DialogTaskManager {
        val taskInfo =
            TaskInfo(index++, priority, dialog = dialog, iCheckTaskStatus = iCheckTaskStatus)
        taskInfoList.add(taskInfo)
        sort()
        log("$taskInfoList")
        if (currentDialog == null) {
            startShowDialog()
        }
        return this
    }

    /**
     * 默认添加dialog 无需调用此方法
     * 调用时机---》主动检查 是否可以立即展示
     */
    fun check() {
        startShowDialog()
    }

    /**
     * 当前页面状态是否可用
     */
    private fun getEnable(): Boolean {
        log("${activity.javaClass.simpleName}--->$isEnable-<->--$isResume")
        return isEnable && isResume
    }

    /**
     * 设置当前的页面可用状态
     */
    fun setEnable(enable: Boolean) {
        this.isEnable = enable
    }


    fun startEvent(event: String) {
        isEnable = false
        if (!eventList.contains(event)) {
            eventList.add(event)
            log("${activity.javaClass.simpleName}--->event--start-->--$event")
        }
    }

    fun endEvent(event: String) {
        eventList.remove(event)
        log("${activity.javaClass.simpleName}--->event--end-->--$event--${eventList.size}")
        if (eventList.isEmpty()) {
            isEnable = true
            check()
        }
    }

    private fun startShowDialog() {
        val currentDialogStatus = currentDialog == null || !currentDialog!!.isShowing
        if (!taskInfoList.isNullOrEmpty() && getEnable() && currentDialogStatus) {
            log("${activity.javaClass.simpleName}--->startShowDialog")
            val taskInfo = taskInfoList[0]
            //增加任务状态检查校验callback
            if (taskInfo.iCheckTaskStatus != null && taskInfo.iCheckTaskStatus.taskCompleteStatus()) {
                taskInfoList.removeAt(0)
                currentDialog = null
                startShowDialog()
                return
            }
            currentDialog = taskInfo.dialog
            currentDialog?.show()
            currentDialog?.setOnDismissListener {
                currentDialog = null
                startShowDialog()
            }
            taskInfoList.removeAt(0)
        }
    }


    private fun sort() {
        taskInfoList.sortWith(Comparator { o1, o2 ->
            val v1 = o1?.getTaskPriority() ?: 0
            val v2 = o2?.getTaskPriority() ?: 0
            return@Comparator when {
                v1 > v2 -> -1
                v1 == v2 -> 0
                else -> 1
            }
        })
    }

  /*  private fun log(str: String) {
        // MyLogUtil.e("DialogTaskManager--->$str")
    }*/

    /**
     * @param index 索引
     * @param priority 优先级  值越大优先级越大
     * @param dialog   dialog
     * @param iCheckTaskStatus 弹窗时检验任务状态callback
     */
    class TaskInfo(
        var index: Int,
        var priority: Int = 0,
        var dialog: Dialog,
        val iCheckTaskStatus: ICheckTaskStatus? = null
    ) {

        fun getTaskPriority(): Int {
            return index + priority
        }

        override fun toString(): String {
            return "TaskInfo(index=$index, priority=$priority, dialog=$dialog)"
        }
    }


    /**
     * 如果需要在展示的时候校验任务完成状态
     * @return true  已完成不需要弹出
     * @return false
     */
    interface ICheckTaskStatus {
        fun taskCompleteStatus(): Boolean
    }


}
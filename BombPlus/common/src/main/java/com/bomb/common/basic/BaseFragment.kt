package com.bomb.common.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zyao89.view.zloading.ZLoadingDialog


/**
 * 基础 fragment
 *.initVm 在 onCreate 里初始化 vm 只调用一次 (vm 调用相关的地方尽量保证 只有一次)
 */
abstract class BaseFragment : Fragment() {

    protected var pageLoad: Boolean = false

    protected lateinit var fView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVm()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (getLayoutId() > 0) {
            if (fView == null) {
                fView = inflater.inflate(getLayoutId(), container, false)
                initView()
            }
            val parent = fView?.parent as ViewGroup?
            parent?.removeView(view)
            return fView

        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    open fun getLayoutId(): Int {
        return 0
    }

    /**
     * 在合适的地方初始化vm
     */
    protected open fun initVm() {

    }

    abstract fun initView()


    fun showLoading() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).showLoading()
        }
    }

    fun closeLoading() {
        if (activity is BaseActivity) {
            (activity as BaseActivity).closeLoading()
        }
    }

}
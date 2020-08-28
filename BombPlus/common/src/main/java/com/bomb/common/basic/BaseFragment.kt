package com.bomb.common.basic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zyao89.view.zloading.ZLoadingDialog


/**
 * 基础 fragment
 */
abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (getLayoutId() > 0) {
            return inflater.inflate(getLayoutId(), container, false)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    open fun getLayoutId(): Int {
        return 0
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
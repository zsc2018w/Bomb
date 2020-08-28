package com.bomb.common.basic

import android.os.Bundle


/**
 * 懒加载 fragment
 * 不兼容 viewpager2
 */
abstract class BaseLazyFragment : BaseFragment() {

    /**
     * 该页面是否准备完毕（onCreateView方法调用完毕）
     */
    private var isPrepared = false
    /**
     * 该Fragment 是否加载过数据
     */
    private var isLazyLoaded = false


    override fun initView() {
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isPrepared = true
        lazyLoad()
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        lazyLoad()
    }

    private fun lazyLoad() {
        if (userVisibleHint && isPrepared && !isLazyLoaded) {
            onLazyLoad()
            isLazyLoaded = true
        }
    }

    abstract fun onLazyLoad()

}
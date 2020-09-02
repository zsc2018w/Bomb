package com.bomb.plus.main

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bomb.common.basic.BaseLazyFragment
import com.bomb.common.core.VMCommon
import com.bomb.plus.R
import com.bomb.plus.eye.adapter.HomeAdapter
import com.bomb.plus.eye.viewmodel.HomeViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_main_home.*

/**
 * HomeFragment
 */
class HomeFragment : BaseLazyFragment() {

    private var homeAdapter: HomeAdapter? = null
    private var pageLoad: Boolean = false
    private val homeViewModel by lazy {
        VMCommon.getVM(this, HomeViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_home
    }

    override fun initView() {
        super.initView()
        homeAdapter=HomeAdapter(requireActivity())
        rv_list.adapter=homeAdapter
        val layoutManager=LinearLayoutManager(activity)
        rv_list.layoutManager=layoutManager
        srl_refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                homeViewModel?.getMoreData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                homeViewModel?.getHomeData()
            }
        })
    }

    override fun onLazyLoad() {
        msv_status_view.showLoading()
        //showLoading()
        msv_status_view.setOnRetryClickListener(View.OnClickListener {
            pageLoad=false
            msv_status_view.showLoading()
            homeViewModel?.getHomeData()
        })
        homeViewModel?.errorLiveData?.observe(this, Observer {
            msv_status_view.showError()
        })
        homeViewModel?.getHomeData()
        homeViewModel?.homeFirstData?.observe(this, Observer {
            if(!pageLoad){
                pageLoad=true
               // closeLoading()
                msv_status_view.showContent()
            }
            finishLoadMore()
            homeAdapter?.bannerItemSize=it.issueList[0].count
            homeAdapter?.setData(it.issueList[0].itemList)
        })
        homeViewModel?.homeMoreData?.observe(this, Observer {
            finishLoadMore()
            homeAdapter?.loadMoreData(it.issueList[0].itemList)
        })

    }

    private fun finishLoadMore() {
        if (srl_refresh.isRefreshing) {
            srl_refresh.finishRefresh()
        }
        if (srl_refresh.isLoading) {
            srl_refresh.finishLoadMore()
        }
    }
}
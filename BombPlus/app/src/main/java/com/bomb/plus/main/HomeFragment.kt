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
import kotlinx.android.synthetic.main.fragment_main_home.view.*

/**
 * HomeFragment
 */
class HomeFragment : BaseLazyFragment() {

    private var homeAdapter: HomeAdapter? = null

    private val homeViewModel by lazy {
        VMCommon.getVM(this, HomeViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_home
    }

    override fun initVm() {
        super.initVm()
        homeViewModel?.errorLiveData?.observe(this, Observer {
            msv_status_view.showError()
        })
        homeViewModel?.homeFirstData?.observe(this, Observer {
            if (!pageLoad) {
                pageLoad = true
                // closeLoading()
                fView.msv_status_view.showContent()
            }
            finishLoadMore()
            homeAdapter?.bannerItemSize = it.issueList[0].count
            homeAdapter?.setData(it.issueList[0].itemList)
        })
        homeViewModel?.homeMoreData?.observe(this, Observer {
            finishLoadMore()
            homeAdapter?.loadMoreData(it.issueList[0].itemList)
        })
    }

    override fun initView() {
        super.initView()
        homeAdapter = HomeAdapter(requireActivity())
        fView.rv_list.adapter = homeAdapter
        val layoutManager = LinearLayoutManager(activity)
        fView.rv_list.layoutManager = layoutManager
        fView.srl_refresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                homeViewModel?.getMoreData()
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                homeViewModel?.getHomeData()
            }
        })

        fView.msv_status_view.setOnRetryClickListener(View.OnClickListener {
            pageLoad = false
            fView.msv_status_view.showLoading()
            homeViewModel?.getHomeData()
        })


    }

    override fun onLazyLoad() {
        fView.msv_status_view.showLoading()
        homeViewModel?.getHomeData()
    }

    private fun finishLoadMore() {
        if (fView.srl_refresh.isRefreshing) {
            fView.srl_refresh.finishRefresh()
        }
        if (fView.srl_refresh.isLoading) {
            fView.srl_refresh.finishLoadMore()
        }
    }
}
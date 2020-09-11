package com.bomb.plus.gank.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bomb.common.basic.BaseLazyFragment
import com.bomb.common.core.VMCommon
import com.bomb.common.net.log
import com.bomb.plus.R
import com.bomb.plus.gank.adapter.PictureAdapter
import com.bomb.plus.gank.bean.GankDataBean
import com.bomb.plus.gank.viewmodel.GankViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_bank_pictures_fragment.*
import kotlinx.android.synthetic.main.fragment_bank_pictures_fragment.view.*


import java.util.ArrayList

class PicturesFragment : BaseLazyFragment() {

    private var vm: GankViewModel? = null
    private var pictureAdapter: PictureAdapter? = null

    companion object {

        fun getInstance(): PicturesFragment {
            val fragment = PicturesFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_bank_pictures_fragment
    }


    override fun initView() {
        super.initView()
        activity?.apply {
            log("initAdapter")
            pictureAdapter = PictureAdapter(this)
            val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
            fView.mRecyclerView.layoutManager = manager
            fView.mRecyclerView.adapter = pictureAdapter
            fView.smartRefresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    vm?.getGankGirlData(false)
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    vm?.getGankGirlData(true)
                }
            })
            if (!pageLoad) {
                fView.statusLayout.showLoading()
            }
        }


    }

    override fun initVm() {
        super.initVm()
        log("initVm")
        vm = VMCommon.getVM(this, GankViewModel::class.java)
        vm?.errorLiveData?.observe(this, Observer {

        })
        vm?.gankGirlliveData?.observe(this, Observer {
            log("girl live data callback")
            if (!pageLoad) {
                pageLoad = true
                fView.statusLayout.showContent()
            }
            if (it.refresh) {
                pictureAdapter?.setData(it.data as ArrayList<GankDataBean>)
            } else {
                pictureAdapter?.loadMoreData(it.data as ArrayList<GankDataBean>)
            }
            finishLoadMore()
        })
    }


    override fun onLazyLoad() {
        log("onLazyLoad")
        vm?.getGankGirlData(true)
    }


    private fun finishLoadMore() {
        if (fView.smartRefresh.isRefreshing) {
            fView.smartRefresh.finishRefresh()
        }
        if (fView.smartRefresh.isLoading) {
            fView.smartRefresh.finishLoadMore()
        }
    }


}
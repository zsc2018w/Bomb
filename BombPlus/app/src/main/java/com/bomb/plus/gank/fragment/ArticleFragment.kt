package com.bomb.plus.gank.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bomb.common.basic.BaseLazyFragment
import com.bomb.common.core.VMCommon
import com.bomb.plus.R
import com.bomb.plus.core.Constants
import com.bomb.plus.gank.adapter.ArticleAdapter
import com.bomb.plus.gank.bean.GankDataBean
import com.bomb.plus.gank.bean.GankTypeData
import com.bomb.plus.gank.viewmodel.GankViewModel
import com.bomb.plus.utils.NormalItemDecoration
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_bank_article_fragment.view.mRecyclerView
import kotlinx.android.synthetic.main.fragment_bank_article_fragment.view.smartRefresh
import kotlinx.android.synthetic.main.fragment_bank_article_fragment.view.statusLayout

import java.util.ArrayList

class ArticleFragment : BaseLazyFragment() {

    private var vm: GankViewModel? = null
    private var itemData: GankTypeData? = null
    private var articleAdapter: ArticleAdapter? = null

    companion object {

        fun getInstance(gankTypeData: GankTypeData): ArticleFragment {
            val fragment = ArticleFragment()
            val bundle = Bundle()
            bundle.putSerializable(Constants.DATA, gankTypeData)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_bank_article_fragment
    }

    override fun initView() {
        super.initView()
        arguments?.let {
            itemData = it.getSerializable(Constants.DATA) as GankTypeData?
        }
        activity?.let { activity ->
            articleAdapter = ArticleAdapter(activity)
            fView.mRecyclerView.layoutManager = LinearLayoutManager(activity)
            fView.mRecyclerView.adapter = articleAdapter
            val nid=NormalItemDecoration()
            nid.setmSpace(20)
            fView.mRecyclerView.addItemDecoration(nid)
        }

        fView.smartRefresh.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {

            override fun onLoadMore(refreshLayout: RefreshLayout) {
                itemData?.type?.let {
                    vm?.getGankArticleData(false, it)
                }

            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                itemData?.type?.let {
                    vm?.getGankArticleData(true, it)
                }
            }
        })


    }

    override fun initVm() {
        vm = VMCommon.getVM(this, GankViewModel::class.java)
        vm?.errorLiveData?.observe(this, Observer {

        })
        vm?.gankArticleliveData?.observe(this, Observer {
            if (!pageLoad) {
                pageLoad = true
                fView.statusLayout.showContent()
            }
            if (it.refresh) {
                articleAdapter?.setData(it.data as ArrayList<GankDataBean>)
            } else {
                articleAdapter?.loadMoreData(it.data as ArrayList<GankDataBean>)
            }
            finishLoadMore()
        })

    }

    override fun onLazyLoad() {
        if (!pageLoad) {
            fView.statusLayout.showLoading()
        }
        if (itemData != null) {
            vm?.getGankArticleData(true, itemData!!.type)
        } else {
            fView.statusLayout.showError()
        }

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
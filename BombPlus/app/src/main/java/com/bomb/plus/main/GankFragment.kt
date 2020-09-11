package com.bomb.plus.main

import android.graphics.Color
import androidx.lifecycle.Observer
import com.bomb.common.basic.BaseLazyFragment
import com.bomb.common.core.ProHelper
import com.bomb.common.core.VMCommon
import com.bomb.common.utils.StatusBarUtil
import com.bomb.common.widget.LoadingDialog
import com.bomb.plus.R
import com.bomb.plus.gank.adapter.GankPageAdapter
import com.bomb.plus.gank.bean.GankTypeData
import com.bomb.plus.gank.viewmodel.GankViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_main_gank.*
import kotlinx.android.synthetic.main.fragment_main_gank.view.*

class GankFragment : BaseLazyFragment() {

    private var vm: GankViewModel? = null
    private var pageAdapter: GankPageAdapter? = null
    private val pictureData =
        GankTypeData("", "", "", ProHelper.getString(R.string.picture_title), "")


    override fun getLayoutId(): Int {
        return R.layout.fragment_main_gank
    }

    override fun initView() {
        super.initView()
        StatusBarUtil.setPaddingSmart(activity, fView.toolbar)
        initAdapter()
    }

    override fun initVm() {
        vm = VMCommon.getVM(this, GankViewModel::class.java)
        vm?.errorLiveData?.observe(this, Observer {

        })
        vm?.gankTypeliveData?.observe(this, Observer {
            closeLoading()
            val data = it.data as ArrayList
            data.add(0, pictureData)
            pageAdapter?.setData(data)
        })
        showLoading()
    }

    private fun initAdapter() {
        pageAdapter = GankPageAdapter(childFragmentManager)
        fView.viewPager.adapter = pageAdapter
        fView.tabLayout.setupWithViewPager(fView.viewPager)
        fView.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout: AppBarLayout?, verticalOffset: Int ->
            fView.gankTitle.setTextColor(changeAlpha(Color.BLACK, 1-Math.abs(verticalOffset * 1.0f) / appBarLayout!!.totalScrollRange))
            fView.gankPoem.setTextColor(changeAlpha(Color.BLACK, 1-Math.abs(verticalOffset * 1.0f) / appBarLayout!!.totalScrollRange))

        })
    }

    override fun onLazyLoad() {
        requestPageData()
    }

    private fun requestPageData() {
        vm?.getGankTypeData()
    }

    private fun changeAlpha(color: Int, fraction: Float): Int {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        val alpha = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }
}
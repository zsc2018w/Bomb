package com.bomb.plus.gank.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.bomb.common.core.ProHelper
import com.bomb.common.net.log
import com.bomb.plus.R
import com.bomb.plus.gank.bean.GankTypeData
import com.bomb.plus.gank.fragment.ArticleFragment
import com.bomb.plus.gank.fragment.PicturesFragment

class GankPageAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_SET_USER_VISIBLE_HINT) {

    private var dataList = arrayListOf<GankTypeData>()

    override fun getItem(position: Int): Fragment {
        val itemData = dataList[position]
        log("getItem------$position----${itemData.type}")
        return if (itemData.title == ProHelper.getString(R.string.picture_title)) {
            PicturesFragment.getInstance()
        } else {
            ArticleFragment.getInstance(itemData)
        }
    }

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return dataList[position].title
    }

    fun setData(dataList: ArrayList<GankTypeData>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }


}
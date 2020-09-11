package com.bomb.plus.gank.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bomb.common.utils.ToastUtils
import com.bomb.plus.R
import com.bomb.plus.gank.bean.GankDataBean
import com.bomb.plus.gank.bean.GankTypeData
import com.bomb.plus.webview.WebDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_article_layout.view.*
import java.util.ArrayList

class ArticleAdapter(val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = arrayListOf<GankDataBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.item_article_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ArticleViewHolder) {
            val itemData = dataList[position]
            val requestOptions =
                RequestOptions().placeholder(R.mipmap.bg_float_permission_detect)
                    .error(R.mipmap.bg_float_permission_detect)
            val images = itemData.images
            val url = if (!images.isNullOrEmpty()) {
                images[0]
            } else {
                ""
            }
            Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(url)
                .into(holder.itemView.iv_image)
            holder.itemView.tv_title.text = itemData.title
            holder.itemView.tv_desc.text = itemData.desc

            holder.itemView.setOnClickListener {
                if (!itemData.url.isNullOrBlank()) {
                    WebDetailActivity.startActivity(mContext, itemData.url)
                }

            }
        }
    }

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setData(listData: ArrayList<GankDataBean>) {
        dataList.clear()
        dataList.addAll(listData)
        notifyDataSetChanged()
    }

    fun loadMoreData(listData: ArrayList<GankDataBean>) {
        val start = itemCount
        dataList.addAll(listData)
        notifyItemRangeInserted(start, listData.size)
    }
}
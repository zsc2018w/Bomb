package com.bomb.plus.eye.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bomb.plus.R
import com.bomb.plus.eye.bean.EyeBean
import com.bomb.plus.utils.ZZUtils.durationFormat
import com.bumptech.glide.Glide

import java.util.*



class VideoDetailAdapter(val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listData: ArrayList<EyeBean.Issue.Item> = ArrayList<EyeBean.Issue.Item>()

    companion object {
        private const val ITEM_TYPE_DETAIL = 1
        private const val ITEM_TYPE_TEXT_CARD = 2
        private const val ITEM_TYPE_SMALL_VIDEO = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ITEM_TYPE_DETAIL ->
                return ViewHolder(inflaterView(parent, R.layout.item_video_detail_info))
            ITEM_TYPE_TEXT_CARD ->
                return ViewHolder(inflaterView(parent, R.layout.item_video_text_card))
            ITEM_TYPE_SMALL_VIDEO ->
                return ViewHolder(inflaterView(parent, R.layout.item_video_small_card))
            else ->
                throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }

    override fun getItemCount(): Int {
        return listData?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mData = listData.get(position)
        when {
            position == 0
            -> setVideoDetailInfo(mData!!, holder as ViewHolder)
            mData.type == "textCard"
            -> holder.itemView.findViewById<TextView>(R.id.tv_text_card).text =
                mData.data?.text
            mData.type == "videoSmallCard"
            -> {
                holder.itemView.findViewById<TextView>(R.id.tv_title).text =
                    mData.data?.title
                holder.itemView.findViewById<TextView>(R.id.tv_tag).text =
                    "#${mData.data?.category!!} / ${durationFormat(mData.data?.duration)}"
                Glide.with(mContext).load(mData.data?.cover?.detail)
                    .into(holder.itemView.findViewById<ImageView>(R.id.iv_video_small_card))

            }

        }
    }

    private fun inflaterView(parent: ViewGroup, viewId: Int): View {
        return LayoutInflater.from(mContext).inflate(viewId, parent, false)
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 ->
                ITEM_TYPE_DETAIL
            listData[position].type == "textCard" ->
                ITEM_TYPE_TEXT_CARD
            listData[position].type == "videoSmallCard" ->
                ITEM_TYPE_SMALL_VIDEO
            else ->
                throw IllegalAccessException("Api 解析出错了，出现其他类型")
        }
    }


    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private fun setVideoDetailInfo(data: EyeBean.Issue.Item, holder: ViewHolder) {
        data.data?.title?.let { holder.itemView.findViewById<TextView>(R.id.tv_title).text = it }
        //视频简介
        data.data?.description?.let {
            holder.itemView.findViewById<TextView>(R.id.expandable_text).text = it
        }
        //标签
        holder.itemView.findViewById<TextView>(R.id.tv_tag).text =
            "#${data.data?.category} / ${durationFormat(data.data?.duration)}"

        if (data.data?.author != null) {
            with(holder) {
                itemView.findViewById<TextView>(R.id.tv_author_name).text = data.data.author.name
                itemView.findViewById<TextView>(R.id.tv_author_desc).text =
                    data.data.author.description
                Glide.with(mContext).load(data.data.author.icon)
                    .into(itemView.findViewById<ImageView>(R.id.iv_avatar))
            }
        } else {
            holder.itemView.findViewById<View>(R.id.layout_author_view).visibility = View.GONE
        }
    }



    fun setData(listData: ArrayList<EyeBean.Issue.Item>) {
        this.listData = listData
        notifyDataSetChanged()
    }

    fun loadMoreData(
        listData: ArrayList<EyeBean.Issue.Item>?, singleItem: EyeBean.Issue.Item? = null
    ) {
        val start = itemCount
        if (!listData.isNullOrEmpty()) {
            this.listData.addAll(listData)
            notifyItemRangeInserted(start, listData.size)
        } else if (singleItem != null) {
            this.listData.add(singleItem)
            notifyItemRangeInserted(start, 1)
        }


    }


}
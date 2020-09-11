package com.bomb.plus.gank.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bomb.common.utils.ToastUtils
import com.bomb.plus.R
import com.bomb.plus.eye.video.DisplayManager
import com.bomb.plus.gank.bean.GankDataBean
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_picture_content.view.*
import java.util.ArrayList

class PictureAdapter(val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: ArrayList<GankDataBean> = arrayListOf()
    private val heightList = arrayListOf(300, 280, 200)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PictureViewHolder(
            LayoutInflater.from(mContext).inflate(R.layout.item_picture_content, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemData = dataList[position]
        val params = holder.itemView.iv_photo.layoutParams
        var height=0
        if(position%2==0){
            height= DisplayManager.dip2px(300f)!!.toInt()
        }else{
            height= DisplayManager.dip2px(260f)!!.toInt()
        }
        params.height = height

      /*  if (itemData.height > 0) {
            params.height = itemData.height
        } else {
            val randomHeight = heightList.random()
            val realHeight = DisplayManager.dip2px(randomHeight.toFloat())!!.toInt()
            itemData.height = realHeight
            params.height = realHeight
        }*/

        holder.itemView.iv_photo.layoutParams = params
        val requestOptions =
            RequestOptions().placeholder(R.mipmap.bg_float_permission_detect).error(R.mipmap.bg_float_permission_detect)
        Glide.with(mContext).applyDefaultRequestOptions(requestOptions).load(itemData.url)
            .into(holder.itemView.iv_photo)

    }


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


    class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                ToastUtils.show("点击图片--> $adapterPosition")
            }
        }
    }
}
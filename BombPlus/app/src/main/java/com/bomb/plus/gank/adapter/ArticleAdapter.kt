package com.bomb.plus.gank.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bomb.common.net.log
import com.bomb.common.utils.ToastUtils
import com.bomb.plus.R
import com.bomb.plus.gank.bean.GankDataBean
import com.bomb.plus.gank.bean.GankTypeData
import com.bomb.plus.main.fragment.MyFragment
import com.bomb.plus.study.CacheImageTarget
import com.bomb.plus.study.LoadImageListener
import com.bomb.plus.webview.WebDetailActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.item_article_layout.view.*
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.MultiCallback
import java.io.File
import java.lang.ref.WeakReference
import java.util.*

class ArticleAdapter(val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList = arrayListOf<GankDataBean>()
    private val multiCallback = MultiCallback()
    val weakHashMapV = WeakHashMap<ImageView, String>()
    val weakHashMapS = WeakHashMap<ImageView, String>()


    private val requestListener = object : RequestListener<Bitmap> {


        init {

            log("load_image---->requestListener--->測試")
        }
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {
            log("load_image---->onLoadFailed--->$isFirstResource")
            return false
        }

        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {


         /*   if (model is String && model.isNotBlank()) {
                Glide.with(mContext).asFile().load(url).into(object : CacheImageTarget() {
                    override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                        super.onResourceReady(resource, transition)
                    }
                })
                multiCallback.addView(holder.itemView.iv_image)
            }*/

            log("load_image---->onResourceReady--->$model------>$dataSource-->$isFirstResource")
            return false
        }

    }

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
                    .error(R.mipmap.bg_float_permission_detect).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            val images = itemData.images
            val url = if (!images.isNullOrEmpty()) {
                images[0]
            } else {
                ""
            }
            holder.itemView.iv_image.mIndex = position
            holder.itemView.iv_image.mTag = url
            Glide.with(mContext).applyDefaultRequestOptions(requestOptions).asBitmap().load(url)
                .listener(LoadImageListener().setWeakImage(holder.itemView.iv_image))
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

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        log("viewHolder---->onViewAttachedToWindow--->${holder.adapterPosition}")
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        log("viewHolder---->onViewDetachedFromWindow--->${holder.adapterPosition}")
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        log("viewHolder---->onViewRecycled--->${holder.adapterPosition}")
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
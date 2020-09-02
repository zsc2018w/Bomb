package com.bomb.plus.eye.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bomb.plus.core.Constants
import com.bomb.plus.R
import com.bomb.plus.eye.bean.HomeBean
import com.bomb.plus.eye.video.VideoDetailsActivity
import com.bomb.plus.utils.ZZUtils.durationFormat
import com.bumptech.glide.Glide

class HomeAdapter(private val mContext: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var bannerItemSize = 0
    var mListData = arrayListOf<HomeBean.Issue.Item>()

    companion object {
        private const val ITEM_TYPE_BANNER = 1    //Banner 类型
        private const val ITEM_TYPE_TEXT_HEADER = 2   //textHeader
        private const val ITEM_TYPE_CONTENT = 3    //item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_TYPE_BANNER->
                BannerViewHolder(inflaterView(parent, R.layout.item_home_banner))
            ITEM_TYPE_TEXT_HEADER->
                TextHeaderViewHolder(inflaterView(parent,R.layout.item_home_header))
            else->
                ContentViewHolder(inflaterView(parent,R.layout.item_home_content))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 ->
                ITEM_TYPE_BANNER
            mListData[position + bannerItemSize - 1].type == "textHeader" ->
                ITEM_TYPE_TEXT_HEADER
            else ->
                ITEM_TYPE_CONTENT
        }
    }

    override fun getItemCount(): Int {
        return when {
            mListData.size > bannerItemSize -> mListData.size - bannerItemSize + 1
            mListData.isEmpty() -> 0
            else -> 1
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                val bannerItemData: ArrayList<HomeBean.Issue.Item> = mListData.take(bannerItemSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                //取出banner 显示的 img 和 Title
                bannerItemData.forEach { item ->
                    bannerFeedList.add(item.data?.cover?.feed ?: "")
                    bannerTitleList.add(item.data?.title ?: "")
                }
                with(viewHolder) {
                    itemView.findViewById<BGABanner>(R.id.banner)
                        .run {
                            setAutoPlayAble(bannerFeedList.size > 1)
                            setData(bannerFeedList, bannerTitleList)
                            setAdapter { banner, _, url, position ->
                                Glide.with(mContext).load(url).into(banner.getItemImageView(position))
                            }

                        }
                    itemView.findViewById<BGABanner>(R.id.banner)
                        .setDelegate { _, imageView, _, i ->
                            //goToVideoPlayer(mContext as Activity,itemView,bannerItemData.get(i))
                        }
                }
            }
            ITEM_TYPE_TEXT_HEADER -> {
                viewHolder.itemView.findViewById<TextView>(R.id.tvHeader).text = mListData[position + bannerItemSize - 1].data?.text ?: ""
            }
            ITEM_TYPE_CONTENT -> {
                setContentItem(viewHolder as ContentViewHolder,mListData[position + bannerItemSize - 1])

            }
        }
    }


    fun setContentItem(holder: ContentViewHolder, item: HomeBean.Issue.Item){

        val itemData = item.data

        //  val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"

        // 作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }

        Glide.with(mContext).load(cover).into(holder.itemView.findViewById(R.id.iv_cover_feed))

        Glide.with(mContext).load(avatar).into(holder.itemView.findViewById(R.id.iv_avatar))

        holder.itemView.findViewById<TextView>(R.id.tv_title).text= itemData?.title ?: ""

        //遍历标签
        itemData?.tags?.take(3)?.forEach {
            tagText += (it.name + "/")
        }
        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        tagText += timeFormat

        holder.itemView.findViewById<TextView>(R.id.tv_tag).text=tagText

        holder.itemView.findViewById<TextView>(R.id.tv_category).text= "#" + itemData?.category

        holder.itemView.setOnClickListener{
            goToVideoPlayer(mContext as Activity,holder.itemView.findViewById(R.id.iv_cover_feed),item)
        }
    }


    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailsActivity::class.java)
        intent.putExtra(Constants.VIDE0_DETAIL_DATA, itemData)
        intent.putExtra(Constants.TRANSITION, true)

        val pair =  androidx.core.util.Pair(view, Constants.IMG_TRANSITION)
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
            activity,
            pair
        )
        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())

    }

    private fun inflaterView(parent:ViewGroup,viewId:Int):View{
        return  LayoutInflater.from(mContext).inflate(viewId,parent,false)
    }


    fun setData(listData: ArrayList<HomeBean.Issue.Item>) {
        this.mListData = listData
        notifyDataSetChanged()
    }

    fun loadMoreData(listData: ArrayList<HomeBean.Issue.Item>) {
        val start=itemCount
        mListData.addAll(listData)
        notifyItemRangeInserted(start,listData.size)
    }


    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class TextHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
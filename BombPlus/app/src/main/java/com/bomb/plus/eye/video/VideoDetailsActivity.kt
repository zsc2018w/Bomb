package com.bomb.plus.eye.video


import android.content.res.Configuration
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bomb.common.basic.BaseActivity
import com.bomb.common.core.ProHelper
import com.bomb.common.core.VMCommon
import com.bomb.common.utils.NetUtils
import com.bomb.common.utils.StatusBarUtil
import com.bomb.common.utils.ToastUtils
import com.bomb.plus.core.Constants
import com.bomb.plus.core.Constants.IMG_TRANSITION
import com.bomb.plus.R
import com.bomb.plus.eye.adapter.VideoDetailAdapter


import com.bomb.plus.eye.bean.EyeBean
import com.bomb.plus.eye.viewmodel.VideoDetailsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_video_detail.*

import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder

import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer


class VideoDetailsActivity : BaseActivity() {

    private lateinit var itemData: EyeBean.Issue.Item
    private var orientationUtils: OrientationUtils? = null
    private var isTransition: Boolean = false
    private var transition: Transition? = null
    private var isPlay: Boolean = false
    private var isPause: Boolean = false
    private var mAdapter: VideoDetailAdapter? = null
    private var vm: VideoDetailsViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_video_detail
    }

    override fun onBackPressed() {
        orientationUtils?.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        if (isTransition) {
            super.onBackPressed()
        } else {
            finish()
        }
    }

    override fun initView() {
        StatusBarUtil.setStatusBarColor(this,ProHelper.getColor(R.color.color_black),false)
        getIntentData()
        initVm()
        initAdapter()
        initTransition()
        initVideoConfig()

    }

    private fun initVm() {
        vm = VMCommon.getVM(this, VideoDetailsViewModel::class.java)
        vm?.reletedLiveData?.observe(this, Observer {
            mAdapter?.loadMoreData(it.itemList)
        })
    }

    private fun getIntentData() {
        itemData = intent.getSerializableExtra(Constants.VIDE0_DETAIL_DATA) as EyeBean.Issue.Item
        isTransition = intent.getBooleanExtra(Constants.TRANSITION, false)
    }

    private fun initTransition() {
        if (isTransition) {
            postponeEnterTransition()
            ViewCompat.setTransitionName(mVideoView, IMG_TRANSITION)
            addTransitionListener()
            startPostponedEnterTransition()
        } else {
            loadVideoInfo()
        }
    }

    private fun initVideoConfig() {
        //设置旋转
        orientationUtils = OrientationUtils(this, mVideoView)
        orientationUtils?.isEnable = false
        val gsyVideoOption = GSYVideoOptionBuilder()
        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this).load(itemData.data?.cover?.feed).into(imageView)
        mVideoView.thumbImageView = imageView
        gsyVideoOption.setThumbImageView(imageView)
            .setIsTouchWiget(true)//是否可以滑动调整
            .setRotateViewAuto(false) //是否旋转
            .setLockLand(false)
            .setAutoFullWithSize(true)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setUrl("")
            .setCacheWithPlay(false)
            .setVideoTitle("")
            .setVideoAllCallBack(object : VideoCallBack() {
                override fun onPrepared(url: String?, vararg objects: Any?) {
                    //开始播放了才能旋转和全屏
                    orientationUtils?.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                    orientationUtils?.backToProtVideo()
                }

                override fun onPlayError(url: String?, vararg objects: Any?) {
                    ToastUtils.show("播放失败")
                }
            }).setLockClickListener { _, lock ->
                //配合下方的onConfigurationChanged
                orientationUtils?.isEnable = !lock
            }.build(mVideoView)
        mVideoView.fullscreenButton.setOnClickListener(View.OnClickListener {
            //直接横屏
            orientationUtils?.resolveByClick()
            mVideoView.startWindowFullscreen(
                this,
                false,
                true
            )
        })

        mVideoView.backButton.setOnClickListener {
            onBackPressed()
        }

        //锁屏事件
        mVideoView.setLockClickListener(object : LockClickListener {
            override fun onClick(view: View?, lock: Boolean) {
                //配合下方的onConfigurationChanged
                orientationUtils?.isEnable = !lock
            }
        })
    }

    private fun setVideo(url: String) {
        mVideoView.setUp(url, false, "")
        //开始自动播放
        mVideoView.startPlayLogic()
    }

     fun setVideoInfo(itemInfo: EyeBean.Issue.Item) {
        itemData = itemInfo
        mAdapter?.loadMoreData(null,itemInfo)
        // 请求相关的最新等视频
        vm?.getReletedVideoData(itemInfo.data?.id?:0)
    }

    private fun initAdapter() {
        mAdapter = VideoDetailAdapter(this)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setPageBackground(url: String) {
        val requestOptions = RequestOptions().centerCrop()
            .format(DecodeFormat.PREFER_ARGB_8888)
        Glide.with(this).load(url).apply(requestOptions).into(mVideoBackground)
    }


    private fun loadVideoInfo() {
       // val playInfo = itemData.data?.playInfo
       // val palyTypeSize = playInfo?.size ?: 0
      //  val isWifi = NetUtils.isWifiConnected(this)
     /*   if (palyTypeSize > 1) {
            if (isWifi) {
                playInfo?.filter {
                    it.type == "high"
                }?.let {
                    if (it.isNotEmpty()) {
                        setVideo(it[0].url)
                    }
                }
            } else {
                playInfo?.filter {
                    it.type == "normal"
                }?.let {
                    if (it.isNotEmpty()) {
                        setVideo(it[0].url)
                    }
                }
            }
        } else {*/
            val playUrl = itemData.data?.playUrl
            if (!playUrl.isNullOrBlank()) {
                setVideo(playUrl)
            } else {
                ToastUtils.show("播放失败")
            }
       // }
        val backgroundUrl =
            itemData.data?.cover?.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(
                250f
            )!!}x${DisplayManager.getScreenWidth()}"
        setPageBackground(backgroundUrl)
        setVideoInfo(itemData)
    }

    private fun addTransitionListener() {
        transition = window.sharedElementEnterTransition
        transition?.addListener(object : TransitionListener() {
            override fun onTransitionEnd(transition: Transition?) {
                super.onTransitionEnd(transition)
                loadVideoInfo()
                transition?.removeListener(this)
            }
        })
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            mVideoView.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }

    private fun getCurPlay(): GSYBaseVideoPlayer {
        return  mVideoView.currentPlayer
    }

    override fun onResume() {
        getCurPlay().onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onPause() {
        getCurPlay().onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            mVideoView.currentPlayer.release()
        }
        orientationUtils?.releaseListener()
    }

}

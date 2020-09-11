package com.bomb.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.bomb.common.R
import com.bomb.common.net.log
import java.util.ArrayList
import kotlin.properties.Delegates

class MultipleStatusView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    companion object {
        const val STATUS_CONTENT = 0x00
        const val STATUS_LOADING = 0x01
        const val STATUS_EMPTY = 0x02
        const val STATUS_ERROR = 0x03
        const val STATUS_NO_NETWORK = 0x04

        private val DEFAULT_LAYOUT_PARAMS = LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT
        )
    }


    private val mOtherIds = ArrayList<Int>()
    private val NULL_RESOURCE_ID = -1

    private var mEmptyView: View? = null
    private var mErrorView: View? = null
    private var mLoadingView: View? = null
    private var mNoNetworkView: View? = null
    private var mContentView: View? = null

    var mEmptyViewResId by Delegates.notNull<Int>()
    var mErrorViewResId by Delegates.notNull<Int>()
    var mLoadingViewResId by Delegates.notNull<Int>()
    var mNoNetworkViewResId by Delegates.notNull<Int>()
    var mContentViewResId by Delegates.notNull<Int>()
    var mInflater: LayoutInflater? = null
    private var mViewStatus: Int = 0

    private var mOnRetryClickListener: OnClickListener? = null

    init {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int = 0) {
        val a =
            context.obtainStyledAttributes(attrs, R.styleable.MultipleStatusView, defStyleAttr, 0)
        mEmptyViewResId =
            a.getResourceId(R.styleable.MultipleStatusView_emptyView, R.layout.empty_view)
        mErrorViewResId =
            a.getResourceId(R.styleable.MultipleStatusView_errorView, R.layout.error_view)
        mLoadingViewResId =
            a.getResourceId(R.styleable.MultipleStatusView_loadingView, R.layout.loading_view)
        mNoNetworkViewResId =
            a.getResourceId(R.styleable.MultipleStatusView_noNetworkView, R.layout.no_network_view)
        mContentViewResId =
            a.getResourceId(R.styleable.MultipleStatusView_contentView, NULL_RESOURCE_ID)
        a.recycle()
        mInflater = LayoutInflater.from(context)
        log("Status-->init--->$mLoadingViewResId--->$mInflater")
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        showContent()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        clear(mEmptyView, mLoadingView, mErrorView, mNoNetworkView)
        mOtherIds.clear()
        if (null != mOnRetryClickListener) {
            mOnRetryClickListener = null
        }
     //   mInflater = null
        log("Status-->onDetachedFromWindow--->$mInflater")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        log("Status-->onAttachedToWindow--->$mInflater")
        if (mInflater == null) {
            mInflater = LayoutInflater.from(context)
            log("Status-->onAttachedToWindow-- in->$mInflater")
        }
    }


    fun setOnRetryClickListener(onRetryClickListener: OnClickListener) {
        this.mOnRetryClickListener = onRetryClickListener
    }

    fun showContent() {
        mViewStatus = STATUS_CONTENT
        if (null == mContentView && mContentViewResId != NULL_RESOURCE_ID) {
            mContentView = mInflater?.inflate(mContentViewResId, null)
            addView(mContentView, 0, DEFAULT_LAYOUT_PARAMS)
        }
        showContentView()
    }


    fun showLoading() {
        showLoading(mLoadingViewResId, DEFAULT_LAYOUT_PARAMS)

    }

    fun showLoading(layoutId: Int, layoutParams: ViewGroup.LayoutParams) {
        showLoading(inflateView(layoutId), layoutParams)
        log("Status-->showloading2--->$layoutId---$mInflater")
    }

    fun showLoading(view: View?, layoutParams: ViewGroup.LayoutParams) {
        log("Status-->showloading3--->$view---$mInflater")
        checkNull(view, "Loading view is null!")
        mViewStatus = STATUS_LOADING
        if (null == mLoadingView) {
            mLoadingView = view
            mOtherIds.add(mLoadingView?.id!!)
            addView(mLoadingView, 0, layoutParams)
        }
        showViewById(mLoadingView?.id!!)
    }


    fun showError() {
        showError(mErrorViewResId, DEFAULT_LAYOUT_PARAMS)
    }

    fun showError(layoutId: Int, layoutParams: ViewGroup.LayoutParams) {
        showError(inflateView(layoutId), layoutParams)
    }


    fun showError(view: View?, layoutParams: ViewGroup.LayoutParams) {
        checkNull(view, "Error view is null!")
        mViewStatus = STATUS_ERROR
        if (null == mErrorView) {
            mErrorView = view
            view?.setOnClickListener(mOnRetryClickListener)
            mOtherIds.add(mErrorView?.id!!)
            addView(mErrorView, 0, layoutParams)
        }
        showViewById(mErrorView?.id!!)
    }


    private fun showViewById(viewId: Int) {
        val childCount = childCount
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            view.visibility = if (view.id == viewId) View.VISIBLE else View.GONE
        }
    }

    private fun showContentView() {
        val childCount = childCount
        for (i in 0 until childCount) {
            val view = getChildAt(i)
            view.visibility = if (mOtherIds.contains(view.id)) View.GONE else View.VISIBLE
        }
    }

    private fun inflateView(layoutId: Int): View? {
        log("Status-->inflateView--->$layoutId---$mInflater")
        return mInflater?.inflate(layoutId, null)
    }

    private fun checkNull(`object`: Any?, hint: String) {
        if (null == `object`) {
            throw NullPointerException(hint)
        }
    }

    private fun clear(vararg views: View?) {
        if (null == views) {
            return
        }
        try {
            for (view in views) {
                if (null != view) {
                    removeView(view)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}

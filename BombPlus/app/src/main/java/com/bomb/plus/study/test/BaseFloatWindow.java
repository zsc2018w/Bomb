package com.bomb.plus.study.test;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;

import static android.content.Context.WINDOW_SERVICE;

public abstract class BaseFloatWindow {

    public static final String TAG = BaseFloatWindow.class.getSimpleName();
    static final int FULLSCREEN_TOUCHABLE = 1;
    static final int FULLSCREEN_NOT_TOUCHABLE = 2;
    static final int WRAP_CONTENT_TOUCHABLE = 3;
    static final int WRAP_CONTENT_NOT_TOUCHABLE = 4;

    Context mContext;
    View mView;
    WindowManager mWindowManager;
    WindowManager.LayoutParams mLayoutParams;
    int mGravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL;
    int mViewMode = WRAP_CONTENT_NOT_TOUCHABLE;
    protected int mAddX = 0;
    protected int mAddY = 0;

    Handler mHandler = new Handler(Looper.getMainLooper());


    public BaseFloatWindow(Context context) {
        mContext = context;
        create();
        init();
    }

    private void create() {
        mWindowManager = (WindowManager) mContext.getApplicationContext().getSystemService(WINDOW_SERVICE);
    }


    protected void show() {
        if (mView == null) {
            throw new IllegalStateException("BaseFloatWindow can not be null");
        }
        getLayoutParams(mViewMode);
        try {
            mLayoutParams.x = mAddX;
            mLayoutParams.y = mAddY;
            mWindowManager.addView(mView, mLayoutParams);
        } catch (Exception e) {
            e.printStackTrace();
            onAddFailed();
        }
    }


    public void remove() {
        if (mView != null && mWindowManager != null) {
            if (mView.isAttachedToWindow()) {
                mWindowManager.removeView(mView);
            }
        }
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

    protected void getLayoutParams(int mode) {
        switch (mode) {
            case FULLSCREEN_TOUCHABLE:
                mLayoutParams = FloatWindowParamManager.getFloatLayoutParam(mContext, true, true);
                break;

            case FULLSCREEN_NOT_TOUCHABLE:
                mLayoutParams = FloatWindowParamManager.getFloatLayoutParam(mContext, true, false);
                break;

            case WRAP_CONTENT_NOT_TOUCHABLE:
                mLayoutParams = FloatWindowParamManager.getFloatLayoutParam(mContext, false, false);
                break;

            case WRAP_CONTENT_TOUCHABLE:
                mLayoutParams = FloatWindowParamManager.getFloatLayoutParam(mContext, false, true);
                break;
        }

        mLayoutParams.gravity = mGravity;
    }

    protected View inflateView(@LayoutRes int layout) {
        return View.inflate(mContext, layout, null);
    }


    abstract void init();

    abstract void onAddFailed();
}

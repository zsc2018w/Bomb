package com.bomb.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;

import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.bomb.common.R;


public class StatusBarUtil {


    /**
     * 设置从状态栏开始布局
     */
    public static void setStatusBarNoSpace(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(uiOptions);
            setStatusBarColor(activity, Color.TRANSPARENT, false);

        }
    }

    /**
     * 状态栏占位
     */
    public static void setStatusBarConsumeSpace(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);
            setStatusBarColor(activity, color, false);
        }
    }

    /**
     * 设置状态栏颜色
     */
    public static void setStatusBarColor(Activity activity, int color, boolean lightBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);

            if (lightBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

    }

    /**
     * 全屏
     *
     * @param activity
     */
    public static void setStatusBarFull(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            activity.getWindow().setAttributes(lp);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
            setStatusBarColor(activity, Color.TRANSPARENT, false);
        }
    }

    public static void setCommonStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarUtil.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.white), true);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.translucent), false);
        }

    }

    public static void darkModel(Activity activity, boolean lightBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            int systemUiVisibility = decorView.getSystemUiVisibility();
            if (lightBar) {
                systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(systemUiVisibility);
        }
    }

    public static void setPaddingSmart(Context context, View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp != null && lp.height > 0) {
            lp.height += getStatusBarHeight(context);//增高
        }
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(context),
                view.getPaddingRight(), view.getPaddingBottom());
    }


    public static int getStatusBarHeight(Context context) {
        int result = 24;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        } else {
            result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    result, Resources.getSystem().getDisplayMetrics());
        }
        return result;
    }
}

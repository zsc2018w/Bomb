package com.bomb.plus.test;

import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.bomb.plus.R;


public class RecordFloatWindow extends BaseFloatWindow {
    public RecordFloatWindow(Context context) {
        super(context);
    }

    private int minute = 60;
    private int hour = minute * 60;
    private int day = hour * 24;
    private TextView tv;
    private View.OnClickListener stopClickListener;

    @Override
    void init() {
        mViewMode = WRAP_CONTENT_TOUCHABLE;

        mGravity = Gravity.LEFT | Gravity.TOP;

        mAddX = 150;
        mAddY = 300;

        //inflate(R.layout.main_layout_follow_touch);
        mView = inflateView(R.layout.record_float_layout);
        tv = mView.findViewById(R.id.tv);
        mView.setOnTouchListener(new View.OnTouchListener() {
            private float mLastY;
            private float mLastX;
            private float mDownY;
            private float mDownX;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getRawX();
                float y = event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mDownX = x;
                        mDownY = y;
                        mLastX = x;
                        mLastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:

                        float moveX = x - mLastX;
                        float moveY = y - mLastY;

                        mLayoutParams.x += moveX;
                        mLayoutParams.y += moveY;

                        mWindowManager.updateViewLayout(mView, mLayoutParams);

                        mLastX = x;
                        mLastY = y;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }

                return false;
            }
        });
    }



    public void setStopClickListener(View.OnClickListener stopClickListener) {
        this.stopClickListener = stopClickListener;
        if(mView!=null){
            mView.findViewById(R.id.stop_lyaout).setOnClickListener(stopClickListener);
        }
    }

    public void updateTime(final int time) {
        mView.post(new Runnable() {
            @Override
            public void run() {
                tv.setText("时间"+getFormatTime(time));
            }
        });


    }

    String getFormatTime(int time) {
        String timeStr = "00:00";
        String hourStr = "00";
        String minuteStr = "00";
        String secondStr = "0";
       /* if (time > hour) {
            hourStr = Integer.toString(time / hour);
        }*/
        if (time >= minute) {
            int m = time / minute;
            minuteStr = Integer.toString(m);
            if (m < 10) {
                minuteStr = "0" + minuteStr;
            }
        }
        int s = time % 60;
        secondStr = Integer.toString(s);
        if (s < 10) {
            secondStr = "0" + secondStr;
        }
        return minuteStr + ":" + secondStr;
    }


    @Override
    void onAddFailed() {

    }
}

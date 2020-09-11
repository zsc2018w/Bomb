package com.bomb.plus.test;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bomb.common.core.ProHelper;

import org.greenrobot.eventbus.EventBus;

public class FloatWindowService extends Service {

    public static final String ADD_FLOAT_WINDOW = "add_float_window";
    public static final String REMOVE_FLOAT_WINDOW = "remove_float_window";
    public static final String KILL_SERVICE = "kill_service";
    private int time = 0;

    RecordFloatWindow recordFloatWindow;

    private Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String actionStr = intent.getAction();
        String action = TextUtils.isEmpty(actionStr) ? "" : actionStr;
        switch (action) {
            case ADD_FLOAT_WINDOW: {
                startTimer();
                addFloatWindow();
                break;
            }
            case REMOVE_FLOAT_WINDOW:
            case KILL_SERVICE: {
                endTimer();
                removeFloatWindow();
                stopSelf();
                break;
            }
            default:
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void startTimer() {
        time = 0;
        postDelayed();
    }

    private void endTimer() {
        handler.removeCallbacksAndMessages(null);
    }

    private void postDelayed() {
        handler.postDelayed(runnable, 1000);
    }


    private void addFloatWindow() {
        if (recordFloatWindow == null) {
            recordFloatWindow = new RecordFloatWindow(this);
            recordFloatWindow.setStopClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(ProHelper.mApp, CallbackActivity.class);
                    ProHelper.mApp.startActivity(intent);
                    EventBus.getDefault().post(new RecordEvent(RecordEvent.TYPE_RECORD_FINISH));
                }
            });
        } else {
            recordFloatWindow.remove();
        }
        recordFloatWindow.show();
    }

    private void removeFloatWindow() {
        if (recordFloatWindow != null) {
            recordFloatWindow.remove();
            recordFloatWindow = null;
        }
    }

    private void updateTime() {
        if (recordFloatWindow != null) {
            recordFloatWindow.updateTime(time);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time++;
            updateTime();
            postDelayed();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        endTimer();
        removeFloatWindow();
    }
}

package com.bomb.plus.main.fragment;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

public class Test {

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            return false;
        }
    }) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };
}

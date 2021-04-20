package com.bomb.plus.study.reflection.proxy;

import android.util.Log;

public class PersonA implements IWorkDispose {

    @Override
    public void toWork() {
        Log.d("proxy--->", "开始工作");
    }

    @Override
    public boolean dayForRest() {
        return true;
    }
}

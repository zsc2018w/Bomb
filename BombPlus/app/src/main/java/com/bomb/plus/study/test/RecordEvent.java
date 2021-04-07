package com.bomb.plus.study.test;

import android.content.Intent;

public class RecordEvent {
    public static final int TYPE_PREPARE = 1;
    public static final int TYPE_RECORD_FINISH = 2;

    public RecordEvent(int type) {
        this.type = type;
    }

    public RecordEvent(int type, int requestCode, int resultCode, Intent data) {
        this.type = type;
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    private int type;
    private int requestCode;
    private int resultCode;
    private Intent data;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent data) {
        this.data = data;
    }
}

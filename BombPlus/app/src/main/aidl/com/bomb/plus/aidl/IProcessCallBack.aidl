// IProcessCallBack.aidl
package com.bomb.plus.aidl;


interface IProcessCallBack {

    String processName();
    void onPost(in String msg);

}
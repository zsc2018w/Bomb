// IProcessCallBack.aidl
package com.bomb.plus.aidl;
import com.bomb.plus.aidl.IProcessCallBack;

interface IProcessManager {

    void post(in String msg);
    void register(IProcessCallBack callback);
    void unregister(IProcessCallBack callback);

}
package com.bomb.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;


public class NetUtils {

    public static final String NETWORK_NONE = "NETWORK_NONE"; // 没有网络连接
    public static final String NETWORK_WIFI = "NETWORK_WIFI"; // wifi连接
    public static final String NETWORK_2G = "NETWORK_2G"; // 2G
    public static final String NETWORK_3G = "NETWORK_3G"; // 3G
    public static final String NETWORK_4G = "NETWORK_4G"; // 4G
    public static final String NETWORK_MOBILE = "NETWORK_MOBILE"; // 手机流量


    public static final int SIGNAL_NONE = 0;
    public static final int SIGNAL_LEVEL_1 = 1;
    public static final int SIGNAL_LEVEL_2 = 2;
    public static final int SIGNAL_LEVEL_3 = 3;
    public static final int SIGNAL_LEVEL_4 = 4;


    public static int networkSignal = SIGNAL_NONE;

    /**
     * 是否网络已连接
     *
     * @param context context
     * @return true 有网  false 无网
     */
    public static boolean isNetworkConnect(Context context) {
        if (context != null) {
            // 获取网络服务
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connManager != null) {
                // 获取网络类型，如果为空，返回无网络
                NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
                return activeNetInfo != null && activeNetInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 获取当前网络类型
     *
     * @param context context
     * @return
     */
    public static String getNetworkState(Context context) {
        if (isNetworkConnect(context)) {
            // 获取网络服务
            ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 判断是否为WIFI
            NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiInfo != null) {
                NetworkInfo.State state = wifiInfo.getState();
                if (state != null) {
                    if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                        //获取wifi强度
                        networkSignal = checkWifiState(context);
                        return NETWORK_WIFI;
                    }
                }
            }
            // 若不是WIFI，则去判断是2G、3G、4G网
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            int networkType = telephonyManager.getNetworkType();
            //获取手机信号强度
            getPhoneState(context);
            switch (networkType) {
                // 2G网络
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN: {
                    return NETWORK_2G;
                }
                // 3G网络
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP: {
                    return NETWORK_3G;
                }
                // 4G网络
                case TelephonyManager.NETWORK_TYPE_LTE:
                case 19://4G+
                    return NETWORK_4G;
                default:
                    return NETWORK_MOBILE;
            }
        }

        return NETWORK_NONE;
    }

    /**
     * 判断是否wifi连接
     *
     * @param context context
     * @return true/false
     */
    public static synchronized boolean isWifiConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                int networkInfoType = networkInfo.getType();
                if (networkInfoType == ConnectivityManager.TYPE_WIFI || networkInfoType == ConnectivityManager.TYPE_ETHERNET) {
                    return networkInfo.isConnected();
                }
            }
        }
        return false;
    }


    public static int checkWifiState(Context mContext) {
        if (isWifiConnected(mContext)) {
            WifiManager mWifiManager = (WifiManager) mContext.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo mWifiInfo = mWifiManager.getConnectionInfo();
            int wifi = mWifiInfo.getRssi();//获取wifi信号强度
            if (wifi > -50 && wifi < 0) {//最强
//                Log.i("NetWorkUtil","CollectAppInfoManager--最强"+ wifi);
                return SIGNAL_LEVEL_4;
            } else if (wifi > -70 && wifi < -50) {//较强
//                Log.i("NetWorkUtil","CollectAppInfoManager--较强"+ wifi);
                return SIGNAL_LEVEL_3;
            } else if (wifi > -80 && wifi < -70) {//较弱
//                Log.i("NetWorkUtil","CollectAppInfoManager--较弱"+ wifi);
                return SIGNAL_LEVEL_2;
            } else if (wifi > -100 && wifi < -80) {//微弱
//                Log.i("NetWorkUtil","CollectAppInfoManager--微弱"+ wifi);
                return SIGNAL_LEVEL_1;
            } else {
//                Log.i("NetWorkUtil","CollectAppInfoManager--"+ wifi);
                return SIGNAL_NONE;
            }
        } else {
//            Log.i("NetWorkUtil","CollectAppInfoManager--无"+ "00000000000");
            return SIGNAL_NONE;
        }
    }


    public static void getPhoneState(Context context) {
        final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener MyPhoneListener = new PhoneStateListener() {
            @Override
            public void onSignalStrengthsChanged(SignalStrength signalStrength) {
                //23及其以上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int level = signalStrength.getLevel();
                    switch (level) {
                        case 4:
                            networkSignal = SIGNAL_LEVEL_4;
                            break;
                        case 3:
                            networkSignal = SIGNAL_LEVEL_3;
                            break;
                        case 2:
                            networkSignal = SIGNAL_LEVEL_2;
                            break;
                        case 1:
                            networkSignal = SIGNAL_LEVEL_1;
                            break;
                        case 0:
                            networkSignal = SIGNAL_NONE;
                            break;
                    }
                    // MyLogUtil.i("NetWorkUtil  CollectAppInfoManager----level---"+ level);
                    return;
                }

                //这个ltedbm 是4G信号的值
                String signalinfo = signalStrength.toString();
                String[] parts = signalinfo.split(" ");
                int ltedbm = Integer.parseInt(parts[9]);
                //这个dbm 是2G和3G信号的值
                int asu = signalStrength.getGsmSignalStrength();
                int dbm = -113 + 2 * asu;

                if (telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE || telephonyManager.getNetworkType() == 19) {
                    // MyLogUtil.i("NetWorkUtil CollectAppInfoManager网络：LTE 信号强度：" + ltedbm + "======Detail:" + signalinfo);
                    if (ltedbm > -85) {
                        networkSignal = SIGNAL_LEVEL_4;
                    } else if (ltedbm > -100) {
                        networkSignal = SIGNAL_LEVEL_3;
                    } else if (ltedbm > -115) {
                        networkSignal = SIGNAL_LEVEL_2;
                    } else {
                        networkSignal = SIGNAL_LEVEL_1;
                    }
                } else if (telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSDPA ||
                        telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSPA ||
                        telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_HSUPA ||
                        telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS) {
                    String bin;
                    if (dbm > -75) {
                        networkSignal = SIGNAL_LEVEL_4;
                        bin = "网络很好";
                        //  MyLogUtil.i("NetWorkUtil CollectAppInfoManager--"+ bin);
                    } else if (dbm > -85) {
                        networkSignal = SIGNAL_LEVEL_3;
                        bin = "网络不错";
                        //  MyLogUtil.i("NetWorkUtil CollectAppInfoManager--"+bin);
                    } else if (dbm > -95) {
                        networkSignal = SIGNAL_LEVEL_2;
                        bin = "网络还行";
                        // MyLogUtil.i("NetWorkUtil CollectAppInfoManager--"+ bin);
                    } else if (dbm > -100) {
                        networkSignal = SIGNAL_LEVEL_1;
                        bin = "网络很差";
                        //  MyLogUtil.i("NetWorkUtil CollectAppInfoManager--"+bin);
                    } else {
                        networkSignal = SIGNAL_NONE;
                        bin = "网络错误";
                        //MyLogUtil.i("NetWorkUtil CollectAppInfoManager--"+bin);
                    }
                    //MyLogUtil.i("NetWorkUtil CollectAppInfoManager--网络：WCDMA 信号值：" + dbm + "========强度：" + bin + "======Detail:" + signalinfo);
                } else {
                    String bin;
                    if (asu < 0 || asu >= 99) {
                        bin = "网络错误";
                        networkSignal = SIGNAL_NONE;
                    } else if (asu >= 16) {
                        bin = "网络很好";
                        networkSignal = SIGNAL_LEVEL_4;
                    } else if (asu >= 8) {
                        bin = "网络不错";
                        networkSignal = SIGNAL_LEVEL_3;
                    } else if (asu >= 4) {
                        bin = "网络还行";
                        networkSignal = SIGNAL_LEVEL_2;
                    } else {
                        bin = "网络很差";
                        networkSignal = SIGNAL_LEVEL_1;
                    }
                    //  MyLogUtil.i("NetWorkUtil CollectAppInfoManager--网络：GSM 信号值：" + dbm + "========强度：" + bin + "======Detail:" + signalinfo);
                }
                super.onSignalStrengthsChanged(signalStrength);
            }
        };
        telephonyManager.listen(MyPhoneListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        telephonyManager.listen(MyPhoneListener, PhoneStateListener.LISTEN_NONE);

    }
}

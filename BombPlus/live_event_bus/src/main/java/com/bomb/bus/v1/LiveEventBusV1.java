package com.bomb.bus.v1;

import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.Map;

public class LiveEventBusV1 {

    private final Map<String, MutableLiveData<Object>> bus;

    private LiveEventBusV1() {
        bus = new HashMap<>();
    }

    private static class InstanceHolder {
        static final LiveEventBusV1 INSTANCE = new LiveEventBusV1();
    }

    private static LiveEventBusV1 ready() {
        return InstanceHolder.INSTANCE;
    }


    public static <T> MutableLiveData<T> getDefault(String key, Class<T> clzz) {
        return ready().with(key, clzz);
    }

    private <T> MutableLiveData<T> with(String key, Class<T> clzz) {
        if (!bus.containsKey(key)) {
            MutableLiveData<Object> liveData = new MutableLiveData<>();
            bus.put(key, liveData);
        }
        return (MutableLiveData<T>) bus.get(key);
    }
}

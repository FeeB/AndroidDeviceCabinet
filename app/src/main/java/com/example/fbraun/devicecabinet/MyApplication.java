package com.example.fbraun.devicecabinet;

import android.app.Application;
import android.content.Context;

/**
 * Created by Fee on 03.03.15.
 */
public class MyApplication extends Application {

    static Context instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }

}

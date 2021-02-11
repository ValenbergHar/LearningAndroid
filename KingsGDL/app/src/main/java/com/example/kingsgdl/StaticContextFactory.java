package com.example.kingsgdl;

import android.app.Application;
import android.content.Context;

public class StaticContextFactory extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        StaticContextFactory.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return StaticContextFactory.context;
    }
}

package com.example.shop.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

public class HomeApplication extends Application {
    private static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext=getApplicationContext();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //Log.i("krot", "Init HomeApplication");
    }

    public static Context getAppContext() { return appContext; }
}

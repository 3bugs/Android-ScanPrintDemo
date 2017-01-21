package com.example.scanprintdemo;

import android.app.Application;

/**
 * Created by Promlert on 2017-01-21.
 */

public class App extends Application {

    private static App mInstance;
    public String data;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        data = "Hello";
    }

    public static App getInstance() {
        return mInstance;
    }
}

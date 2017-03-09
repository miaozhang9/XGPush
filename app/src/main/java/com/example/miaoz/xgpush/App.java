package com.example.miaoz.xgpush;

import android.app.Application;

/**
 * Created by miaoz on 2017/3/9.
 */
public class App extends Application {
    private static  App INSTANCE;
    public static App getInstance() { return INSTANCE; }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}

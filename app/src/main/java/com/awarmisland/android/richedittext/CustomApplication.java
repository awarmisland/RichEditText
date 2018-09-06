package com.awarmisland.android.richedittext;

import android.app.Application;

public class CustomApplication extends Application {
    private static CustomApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        this.application = this;
    }

    public static CustomApplication currentApplication(){
        return  application;
    }
}

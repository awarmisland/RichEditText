package com.awarmisland.android.richedittext;

import android.app.Application;
/**
 * Created by awarmisland on 2018/9/10.
 */
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

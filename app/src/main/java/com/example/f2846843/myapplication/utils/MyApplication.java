package com.example.f2846843.myapplication.utils;

import android.app.Application;
import android.content.Context;
/*
* 获取全局的context
* */
public class MyApplication extends Application {
    private static MyApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    //全局context
    public static Context getContext(){
        return context;
    }
}

package com.example.vishalpatel.pinlistdemo;

import android.app.Application;

import com.orhanobut.hawk.Hawk;

public class PinApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Hawk.init(this).build();
    }
}
package com.tools.taojike.androidtoolscollections.base;

import android.app.Application;

import com.tools.taojike.androidtoolscollections.utils.ShowTip;

/**
 * Created by taoji on 2016/7/16 0016.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ShowTip.getInstance().init(this);
    }
}

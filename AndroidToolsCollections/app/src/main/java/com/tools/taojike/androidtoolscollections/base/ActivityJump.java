package com.tools.taojike.androidtoolscollections.base;

import android.app.Activity;
import android.content.Intent;

import com.tools.taojike.androidtoolscollections.SecondActivity;

/**
 * Created by taoji on 2016/3/15 0015.
 */
public class ActivityJump {
    public static void startNetworkStateActivity(Activity ac) {
        Intent intent = new Intent();
        intent.setClass(ac, SecondActivity.class);
        ac.startActivity(intent);
    }
}

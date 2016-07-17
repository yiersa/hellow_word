package com.tools.taojike.androidtoolscollections.base;

import android.app.Activity;
import android.content.Intent;

import com.tools.taojike.androidtoolscollections.FourActivity;
import com.tools.taojike.androidtoolscollections.SecondActivity;
import com.tools.taojike.androidtoolscollections.ThirdActivity;

/**
 * Created by taoji on 2016/3/15 0015.
 */
public class ActivityJump {
    public static void startNetworkStateActivity(Activity ac) {
        Intent intent = new Intent();
        intent.setClass(ac, SecondActivity.class);
        ac.startActivity(intent);
    }
    public static void startThirdActivity(Activity ac) {
        Intent intent = new Intent();
        intent.setClass(ac, ThirdActivity.class);
        ac.startActivity(intent);
    }
    public static void startFourActivity(Activity ac) {
        Intent intent = new Intent();
        intent.setClass(ac, FourActivity.class);
        ac.startActivity(intent);
    }
}

package com.tools.taojike.androidtoolscollections.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by taoji on 2016/7/16 0016.
 */
public class ShowTip {
    private Context mContext;
    private static ShowTip mInstance;
    /**
     * application中调用
     * @param context
     */
    public void init(Context context) {
        mContext = context;
    }
    public ShowTip getInstance() {
        if (mInstance == null) {
            synchronized (ShowTip.class) {
                if (mInstance == null) {  //第二层校验
                    mInstance = new ShowTip();
                }
            }
        }
        return mInstance;
    }

    /**
     * 基础Toast
     * @param msg
     * @param duration 大于0即Toast.LENGTH_LONG
     */
    public void showToast(String msg, int duration) {
        if (TextUtils.isEmpty(msg))
            return;
        if (duration > 0) {
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
        }
    }


    


}

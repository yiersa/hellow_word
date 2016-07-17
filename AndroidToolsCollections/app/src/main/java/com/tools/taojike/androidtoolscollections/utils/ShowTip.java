package com.tools.taojike.androidtoolscollections.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
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
    public static ShowTip getInstance() {
        if (mInstance == null) {
            synchronized (ShowTip.class) {
                if (mInstance == null) {  //第二层校验
                    mInstance = new ShowTip();
                }
            }
        }
        return mInstance;
    }

    private static final int NORMAL_TOAST = 0;
    Handler showTipHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.arg1) {
                    case NORMAL_TOAST:
                        Toast.makeText(mContext, (String) msg.obj, msg.arg2).show();
                        break;
                }
            }
        }
    };

    public void showToast(String msg, int duration) {
        if (TextUtils.isEmpty(msg))
            return;
        int time = Toast.LENGTH_SHORT;
        if (duration > 0) {
            time = Toast.LENGTH_LONG;
        }
        Message message = new Message();
        message.arg1 = NORMAL_TOAST;
        message.arg2 = time;
        message.obj = msg;
        showTipHandler.sendMessage(message);
    }





}

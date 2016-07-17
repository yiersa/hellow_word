package com.tools.taojike.androidtoolscollections;

import android.os.Bundle;
import android.view.View;

import com.tools.taojike.androidtoolscollections.utils.ShowTip;

/**
 * Created by taoji on 2016/3/19 0019.
 */
public class FourActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        findViewById(R.id.thread_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ShowTip.getInstance().showToast("子线程toast", 10);
                    }
                });
                thread.start();
            }
        });
    }
}

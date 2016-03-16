package com.tools.taojike.androidtoolscollections;

import android.os.Bundle;
import android.widget.TextView;

import com.tools.taojike.androidtoolscollections.utils.NetWorkUtil;

/**
 * Created by taoji on 2016/3/14 0014.
 */
public class SecondActivity extends BaseActivity {
    private TextView net_state_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        net_state_text = (TextView) findViewById(R.id.net_state_text);
        if (NetWorkUtil.IsNetWorkEnable(this)) {
            net_state_text.setText("已连接网络\n");
        }
        if (NetWorkUtil.isWifiAvailable(this)) {
            net_state_text.append("已连接Wifi\n");
        }
        net_state_text.append(NetWorkUtil.getMacAddress(this) + "\n");
        net_state_text.append(NetWorkUtil.getProvider(this) + "\n");
        net_state_text.append(NetWorkUtil.getCurrentNetworkType(this) + "\n");
        net_state_text.append(NetWorkUtil.getWifiRssi(this) + "\n");
        net_state_text.append(NetWorkUtil.getWifiSsid(this) + "\n");
    }
}

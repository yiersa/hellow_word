package com.tools.taojike.androidtoolscollections;


import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tools.taojike.androidtoolscollections.base.ActivityJump;
import com.tools.taojike.androidtoolscollections.utils.DownLoadFile;

import java.util.ArrayList;


/**
 * 参考 http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1118/2004.html
 * http://blog.csdn.net/lmj623565791/article/details/45059587
 */
public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    private DownLoadFile downLoadFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new MyAdapter(initData());
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<String> initData() {
        ArrayList<String> listData = new ArrayList<>();
        listData.add("网络状态相关类");
        listData.add("下载文件");
        return listData;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        public ArrayList<String> datas = null;
        public MyAdapter(ArrayList<String> datas) {
            this.datas = datas;
        }
        //创建新View，被LayoutManager所调用
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }
        //将数据与界面进行绑定的操作
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            final String title = datas.get(position);
            viewHolder.mTextView.setText(title);
            viewHolder.item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(title);
                }
            });
        }
        //获取数据的数量
        @Override
        public int getItemCount() {
            Log.i("size",""+datas.size());
            return datas.size();
        }
        //自定义的ViewHolder，持有每个Item的的所有界面元素
        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public LinearLayout item_layout;
            public ViewHolder(View view){
                super(view);
                mTextView = (TextView) view.findViewById(R.id.text);
                item_layout = (LinearLayout) view.findViewById(R.id.item_layout);
            }
        }
        private void startActivity(String title) {
            if (title != null) {
                if (title.equals("网络状态相关类")) {
                    ActivityJump.startNetworkStateActivity(MainActivity.this);
                }
                else if (title.equals("下载文件")) {
                    downLoadFile();
                }
            }
        }


    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (downLoadFile == null)
                return;
            switch (msg.what) {
                case DownLoadFile.START_DOWNLOAD :
                    downLoadFile.setWaitDialogProcess(msg.arg1, msg.arg2);
                    break;
                case DownLoadFile.FINISH_DOWNLOAD :
                    downLoadFile.dissmissWaitDialog();
                    break;
                case DownLoadFile.FAIL_DOWNLOAD :
                    downLoadFile.dissmissWaitDialog();
                    break;
            }
        }
    };
    private void downLoadFile() {
        String path = Environment.getExternalStorageDirectory().toString() + "/file";
        String url = "http://dlsw.baidu.com/sw-search-sp/soft/3a/12350/QQ_8.1.17255.0_setup.1456298445.exe";
        downLoadFile = new DownLoadFile(this, url, path, "下载",handler);
        downLoadFile.startDownload();
    }
}

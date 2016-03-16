package com.tools.taojike.androidtoolscollections.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tools.taojike.androidtoolscollections.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件下载
 * Created by taoji on 2016/3/15 0015.
 */
public class DownLoadFile {
    public static final int START_DOWNLOAD = 1;
    public static final int FINISH_DOWNLOAD = 2;
    public static final int FAIL_DOWNLOAD = 3;
    public Activity activity = null;
    private static final String TAG = "AutoUpdate";
    private String strURL = "";
    private ProgressBar progress_bar = null;
    private TextView progress_text = null;
    private AlertDialog dialog;
    private Boolean isUpdate = true;
    private boolean isCancelDownload=false;
    private String mTitle;
    private String mFileLocalPath;

    private Handler mMainMenuHandle;
    public DownLoadFile(Activity activity, String fileUrl, String filePath,
                        String title, Handler handler) {
        this.activity = activity;
        strURL = fileUrl;
        mTitle = title;
        mFileLocalPath = filePath;
        mMainMenuHandle = handler;
    }

    public void startDownload() {
        downloadTheFile(strURL);
        showWaitDialogNoExit();
    }

    public void showWaitDialogNoExit() {
        LayoutInflater inflate = LayoutInflater.from(activity);
        View auto_update_progress_dialog = inflate.inflate(
                R.layout.progress_dialog, null);
        progress_bar = (ProgressBar) auto_update_progress_dialog
                .findViewById(R.id.progress_bar);
        progress_text = (TextView) auto_update_progress_dialog
                .findViewById(R.id.progress_text);

        dialog = new AlertDialog.Builder(activity)
                .setTitle(mTitle)
                .setView(auto_update_progress_dialog)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface d, int which) {
                        isCancelDownload = true;
                        if (dialog != null) {
                            dialog.dismiss();
                            dialog = null;
                        }
                    }
                }).create();
        dialog.setCancelable(false);
        dialog.show();

    }

    public void dissmissWaitDialog() {
        if (dialog != null) {
            dialog.cancel();
            dialog.dismiss();
            dialog = null;
        }
    }

    public void setWaitDialogProcess(int curProcess, int total) {
        if (total == 0)
            return;
        progress_bar.setProgress(curProcess * 100 / total);
        if (this.isUpdate) {
            progress_text
                    .setText(curProcess + "KB/" + total + "KB");
        }
    }

    private void downloadTheFile(final String strPath) {

        try {
            Runnable r = new Runnable() {
                public void run() {
                    try {
                        doDownloadTheFile(strPath);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            };
            new Thread(r).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int DOWNLOAD_BUFFER_SIZE = 1024;

    private void doDownloadTheFile(String strPath) throws Exception {
        Log.i(TAG, "getDataSource()");
        if (!URLUtil.isNetworkUrl(strPath)) {
            Log.i(TAG, "getDataSource() It's a wrong URL!");
        } else {

            URL url;
            HttpURLConnection urlcon = null;

            try {
                url = new URL(strPath);
                urlcon = (HttpURLConnection) url.openConnection();
                long total = urlcon.getContentLength();
                InputStream input = urlcon.getInputStream();
                FileOutputStream fos = null;
                if (input != null) {
                    File myTempFile = new File(mFileLocalPath);

                    if (myTempFile.exists()) {
                        myTempFile.delete();
                    }

                    fos = new FileOutputStream(myTempFile);

                    byte[] buffer = new byte[DOWNLOAD_BUFFER_SIZE];
                    int iReadLength = -1;
                    long curReadLength = 0;
                    while ((iReadLength = input.read(buffer, 0, buffer.length)) != -1) {
                        fos.write(buffer, 0, iReadLength);
                        curReadLength += iReadLength;

                        Message msg = mMainMenuHandle.obtainMessage();
                        msg.what = START_DOWNLOAD;
                        msg.arg1 = (int) (curReadLength / 1024);
                        msg.arg2 = (int) (total / 1024);
                        // msg.arg2 = (int) (curReadLength * 100 / total);
                        mMainMenuHandle.sendMessage(msg);
                        if(isCancelDownload){
                            return;
                        }
                    }

                    fos.flush();

                    if (fos != null) {
                        fos.close();
                    }

                    Log.i(TAG, "getDataSource() Download  ok...");

                    if (dialog != null) {
                        dialog.cancel();
                        dialog.dismiss();
                        dialog = null;
                        Message msg = mMainMenuHandle.obtainMessage();
                        msg.what = FINISH_DOWNLOAD;
                        mMainMenuHandle.sendMessage(msg);
                    }

                }

            } catch (Exception e) {
                Message msg = mMainMenuHandle.obtainMessage();
                msg.what = FAIL_DOWNLOAD;
                mMainMenuHandle.sendMessage(msg);
                e.printStackTrace();
            }
        }
    }
}

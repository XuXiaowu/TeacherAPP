package com.lichen.teacher.apps;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lichen.teacher.R;
import com.lichen.teacher.util.ConstUtils;
import com.lichen.teacher.util.ConvertUtils;
import com.lichen.teacher.util.FileUtils;

import java.io.File;
import java.text.DecimalFormat;

/**
 * update by xiaowu on 2016/8/23.
 */
public class ActivitySetting extends AppCompatActivity {

    private RelativeLayout mClearCatchView;
    private ProgressBar mClearCatchProgressView;
    private TextView mCatchSizeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_view);

        initView();
        setViewClickListener();
        computeCatchSize();
    }

    private View.OnClickListener mFunctionViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.clear_catch_view:
                    mClearCatchView.setClickable(false);
                    mClearCatchProgressView.setVisibility(View.VISIBLE);
                    mCatchSizeView.setVisibility(View.GONE);
                    new Thread(new MyThread()).start();
                    break;
            }
        }
    };

    private void initView() {
        mClearCatchView = (RelativeLayout) findViewById(R.id.clear_catch_view);
        mCatchSizeView = (TextView) findViewById(R.id.catch_size_view);
        mClearCatchProgressView = (ProgressBar) findViewById(R.id.clear_catch_progress_view);
    }

    private void setViewClickListener() {
        mClearCatchView.setOnClickListener(mFunctionViewClickListener);
    }

    private void computeCatchSize() {
        File file = Glide.getPhotoCacheDir(this);
        long size = getTotalSizeOfFilesInDir(file);
        DecimalFormat df = new DecimalFormat("#.00");
        String sizeStr;
        if (size == 0) {
            sizeStr = "0KB";
        }else if (size < ConstUtils.MB) {
            sizeStr = df.format(FileUtils.byte2Size(size, ConstUtils.MemoryUnit.KB));
            sizeStr =  sizeStr + "KB";
        } else {
            sizeStr = df.format(FileUtils.byte2Size(size, ConstUtils.MemoryUnit.MB));
            sizeStr =  sizeStr + "MB";
        }
        mCatchSizeView.setText(sizeStr);
    }

    private long getTotalSizeOfFilesInDir(final File file) {
        if (file.isFile()) return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mClearCatchView.setClickable(true);
                    mClearCatchProgressView.setVisibility(View.GONE);
                    mCatchSizeView.setText("0KB");
                    mCatchSizeView.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    private class MyThread implements Runnable {
        @Override
        public void run() {
            File file = Glide.getPhotoCacheDir(ActivitySetting.this);
            FileUtils.deleteDir(file);
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
        }
    }

}

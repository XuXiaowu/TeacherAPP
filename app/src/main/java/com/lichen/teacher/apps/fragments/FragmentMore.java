package com.lichen.teacher.apps.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lichen.teacher.R;
import com.lichen.teacher.apps.ActivityFeedback;
import com.lichen.teacher.apps.ActivityUserEdit;
import com.lichen.teacher.apps.ActivityUserLogin;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.service.DownloadService;
import com.lichen.teacher.util.ConstUtils;
import com.lichen.teacher.util.FileUtils;
import com.lichen.teacher.util.PreferenceUtils;
import com.lichen.teacher.util.ShowUtils;
import com.lichen.teacher.view.CircleImageView;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by xiaowu on 2016/9/2.
 */
public class FragmentMore extends Fragment {

    private View mContentView;
    private CircleImageView mUserHeadView;
    private RelativeLayout mFunctionFeedbackView;
    private RelativeLayout mFunctionClearCacheView;
    private RelativeLayout mFunctionCheckUpdateView;
    private RelativeLayout mFunctionCustomServicePhoneView;
    private TextView mCacheSizeView;
    private ProgressBar mClearCacheProgressView;
    private ProgressBar mCheckUpdateProgressView;
    private Button mExitLoginBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_more_view, container, false);
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        initView();
        setViewClickListener();
        computeCatchSize();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private View.OnClickListener mFunctionViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.feedback_view:
                    intent = new Intent();
                    intent.setClass(getActivity(), ActivityFeedback.class);
                    startActivity(intent);
                    break;
                case R.id.clear_cache_view:
                    mFunctionClearCacheView.setClickable(false);
                    mClearCacheProgressView.setVisibility(View.VISIBLE);
                    mCacheSizeView.setVisibility(View.GONE);
                    new Thread(new ClearCacheRunnable()).start();
                    break;
                case R.id.check_update_view:
                    mFunctionCheckUpdateView.setClickable(false);
                    mCheckUpdateProgressView.setVisibility(View.VISIBLE);
                    ShowUtils.showUpdateVersioniDialog(getActivity(), "更新测试", mSureUpdateClickListener);
                    break;
                case R.id.custom_service_phone_view:
                    intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:400-835-0088"));
                    startActivity(intent);

            }
        }
    };

    private View.OnClickListener mUserHeadClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ActivityUserEdit.class);
            startActivity(intent);
        }
    };

    private DialogInterface.OnClickListener mSureUpdateClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mFunctionCheckUpdateView.setClickable(true);
            mCheckUpdateProgressView.setVisibility(View.GONE);
            downloadNewVersion();
        }
    };

    private DialogInterface.OnClickListener mSureExitLoginClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            PreferenceUtils.setStringPref(Constant.USER_ID, "");
            Intent intent = new Intent();
            intent.setClass(getActivity(), ActivityUserLogin.class);
            startActivity(intent);
            getActivity().finish();
        }
    };

    private View.OnClickListener mExitLoginBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShowUtils.showDialog(getActivity(), R.string.app_name, R.string.
                    more_exit_login_dialog_content, mSureExitLoginClickListener, null);
        }
    };

    private void initView(){
        mUserHeadView = (CircleImageView) mContentView.findViewById(R.id.user_head_view);
        mFunctionFeedbackView = (RelativeLayout) mContentView.findViewById(R.id.feedback_view);
        mFunctionClearCacheView = (RelativeLayout) mContentView.findViewById(R.id.clear_cache_view);
        mFunctionCheckUpdateView = (RelativeLayout) mContentView.findViewById(R.id.check_update_view);
        mFunctionCustomServicePhoneView = (RelativeLayout) mContentView.findViewById(R.id.custom_service_phone_view);
        mCacheSizeView = (TextView) mContentView.findViewById(R.id.catch_size_view);
        mClearCacheProgressView = (ProgressBar) mCacheSizeView.findViewById(R.id.clear_cache_progress_view);
        mCheckUpdateProgressView = (ProgressBar) mContentView.findViewById(R.id.check_update_progress_view);
        mExitLoginBtn = (Button) mContentView.findViewById(R.id.exit_login_btn);
    }

    private void setViewClickListener() {
        mUserHeadView.setOnClickListener(mUserHeadClickListener);
        mFunctionFeedbackView.setOnClickListener(mFunctionViewClickListener);
        mFunctionClearCacheView.setOnClickListener(mFunctionViewClickListener);
        mFunctionCheckUpdateView.setOnClickListener(mFunctionViewClickListener);
        mFunctionCustomServicePhoneView.setOnClickListener(mFunctionViewClickListener);
        mExitLoginBtn.setOnClickListener(mExitLoginBtnClickListener);
    }

    private void computeCatchSize() {
        File file = Glide.getPhotoCacheDir(getActivity());
        long size = getTotalSizeOfFilesInDir(file);
        DecimalFormat df = new DecimalFormat("#.00");
        String sizeStr;
        if (size == 0) {
            sizeStr = "0KB";
        }else if (size < ConstUtils.MB) {
            sizeStr = df.format(FileUtils.byte2Size(size, ConstUtils.MemoryUnit.KB));
            if (sizeStr.substring(0, 1).equals(".")) sizeStr = "0" + sizeStr;
            sizeStr =  sizeStr + "KB";
        } else {
            sizeStr = df.format(FileUtils.byte2Size(size, ConstUtils.MemoryUnit.MB));
            sizeStr =  sizeStr + "MB";
        }
        mCacheSizeView.setText(sizeStr);
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
                    mFunctionClearCacheView.setClickable(true);
                    mClearCacheProgressView.setVisibility(View.GONE);
                    mCacheSizeView.setText("0KB");
                    mCacheSizeView.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    private class ClearCacheRunnable implements Runnable {
        @Override
        public void run() {
            File file = Glide.getPhotoCacheDir(getActivity());
            FileUtils.deleteDir(file);
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
        }
    }

    private void downloadNewVersion() {
        Intent intent = new Intent();
        intent.putExtra(Constant.EXTRA_DOWNLOAD_URL, "https://apple.lichenjy.com/android/LCJY.apk");
        intent.setClass(getActivity(), DownloadService.class);
        getActivity().startService(intent);
        Toast.makeText(getActivity(), R.string.downloading_new_version, Toast.LENGTH_SHORT).show();
    }


}

package com.lichen.teacher.apps.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.LiveAdapter;
import com.lichen.teacher.apps.ActivityLive;
import com.lichen.teacher.apps.ActivityLiveDetails;
import com.lichen.teacher.apps.ActivityLiveRecord;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.models.LiveParcelable;
import com.lichen.teacher.models.LiveResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016/9/9.
 */
public class FragmentLive extends Fragment {

    public static final int COUNT_DOWN_START = 0;
    public static final int COUNT_DOWN_STOP = 1;

    private View mContentView;
    private ImageView mRecordBtn;
    private LRecyclerView mListView;
    private TextView mLoadingStatusView;
    private LinearLayout mLoadingView;
    private RelativeLayout mLoadingContainView;

    private LiveAdapter mLiveAdapter;
    private List mDataList;
    private boolean mRunFlag = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_live_view, container, false);
        initView();
        setViewListener();
        setTestData();
        setupListView();
        setLoadingErrorStatus();
        return mContentView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private View.OnClickListener mRightBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ActivityLiveRecord.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener mLoadingErrorOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mLiveAdapter.setDataList(mDataList);
            setLoadingNoneStatus();
        }
    };

    private View.OnClickListener mItemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LiveResult cpr = (LiveResult) v.getTag();
            LiveParcelable lp = new LiveParcelable();
            lp.setImageUrl(cpr.imageUrl);
            lp.setTitle(cpr.title);
            lp.setTeacher(cpr.teacher);
            lp.setDate(cpr.date);
            lp.setStartTime(cpr.startTime);
            lp.setEndTime(cpr.endTime);
            lp.setType(cpr.type);
            lp.setSummary(cpr.summary);
            Intent intent = new Intent();
            intent.putExtra(Constant.EXTRA_LIVE_INFO, lp);
            intent.setClass(getActivity(), ActivityLiveDetails.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener mStatusBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LiveResult cpr = (LiveResult) v.getTag();
            LiveParcelable lp = new LiveParcelable();
            lp.setImageUrl(cpr.imageUrl);
            lp.setTitle(cpr.title);
            lp.setTeacher(cpr.teacher);
            lp.setDate(cpr.date);
            lp.setStartTime(cpr.startTime);
            lp.setEndTime(cpr.endTime);
            lp.setType(cpr.type);
            lp.setSummary(cpr.summary);
            Intent intent = new Intent();
            intent.putExtra(Constant.EXTRA_LIVE_INFO, lp);
            intent.setClass(getActivity(), ActivityLive.class);
            startActivity(intent);
        }
    };

    private void initView() {
        mRecordBtn = (ImageView) mContentView.findViewById(R.id.right_btn);
        mListView = (LRecyclerView) mContentView.findViewById(R.id.list_view);
        mLoadingContainView = (RelativeLayout) mContentView.findViewById(R.id.loading_status_contain_view);
        mLoadingStatusView = (TextView) mContentView.findViewById(R.id.loading_status_view);
        mLoadingView = (LinearLayout) mContentView.findViewById(R.id.loading_view);
    }

    private void setViewListener() {
        mRecordBtn.setOnClickListener(mRightBtnClickListener);
        mLoadingContainView.setOnClickListener(mLoadingErrorOnClickListener);
    }

    private void setupListView() {
        mLiveAdapter = new LiveAdapter(getActivity());
        mLiveAdapter.setItemOnClickListener(mItemOnClickListener);
        mLiveAdapter.setStatusBtnClickListener(mStatusBtnClickListener);
//        mLiveAdapter.setDataList(mTestData);
        HeaderAndFooterRecyclerViewAdapter hafrva = new HeaderAndFooterRecyclerViewAdapter(getActivity(), mLiveAdapter);
        mListView.setAdapter(hafrva);
        mListView.setPullRefreshEnabled(false);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setLoadingNoneStatus() {
        mListView.setVisibility(View.VISIBLE);
        mLoadingContainView.setVisibility(View.GONE);
    }

    private void setLoadingStatus() {
        mListView.setVisibility(View.GONE);
        mLoadingStatusView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void setLoadingErrorStatus() {
        mListView.setVisibility(View.GONE);
        mLoadingContainView.setVisibility(View.VISIBLE);
        mLoadingStatusView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }

    private void setTestData() {
        mDataList = new ArrayList();
        for (int i = 0; i < 20; i++) {
            LiveResult cpr = new LiveResult();
            cpr.imageUrl = "http://img5.duitang.com/uploads/item/201506/14/20150614214047_BA5Zy.jpeg";
            cpr.title = "标题标题标题" + i;
            cpr.teacher = "老师";
            cpr.date = "2016-10-04";
            cpr.startTime = "20:30:60";
            if (i % 2 == 0) cpr.date = "2016-10-08";
            cpr.endTime = "21:50:00";
            cpr.price = 19.9f;
            cpr.type = "类型";
            cpr.summary = "简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述";
            cpr.watchCount = 99;
            mDataList.add(cpr);
        }
    }

    private class CountDownThread extends Thread {

        @Override
        public void run() {
            while (mRunFlag) {
                try {
                    synchronized(this) {
                        wait(1000);
                    }
                    mHandler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mLiveAdapter.notifyDataSetChanged();
                    break;

            }
        }
    };

//    public class NotifyListReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            int countDownFlag = intent.getIntExtra(Constant.EXTRA_FRAGMENT_LIVE_COUNT_DOWN_TAG, 0);
//            if (countDownFlag == COUNT_DOWN_START)  {
//                mRunFlag = true;
//                new CountDownThread().start();
//            } else if (countDownFlag == COUNT_DOWN_STOP) mRunFlag = false;
//        }
//    }

    public void setCountdownEnable(boolean enable) {
        mRunFlag = enable;
        if (mRunFlag) {
            new CountDownThread().start();
        }
    }
}

//package com.lichen.teacher.apps;
//
//import android.content.Intent;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
//import com.cundong.recyclerview.LRecyclerView;
//import com.lichen.teacher.R;
//import com.lichen.teacher.adapter.LiveMyAdapter;
//import com.lichen.teacher.global.Constant;
//import com.lichen.teacher.models.LiveResult;
//import com.lichen.teacher.models.LiveParcelable;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ActivityLiveMy extends AppCompatActivity {
//
//    private ImageView mBackView;
//    private LRecyclerView mListView;
//    private TextView mLoadingStatusView;
//    private LinearLayout mLoadingView;
//    private RelativeLayout mLoadingContainView;
//    private ImageView mRightBtn;
//
//    private LiveMyAdapter mLiveMyAdapter;
//    private List mDataList;
//    private boolean runFlag = true;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_live_my);
//
//        initView();
//        setViewListener();
//        setTestData();
//        setupListView();
//        setLoadingErrorStatus();
//        new CountDownThread().start();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        runFlag = false;
//    }
//
//    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            onBackPressed();
//        }
//    };
//
//    private View.OnClickListener mRightBtnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent();
//            intent.setClass(ActivityLiveMy.this, ActivityLiveRecord.class);
//            startActivity(intent);
//        }
//    };
//
//    private View.OnClickListener mLoadingErrorOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            mLiveMyAdapter.setDataList(mDataList);
//            setLoadingNoneStatus();
//        }
//    };
//
//    private View.OnClickListener mItemOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            LiveResult cpr = (LiveResult) v.getTag();
//            LiveParcelable lp = new LiveParcelable();
//            lp.setImageUrl(cpr.imageUrl);
//            lp.setTitle(cpr.title);
//            lp.setTeacher(cpr.teacher);
//            lp.setDate(cpr.date);
//            lp.setStartTime(cpr.startTime);
//            lp.setEndTime(cpr.endTime);
//            lp.setType(cpr.type);
//            lp.setSummary(cpr.summary);
//            Intent intent = new Intent();
//            intent.putExtra(Constant.EXTRA_LIVE_INFO, lp);
//            intent.setClass(ActivityLiveMy.this, ActivityLiveMyDetails.class);
//            startActivity(intent);
//        }
//    };
//
//    private void initView() {
//        mBackView = (ImageView) findViewById(R.id.back_view);
//        mListView = (LRecyclerView) findViewById(R.id.list_view);
//        mLoadingContainView = (RelativeLayout) findViewById(R.id.loading_status_contain_view);
//        mLoadingStatusView = (TextView) findViewById(R.id.loading_status_view);
//        mLoadingView = (LinearLayout) findViewById(R.id.loading_view);
//        mRightBtn = (ImageView) findViewById(R.id.right_btn);
//    }
//
//    private void setViewListener() {
//        mBackView.setOnClickListener(mBackViewClickListener);
//        mRightBtn.setOnClickListener(mRightBtnClickListener);
//        mLoadingContainView.setOnClickListener(mLoadingErrorOnClickListener);
//    }
//
//    private void setupListView() {
//        mLiveMyAdapter = new LiveMyAdapter(this);
//        mLiveMyAdapter.setItemOnClickListener(mItemOnClickListener);
////        mLiveAdapter.setDataList(mTestData);
//        HeaderAndFooterRecyclerViewAdapter hafrva = new HeaderAndFooterRecyclerViewAdapter(this, mLiveMyAdapter);
//        mListView.setAdapter(hafrva);
//        mListView.setPullRefreshEnabled(false);
//        mListView.setLayoutManager(new LinearLayoutManager(this));
//    }
//
//    private void setLoadingNoneStatus() {
//        mListView.setVisibility(View.VISIBLE);
//        mLoadingContainView.setVisibility(View.GONE);
//    }
//
//    private void setLoadingStatus() {
//        mListView.setVisibility(View.GONE);
//        mLoadingStatusView.setVisibility(View.GONE);
//        mLoadingView.setVisibility(View.VISIBLE);
//    }
//
//    private void setLoadingErrorStatus() {
//        mListView.setVisibility(View.GONE);
//        mLoadingContainView.setVisibility(View.VISIBLE);
//        mLoadingStatusView.setVisibility(View.VISIBLE);
//        mLoadingView.setVisibility(View.GONE);
//    }
//
//    private void setTestData() {
//        mDataList = new ArrayList();
//        for (int i = 0; i < 20; i++) {
//            LiveResult cpr = new LiveResult();
//            cpr.imageUrl = "http://img5.duitang.com/uploads/item/201506/14/20150614214047_BA5Zy.jpeg";
//            cpr.title = "标题标题标题" + i;
//            cpr.teacher = "老师";
//            cpr.date = "2016-10-04";
//            cpr.startTime = "20:30:60";
//            if (i % 2 == 0) cpr.date = "2016-10-08";
//            cpr.endTime = "21:50:00";
//            cpr.price = 19.9f;
//            cpr.type = "类型";
//            cpr.summary = "简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述";
//            cpr.watchCount = 99;
//            mDataList.add(cpr);
//        }
//    }
//
//    private class CountDownThread extends Thread {
//
//        @Override
//        public void run() {
//            while (runFlag) {
//                try {
//                    synchronized(this) {
//                        wait(1000);
//                    }
//                    mHandler.sendEmptyMessage(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1:
//                    mLiveMyAdapter.notifyDataSetChanged();
//                    break;
//
//            }
//        }
//    };
//}

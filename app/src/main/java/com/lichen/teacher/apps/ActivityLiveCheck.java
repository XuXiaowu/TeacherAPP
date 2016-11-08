package com.lichen.teacher.apps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.LiveCheckAdapter;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.models.LiveResult;
import com.lichen.teacher.models.LiveParcelable;

import java.util.ArrayList;
import java.util.List;

public class ActivityLiveCheck extends AppCompatActivity {

    private ImageView mBackView;
    private LRecyclerView mListView;
    private TextView mLoadingStatusView;
    private LinearLayout mLoadingView;
    private RelativeLayout mLoadingContainView;

    private LiveCheckAdapter mCheckProgressAdapter;
    private List mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_check_view);

        initView();
        setViewListener();
        setTestData();
        setupListView();
        setLoadingErrorStatus();
    }

    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private View.OnClickListener mLoadingErrorOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mCheckProgressAdapter.setDataList(mDataList);
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
            intent.setClass(ActivityLiveCheck.this, ActivityLiveCheckDetails.class);
            startActivity(intent);
        }
    };

    private void initView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
        mListView = (LRecyclerView) findViewById(R.id.list_view);
        mLoadingContainView = (RelativeLayout) findViewById(R.id.loading_status_contain_view);
        mLoadingStatusView = (TextView) findViewById(R.id.loading_status_view);
        mLoadingView = (LinearLayout) findViewById(R.id.loading_view);
    }

    private void setViewListener() {
        mBackView.setOnClickListener(mBackViewClickListener);
        mLoadingContainView.setOnClickListener(mLoadingErrorOnClickListener);
    }

    private void setupListView() {
        mCheckProgressAdapter = new LiveCheckAdapter(this);
        mCheckProgressAdapter.setItemOnClickListener(mItemOnClickListener);
//        mLiveAdapter.setDataList(mTestData);
        HeaderAndFooterRecyclerViewAdapter hafrva = new HeaderAndFooterRecyclerViewAdapter(this, mCheckProgressAdapter);
        mListView.setAdapter(hafrva);
        mListView.setPullRefreshEnabled(false);
        mListView.setLayoutManager(new LinearLayoutManager(this));
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
            cpr.date = "2016-10-10";
            cpr.startTime = "20:30";
            cpr.endTime = "21:30";
            cpr.price = 19.9f;
            cpr.type = "类型";
            cpr.summary = "简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述简介描述";
            if (i % 2 == 0) cpr.status = 0;
            else  cpr.status = 1;
            mDataList.add(cpr);
        }
    }
}

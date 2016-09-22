package com.lichen.teacher.apps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.AlreadyLiveAdapter;
import com.lichen.teacher.adapter.WaitLiveAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016//21.
 */
public class ActivityWaitLive extends AppCompatActivity {

    private LRecyclerView mListView;
    private TextView mLoadingStatusView;
    private LinearLayout mLoadingView;

    private WaitLiveAdapter mLiveAdapter;
    private List mTestData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_live_view);

        initView();
        setTestData();
        setupListView();
        setLoadingNone();
    }

    private void initView() {
        mListView = (LRecyclerView) findViewById(R.id.list_view);
        mLoadingStatusView = (TextView) findViewById(R.id.status_view);
        mLoadingView = (LinearLayout) findViewById(R.id.loading_view);
    }

    private void setupListView() {
        mLiveAdapter = new WaitLiveAdapter(this);
        mLiveAdapter.setDataList(mTestData);
        HeaderAndFooterRecyclerViewAdapter hafrva = new HeaderAndFooterRecyclerViewAdapter(this, mLiveAdapter);
        mListView.setAdapter(hafrva);
        mListView.setPullRefreshEnabled(false);
        mListView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setLoadingNone() {
        mLoadingStatusView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.GONE);
    }

    private void setTestData() {
        mTestData = new ArrayList();
        for (int i = 0; i < 20; i++) {
            mTestData.add(0);
        }
    }
}

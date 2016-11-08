package com.lichen.teacher.apps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.ProfitAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016//21.
 */
public class ActivityProfit extends AppCompatActivity {

    private ImageView mBackView;
    private LRecyclerView mListView;
    private TextView mLoadingStatusView;
    private LinearLayout mLoadingView;
    private RelativeLayout mLoadingContainView;

    private ProfitAdapter mProfitAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private HeaderAndFooterRecyclerViewAdapter mHafrva;

    private List mDataList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_view);

        initView();
        setViewListener();
        setupListView();
        setLoadingErrorStatus();
    }

    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private View.OnClickListener mLoadingErrorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setTestData();
            mProfitAdapter.addAll(mDataList);
            setLoadingNoneStatus();
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
        mLoadingContainView.setOnClickListener(mLoadingErrorClickListener);
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

    private void setupListView() {
        mProfitAdapter = new ProfitAdapter(this);
        mHafrva = new HeaderAndFooterRecyclerViewAdapter(this, mProfitAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mListView.setAdapter(mHafrva);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setPullRefreshEnabled(false);
    }

    private void setTestData() {
        for (int i = 0; i < 20; i++) {
            mDataList.add(0);
        }
    }
}

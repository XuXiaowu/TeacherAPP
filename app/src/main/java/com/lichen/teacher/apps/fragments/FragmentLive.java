package com.lichen.teacher.apps.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.LiveAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016/9/9.
 */
public class FragmentLive extends Fragment {

    private View mContentView;
    private LRecyclerView mListView;
    private TextView mLoadingStatusView;
    private LinearLayout mLoadingView;

    private LiveAdapter mLiveAdapter;
    private List mTestData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_live_view, container, false);
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        initView();
        setTestData();
        setupListView();
        setLoadingNone();
    }

    private void initView() {
        mListView = (LRecyclerView) mContentView.findViewById(R.id.list_view);
        mLoadingStatusView = (TextView) mContentView.findViewById(R.id.status_view);
        mLoadingView = (LinearLayout) mContentView.findViewById(R.id.loading_view);
    }

    private void setupListView() {
        mLiveAdapter = new LiveAdapter(getActivity());
        mLiveAdapter.setDataList(mTestData);
        HeaderAndFooterRecyclerViewAdapter hafrva = new HeaderAndFooterRecyclerViewAdapter(getActivity(), mLiveAdapter);
        mListView.setAdapter(hafrva);
        mListView.setPullRefreshEnabled(false);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

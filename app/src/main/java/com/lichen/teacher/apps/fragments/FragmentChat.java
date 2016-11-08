package com.lichen.teacher.apps.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.ChatAdapter;
import com.lichen.teacher.apps.ActivityChatDetails;
import com.lichen.teacher.models.ChatResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016/10/5.
 */
public class FragmentChat extends Fragment {

    private View mContentView;
    private LRecyclerView mListView;
    private TextView mLoadingStatusView;
    private LinearLayout mLoadingView;
    private RelativeLayout mLoadingContainView;

    private ChatAdapter mChatAdapter;
    private List mDataList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_chat_view, container, false);
        initView();
        setViewListener();
        setupListView();
        setTestData();
        setLoadingErrorStatus();
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();

//        initView();
//        setViewListener();
//        setupListView();
//        setTestData();
//        setLoadingErrorStatus();
    }

    private View.OnClickListener mLoadingErrorOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mChatAdapter.setDataList(mDataList);
            setLoadingNoneStatus();
        }
    };

    private View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ActivityChatDetails.class);
            startActivity(intent);
        }
    };

    private void initView() {
        mListView = (LRecyclerView) mContentView.findViewById(R.id.list_view);
        mLoadingContainView = (RelativeLayout) mContentView.findViewById(R.id.loading_status_contain_view);
        mLoadingStatusView = (TextView) mContentView.findViewById(R.id.loading_status_view);
        mLoadingView = (LinearLayout) mContentView.findViewById(R.id.loading_view);
    }

    private void setViewListener() {
        mLoadingContainView.setOnClickListener(mLoadingErrorOnClickListener);
    }

    private void setupListView() {
        mChatAdapter = new ChatAdapter(getActivity());
        mChatAdapter.setItemOnClickListener(mItemClickListener);
//        mLiveAdapter.setDataList(mTestData);
        HeaderAndFooterRecyclerViewAdapter hafrva = new HeaderAndFooterRecyclerViewAdapter(getActivity(), mChatAdapter);
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
            ChatResult cr = new ChatResult();
            cr.imagerUrl = "http://img5.duitang.com/uploads/item/201506/14/20150614214047_BA5Zy.jpeg";
            cr.title = "标题" + i;
            cr.summary = "简介简介简介简介简介简介简介简介简介" + i;
            mDataList.add(cr);
        }
    }
}

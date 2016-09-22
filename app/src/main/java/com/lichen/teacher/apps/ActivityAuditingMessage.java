package com.lichen.teacher.apps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.LiveAdapter;
import com.lichen.teacher.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016//21.
 */
public class ActivityAuditingMessage extends AppCompatActivity {

    private LRecyclerView mListView;

    private MessageAdapter mMessageAdapter;

    private List mTestData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditing_message_view);

        initView();
        setTestData();
        setupListView();
    }

    private void initView() {
        mListView = (LRecyclerView) findViewById(R.id.list_view);
//        mLoadingStatusView = (TextView) mContentView.findViewById(R.id.status_view);
//        mLoadingView = (LinearLayout) mContentView.findViewById(R.id.loading_view);
    }

    private void setupListView() {
        mMessageAdapter = new MessageAdapter(this);
        mMessageAdapter.setDataList(mTestData);
        HeaderAndFooterRecyclerViewAdapter hafrva = new HeaderAndFooterRecyclerViewAdapter(this, mMessageAdapter);
        mListView.setAdapter(hafrva);
        mListView.setPullRefreshEnabled(false);
        mListView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setTestData() {
        mTestData = new ArrayList();
        for (int i = 0; i < 20; i++) {
            mTestData.add(0);
        }
    }
}

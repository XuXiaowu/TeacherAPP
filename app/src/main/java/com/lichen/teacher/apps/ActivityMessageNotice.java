package com.lichen.teacher.apps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.MessageNoticeAdapter;
import com.lichen.teacher.database.MessageNoticeProvider;
import com.lichen.teacher.models.MessageNoticeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xiaowu on 2016//21.
 */
public class ActivityMessageNotice extends AppCompatActivity {

    private LRecyclerView mListView;

    private MessageNoticeAdapter mMessageAdapter;

    private List<MessageNoticeInfo> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_notice_view);

        initView();
//        setTestData();
        setDataList();
        setupListView();
        MessageNoticeProvider.setMessageAlreadyRead();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private void initView() {
        mListView = (LRecyclerView) findViewById(R.id.list_view);
//        mLoadingStatusView = (TextView) mContentView.findViewById(R.id.status_view);
//        mLoadingView = (LinearLayout) mContentView.findViewById(R.id.loading_view);
    }

    private void setupListView() {
        mMessageAdapter = new MessageNoticeAdapter(this);
        mMessageAdapter.setDataList(mDataList);
        HeaderAndFooterRecyclerViewAdapter hafrva = new HeaderAndFooterRecyclerViewAdapter(this, mMessageAdapter);
        mListView.setAdapter(hafrva);
        mListView.setPullRefreshEnabled(false);
        mListView.setLayoutManager(new LinearLayoutManager(this));
    }
    
    private void setDataList() {
        List<MessageNoticeInfo> list = MessageNoticeProvider.findAllMessage();
        for (int i = list.size() - 1; i >= 0; i--) {
            mDataList.add(list.get(i));
        }
    }

//    private void setTestData() {
//        mDataList = new ArrayList();
//        for (int i = 0; i < 20; i++) {
//            mDataList.add(0);
//        }
//    }
}

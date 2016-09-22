package com.lichen.teacher.apps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.MessageAdapter;
import com.lichen.teacher.adapter.MyClassAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016//21.
 */
public class ActivityMyClass extends AppCompatActivity {

    private ImageView mAddView;
    private LRecyclerView mListView;

    private MyClassAdapter mMyClassAdapter;

    private List mTestData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class_view);

        initView();
        setViewListener();
        setTestData();
        setupListView();
    }

    private View.OnClickListener mAddViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ActivityMyClass.this, ActivityCreateClass.class);
            startActivity(intent);
        }
    };

    private void initView() {
        mAddView = (ImageView) findViewById(R.id.add_view);
        mListView = (LRecyclerView) findViewById(R.id.list_view);
//        mLoadingStatusView = (TextView) mContentView.findViewById(R.id.status_view);
//        mLoadingView = (LinearLayout) mContentView.findViewById(R.id.loading_view);
    }

    private void setupListView() {
        mMyClassAdapter = new MyClassAdapter(this);
        mMyClassAdapter.setDataList(mTestData);
        HeaderAndFooterRecyclerViewAdapter hafrva = new HeaderAndFooterRecyclerViewAdapter(this, mMyClassAdapter);
        mListView.setAdapter(hafrva);
        mListView.setPullRefreshEnabled(false);
        mListView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setViewListener() {
        mAddView.setOnClickListener(mAddViewClickListener);
    }

    private void setTestData() {
        mTestData = new ArrayList();
        for (int i = 0; i < 20; i++) {
            mTestData.add(0);
        }
    }
}

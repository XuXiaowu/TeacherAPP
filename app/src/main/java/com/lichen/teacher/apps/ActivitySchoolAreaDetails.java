package com.lichen.teacher.apps;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.SchoolAreaDetailsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016/8/20.
 */
public class ActivitySchoolAreaDetails extends Activity {

    private LRecyclerView mListView;
    private ImageView mBackView;

    private LinearLayoutManager mLinearLayoutManager;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private SchoolAreaDetailsAdapter mSchoolAreaDetailsAdapter;

    private List mDataList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_area_details_view);

        initView();
        setUpListView();
        setTestData();
    }

    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private void initView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
        mBackView.setOnClickListener(mBackViewClickListener);
        mListView = (LRecyclerView) findViewById(R.id.list_view);
    }

    private void setUpListView() {
        mSchoolAreaDetailsAdapter = new SchoolAreaDetailsAdapter(this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(this, mSchoolAreaDetailsAdapter);
        mListView.setLayoutManager(mLinearLayoutManager);
        mListView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mListView.setPullRefreshEnabled(false);
    }

    private void setTestData() {
        for (int i = 0; i < 20; i++) {
            mDataList.add(0);
        }
        mSchoolAreaDetailsAdapter.addAll(mDataList);
    }
}

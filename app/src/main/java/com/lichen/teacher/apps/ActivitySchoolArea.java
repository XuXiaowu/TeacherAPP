package com.lichen.teacher.apps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.lichen.teacher.R;
import com.lichen.teacher.adapter.SchoolAreaAdapter;
import com.lichen.teacher.models.SchoolArea;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016/7/25.
 */
public class ActivitySchoolArea extends Activity {

    private ImageView mBackView;
    private ExpandableListView mExpandableListView;
    private SchoolAreaAdapter mSchoolAreaAdapter;
    private List mDataList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_area_view);

        initView();
        setTestData();
        setUpExpandableListView();
    }

    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private View.OnClickListener mChildItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ActivitySchoolArea.this, ActivitySchoolAreaDetails.class);
            startActivity(intent);
        }
    };

    private void initView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
        mBackView.setOnClickListener(mBackViewClickListener);
        mExpandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);
    }

    private void setUpExpandableListView() {
        mSchoolAreaAdapter = new SchoolAreaAdapter(this, mDataList);
        mSchoolAreaAdapter.setChildItemClickListener(mChildItemClickListener);
        mExpandableListView.setAdapter(mSchoolAreaAdapter);
    }

    private void setTestData() {
        for (int i = 0; i < 20; i++) {
            SchoolArea sa = new SchoolArea();
            sa.citys = new SchoolArea.City[10];
            sa.province = "父类型" + i;
            for (int j = 0; j < 10; j++) {
                sa.citys[j] = new SchoolArea.City();
                sa.citys[j].city = "子类型" + j;
            }
            mDataList.add(sa);
        }
    }
}

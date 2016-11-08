package com.lichen.teacher.apps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lichen.teacher.R;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.models.LiveParcelable;

public class ActivityLiveCheckDetails extends AppCompatActivity {

    private ImageView mBackView;
    private ImageView mCoverView;
    private TextView mTitleView;
    private TextView mTeacherView;
    private TextView mDateView;
    private TextView mPriceView;
    private TextView mTypeView;
    private TextView mSummaryView;
    private Button mStatusBtn;

    private LiveParcelable mLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_check_details_view);

        initView();
        setViewListener();
        getExtraData();
        setExtraData();
    }

    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private View.OnClickListener mStatusBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putExtra(Constant.EXTRA_LIVE_INFO, mLive);
            intent.setClass(ActivityLiveCheckDetails.this, ActivityLiveRequest.class);
            startActivity(intent);
        }
    };

    private void initView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
        mCoverView = (ImageView) findViewById(R.id.cover_view);
        mTitleView = (TextView) findViewById(R.id.activity_title_view);
        mTeacherView = (TextView) findViewById(R.id.teacher_view);
        mDateView = (TextView) findViewById(R.id.date_view);
        mPriceView = (TextView) findViewById(R.id.price_edit_view);
        mTypeView = (TextView) findViewById(R.id.type_view);
        mSummaryView = (TextView) findViewById(R.id.edit_summary_view);
        mStatusBtn = (Button) findViewById(R.id.status_btn);
    }

    private void setViewListener() {
        mBackView.setOnClickListener(mBackViewClickListener);
        mStatusBtn.setOnClickListener(mStatusBtnClickListener);
    }

    private void getExtraData() {
        Intent intent = getIntent();
        mLive = intent.getParcelableExtra(Constant.EXTRA_LIVE_INFO);
    }

    private void setExtraData() {
        if (mLive != null) {
            mTitleView.setText(mLive.getTitle());
            mTeacherView.setText(mLive.getTeacher());
            mDateView.setText(mLive.getDate() + " " + mLive.getStartTime() + "-" + mLive.getEndTime());
            mPriceView.setText(String.valueOf(mLive.getPrice()));
            mTypeView.setText(mLive.getType());
            mSummaryView.setText(mLive.getSummary());
            setCover(mLive.getImageUrl());
        }
    }

    private void setCover(String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.cover_default)
//                .listener(mRequestListener)
                .into(mCoverView);
    }
}

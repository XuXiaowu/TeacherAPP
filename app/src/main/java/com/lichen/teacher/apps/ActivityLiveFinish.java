
package com.lichen.teacher.apps;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lichen.teacher.R;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.models.LiveParcelable;

public class ActivityLiveFinish extends AppCompatActivity {

    private ImageView mCoverView;
    private Button mGoToChatBtn;
    private Button mBackToHomeBtn;

    private LiveParcelable mLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_finish_view);

        intiView();
        setViewListener();
        getExtraData();
        setCover();
    }

    private View.OnClickListener mBackToHomeBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            Intent intent = new Intent(Constant.SELECT_HOME_PAGE_ACTION);
            LocalBroadcastManager.getInstance(ActivityLiveFinish.this).sendBroadcast(intent);
        }
    };

    private View.OnClickListener mGoToChatBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            Intent intent = new Intent(Constant.SELECT_CHAT_PAGE_ACTION);
            LocalBroadcastManager.getInstance(ActivityLiveFinish.this).sendBroadcast(intent);
        }
    };


    private void intiView() {
        mCoverView = (ImageView) findViewById(R.id.cover_view);
        mGoToChatBtn = (Button) findViewById(R.id.go_to_chat_btn);
        mBackToHomeBtn = (Button) findViewById(R.id.back_to_home_btn);
    }

    private void setViewListener() {
        mBackToHomeBtn.setOnClickListener(mBackToHomeBtnClickListener);
        mGoToChatBtn.setOnClickListener(mGoToChatBtnClickListener);
    }

    private void getExtraData() {
        Intent intent = getIntent();
        mLive = intent.getParcelableExtra(Constant.EXTRA_LIVE_INFO);
    }

    private void setCover() {
        Glide.with(this)
                .load(mLive.getImageUrl())
                .placeholder(R.drawable.cover_default)
//                .listener(mRequestListener)
                .into(mCoverView);
    }
}

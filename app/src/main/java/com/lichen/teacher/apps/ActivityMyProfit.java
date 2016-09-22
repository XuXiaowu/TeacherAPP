package com.lichen.teacher.apps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.lichen.teacher.R;

/**
 * Created by xiaowu on 2016//21.
 */
public class ActivityMyProfit extends AppCompatActivity {

    private ImageView mBackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profit_view);

        initView();
        setViewListener();
    }

    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void initView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
    }

    private void setViewListener() {
        mBackView.setOnClickListener(mBackViewClickListener);
    }
}

package com.lichen.teacher.apps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lichen.teacher.R;
import com.lichen.teacher.view.ClockView;
import com.lichen.teacher.view.ExamRingView;

public class ActivityExamView extends AppCompatActivity {

    private ClockView mExamRingView;
    private Button mStartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_view);

        initView();
    }

    private View.OnClickListener mStartBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mExamRingView.startAnim();
        }
    };

    private void initView() {
        mExamRingView = (ClockView) findViewById(R.id.ring_view);
        mStartBtn = (Button) findViewById(R.id.start_btn);
        mStartBtn.setOnClickListener(mStartBtnClickListener);
    }
}

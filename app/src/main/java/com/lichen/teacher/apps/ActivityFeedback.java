package com.lichen.teacher.apps;

import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichen.teacher.R;

/**
 * Created by xiaowu on 2016/10/12.
 */
public class ActivityFeedback extends AppCompatActivity {

    private ImageView mBackView;
    private EditText mEditView;
    private Button mSubmitBtn;
    private TextView mVersionView;
    private View mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_view);

        initView();
        setViewListener();
        setVersionInfo();
    }

    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private View.OnClickListener mSubmitBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String feedbackContent = mEditView.getText().toString();
            if (!feedbackContent.equals("")) {
                mLoadingView.setVisibility(View.VISIBLE);
                mSubmitBtn.setVisibility(View.GONE);
                mEditView.setVisibility(View.GONE);
                //TODO
            } else {
                Snackbar.make(mEditView, R.string.feedback_content_must_not_empty, Snackbar.LENGTH_LONG).show();
            }
        }
    };

    private void initView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
        mEditView = (EditText) findViewById(R.id.edit_view);
        mSubmitBtn = (Button) findViewById(R.id.submit_btn);
        mVersionView = (TextView) findViewById(R.id.version_view);
        mLoadingView = findViewById(R.id.loading_view);
    }

    private void setViewListener() {
        mBackView.setOnClickListener(mBackViewClickListener);
        mSubmitBtn.setOnClickListener(mSubmitBtnClickListener);
    }

    private void setVersionInfo() {
        String versionName = "";
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mVersionView.setText(getString(R.string.app_name) + " " + versionName);
    }
}

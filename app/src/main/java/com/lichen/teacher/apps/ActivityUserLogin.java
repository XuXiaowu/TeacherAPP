package com.lichen.teacher.apps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lichen.teacher.R;

public class ActivityUserLogin extends AppCompatActivity {

    private EditText mEditUserIdView;
    private EditText mEditPasswordView;
    private ProgressBar mProgressBar;
    private TextView mForgetPasswordView;
    private TextView mRegisterView;
    private Button mLoginBtn;
    private LinearLayout mLoginFromView;

    private String mUserId;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_view);

        initView();
        setViewClickListener();

    }

    private View.OnClickListener mForgetPasswordViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    private View.OnClickListener mRegisterViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ActivityUserLogin.this, ActivityUserRegister.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener mLoginBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mUserId = mEditUserIdView.getText().toString();
            mPassword = mEditPasswordView.getText().toString();
            setLoadingStatus();

            Intent intent = new Intent();
            intent.setClass(ActivityUserLogin.this, ActivityTabs.class);
            startActivity(intent);
        }
    };

    private void initView() {
        mEditUserIdView = (EditText) findViewById(R.id.edit_userid_view);
        mEditPasswordView = (EditText) findViewById(R.id.edit_password_view);
        mForgetPasswordView = (TextView) findViewById(R.id.forget_password_view);
        mRegisterView = (TextView) findViewById(R.id.register_view);
        mLoginBtn = (Button) findViewById(R.id.login_button);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_view);
        mLoginFromView = (LinearLayout) findViewById(R.id.login_form);
    }

    private void setViewClickListener() {
        mLoginBtn.setOnClickListener(mLoginBtnClickListener);
        mForgetPasswordView.setOnClickListener(mForgetPasswordViewClickListener);
        mRegisterView.setOnClickListener(mRegisterViewClickListener);
    }

    private void setLoadingStatus() {
        mProgressBar.setVisibility(View.VISIBLE);
        mLoginFromView.setVisibility(View.GONE);
    }

    //        mImageView = (ImageView) findViewById(R.id.image_view);
//        mImageView2 = (ImageView) findViewById(R.id.image_view2);
//        Glide.with(this)
//                .load(url)
//                .placeholder(R.mipmap.ic_launcher)
//                .into(mImageView);
//
//        Glide.with(this)
//                .load(url2)
//                .placeholder(R.mipmap.ic_launcher)
//                .into(mImageView2);
}

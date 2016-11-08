package com.lichen.teacher.apps;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lichen.teacher.R;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.util.PreferenceUtils;

/**
 * update by xiaowu on 2016/8/20.
 */
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
        checkLogin();
    }

    private View.OnClickListener mForgetPasswordViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ActivityUserLogin.this, ActivityAccountSet.class);
            intent.putExtra(Constant.EXTRA_PASSWORD_EDIT_TYPE, ActivityAccountSet.PASSWORD_EDIT_TYPE_RETRIEVE);
            startActivity(intent);
        }
    };

    private View.OnClickListener mRegisterViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ActivityUserLogin.this, ActivityAccountSet.class);
            intent.putExtra(Constant.EXTRA_PASSWORD_EDIT_TYPE, ActivityAccountSet.PASSWORD_EDIT_TYPE_REGISTER);
            startActivity(intent);
        }
    };

    private View.OnClickListener mLoginBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mUserId = mEditUserIdView.getText().toString();
            mPassword = mEditPasswordView.getText().toString();
            if (mUserId.equals("")) {
                Snackbar.make(mLoginBtn,R.string.login_user_id_not_empty, Snackbar.LENGTH_LONG).show();
                return;
            } else if (mPassword.equals("")) {
                Snackbar.make(mLoginBtn,R.string.login_password_not_empty, Snackbar.LENGTH_LONG).show();
                return;
            } else {
                PreferenceUtils.setStringPref(Constant.USER_ID, mUserId);
                setLoadingStatus();
                // TODO: 2016/10/12  
                Intent intent = new Intent();
                intent.setClass(ActivityUserLogin.this, ActivityTabs.class);
                startActivity(intent);
            }
           
        }
    };

    private void initView() {
        mEditUserIdView = (EditText) findViewById(R.id.edit_userid_view);
        mEditPasswordView = (EditText) findViewById(R.id.edit_title_view);
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

    private void checkLogin() {
        String userId = PreferenceUtils.getStringPref(Constant.USER_ID, "");
        if (!userId.equals("")) {
            Intent intent = new Intent();
            intent.setClass(ActivityUserLogin.this, ActivityTabs.class);
            startActivity(intent);
        }
    }
    
}

package com.lichen.teacher.apps;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichen.teacher.R;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.util.RegularUtils;

/**
 * update by xiaowu on 2016/8/21.
 */
public class ActivityAccountSet extends AppCompatActivity {

    public static final int PASSWORD_EDIT_TYPE_RETRIEVE = 0;
    public static final int PASSWORD_EDIT_TYPE_REGISTER = 1;
    public static final int PASSWORD_EDIT_TYPE_CHANGE = 2;

    private TextView mTitleView;
    private ImageView mBackView;
    private EditText mPhoneEdieView;
    private EditText mIdentifyingCodeEditView;
    private EditText mPasswordEditView;
    private EditText mConfirmPasswordEditView;
    private Button mGetIdentifyingCodeBtn;
    private Button mSubmitBtn;

    private String mPhoneNumber;
    private String mIdentifyingCode;
    private String mPassword;
    private String mConfirmPassword;

    private int mType;
    private long mThreadId;
    private boolean mRunEnable;
    private int mCountdownSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_set_view);

        initView();
        setViewListener();
        getExtraData();
        setView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRunEnable = false;
    }

    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private View.OnClickListener mGetIdentifyingCodeBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPhoneNumber = mPhoneEdieView.getText().toString();
            if (RegularUtils.isMobileExact(mPhoneNumber)) {
                if (mGetIdentifyingCodeBtn.isClickable()) {
                    mCountdownSecond = 10;
                    mGetIdentifyingCodeBtn.setClickable(false);
                    mGetIdentifyingCodeBtn.setBackgroundResource(R.drawable.blue_btn_press_bg);
                    mRunEnable = true;
                    CountDownThread countDownThread = new CountDownThread();
                    countDownThread.start();
                    mThreadId = countDownThread.getId();
                }
                // TODO: 2016/10/12
            } else {
                Snackbar.make(mPhoneEdieView, R.string.password_edit_phone_number_format_error, Snackbar.LENGTH_LONG).show();
            }
        }
    };

    private View.OnClickListener mSubmitBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mIdentifyingCode = mIdentifyingCodeEditView.getText().toString();
            mPassword = mPasswordEditView.getText().toString();
            mConfirmPassword = mConfirmPasswordEditView.getText().toString();
            if (mIdentifyingCode.equals("")){
                Snackbar.make(mPhoneEdieView, R.string.password_edit_identifying_code_not_empty, Snackbar.LENGTH_LONG).show();
                return;
            } else if (mPassword.equals("")) {
                Snackbar.make(mPhoneEdieView, R.string.password_edit_password_not_empty, Snackbar.LENGTH_LONG).show();
                return;
            } else if (!mPassword.equals(mConfirmPassword)) {
                Snackbar.make(mPhoneEdieView, R.string.password_edit_password_not_equal, Snackbar.LENGTH_LONG).show();
                return;
            } else {
                // TODO: 2016/10/12
            }
        }
    };

    private void initView() {
        mTitleView = (TextView) findViewById(R.id.title_view);
        mBackView = (ImageView) findViewById(R.id.back_view);
        mPhoneEdieView = (EditText) findViewById(R.id.edit_phone_view);
        mIdentifyingCodeEditView = (EditText) findViewById(R.id.edit_identifying_code_view);
        mPasswordEditView = (EditText) findViewById(R.id.edit_password_view);
        mConfirmPasswordEditView = (EditText) findViewById(R.id.edit_confirm_password_view);
        mGetIdentifyingCodeBtn = (Button) findViewById(R.id.get_identifying_code_btn);
        mSubmitBtn = (Button) findViewById(R.id.submit_btn);
    }

    private void setViewListener() {
        mBackView.setOnClickListener(mBackViewClickListener);
        mGetIdentifyingCodeBtn.setOnClickListener(mGetIdentifyingCodeBtnClickListener);
        mSubmitBtn.setOnClickListener(mSubmitBtnClickListener);
    }

    private void getExtraData() {
        Intent intent = getIntent();
        mType = intent.getIntExtra(Constant.EXTRA_PASSWORD_EDIT_TYPE, 0);
    }

    private void setView() {
        if (mType == PASSWORD_EDIT_TYPE_RETRIEVE) {
            mTitleView.setText(R.string.password_edit_title_retrieve);
            mSubmitBtn.setText(R.string.password_edit_submit_retrieve);
        } else if (mType == PASSWORD_EDIT_TYPE_REGISTER) {
            mTitleView.setText(R.string.password_edit_title_register);
            mSubmitBtn.setText(R.string.password_edit_submit_register);
        } else if (mType == PASSWORD_EDIT_TYPE_CHANGE) {
            mTitleView.setText(R.string.password_edit_title_change);
            mSubmitBtn.setText(R.string.password_edit_submit_change);
        }
    }

    private class CountDownThread extends Thread {

        @Override
        public void run() {
            while (mRunEnable && getId() == mThreadId) {
                try {
                    mHandler.sendEmptyMessage(1);
                    synchronized(this) {
                        wait(1000);
                    }
                    mCountdownSecond--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    mGetIdentifyingCodeBtn.setText(mCountdownSecond + "s");
                    if (mCountdownSecond == 0) {
                        mGetIdentifyingCodeBtn.setText(R.string.get_identifying_code);
                        mGetIdentifyingCodeBtn.setClickable(true);
                        mGetIdentifyingCodeBtn.setBackgroundResource(R.drawable.blue_btn_selector);
                        mRunEnable = false;
                    }
                    break;
            }
        }
    };

}

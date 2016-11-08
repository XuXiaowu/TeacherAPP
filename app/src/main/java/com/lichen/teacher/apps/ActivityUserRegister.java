//package com.lichen.teacher.apps;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import com.lichen.teacher.R;
//
///**
// * update by xiaowu on 2016/8/20.
// */
//public class ActivityUserRegister extends AppCompatActivity {
//
//    private ImageView mBackView;
//    private EditText mEditPhoneView;
//    private EditText mEditIdentifyingCodeView;
//    private Button mGetIdentifyingCodeBtn;
//    private Button mRegisterBtn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_user_register);
//
//        initView();
//        setViewClickListener();
//    }
//
//    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            finish();
//        }
//    };
//
//    private View.OnClickListener mGetIdentifyingCodeBtnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    };
//
//    private View.OnClickListener mRegisterBtnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//
//        }
//    };
//
//    private void initView() {
//        mBackView = (ImageView) findViewById(R.id.back_view);
//        mEditPhoneView = (EditText) findViewById(R.id.edit_phone_view);
//        mEditIdentifyingCodeView = (EditText) findViewById(R.id.edit_identifying_code_view);
//        mGetIdentifyingCodeBtn = (Button) findViewById(R.id.get_identifying_code_btn);
//        mRegisterBtn = (Button) findViewById(R.id.register_btn);
//    }
//
//    private void setViewClickListener() {
//        mBackView.setOnClickListener(mBackViewClickListener);
//        mGetIdentifyingCodeBtn.setOnClickListener(mGetIdentifyingCodeBtnClickListener);
//        mRegisterBtn.setOnClickListener(mRegisterBtnClickListener);
//    }
//}

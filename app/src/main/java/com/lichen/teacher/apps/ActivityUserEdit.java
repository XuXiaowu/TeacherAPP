package com.lichen.teacher.apps;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lichen.teacher.R;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.view.CircleImageView;

import java.io.File;
import java.util.ArrayList;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;

/**
 * update by xiaowu on 2016/8/20.
 */
public class ActivityUserEdit extends AppCompatActivity {

    private static final String TAG = "ActivityUserEdit";

    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;

    private ImageView mBackView;
    private RelativeLayout mUserHeadContainView;
    private RelativeLayout mChangePasswordView;
    private CircleImageView mUserHeadView;
    private RadioButton mSexManRadioBtn;
    private RadioButton mSexWomanRadioBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_view);

        initView();
        setViewClickListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            ArrayList<String> image = BGAPhotoPickerActivity.getSelectedImages(data);
            Log.e(TAG, "EEEE");
            Glide.with(ActivityUserEdit.this)
                    .load(image.get(0))
                    .placeholder(R.drawable.user_default_head)
                    .listener(mRequestListener)
                    .into(mUserHeadView);
        }
    }

    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };


    private View.OnClickListener mUserHeadViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            File downloadDir = new File(Environment.getExternalStorageDirectory(), "LichenTeacher/image");
            startActivityForResult(BGAPhotoPickerActivity.newIntent(ActivityUserEdit.this, downloadDir, 1,
                    null), REQUEST_CODE_CHOOSE_PHOTO);
        }
    };

    private View.OnClickListener mChangePasswordViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ActivityUserEdit.this, ActivityAccountSet.class);
            intent.putExtra(Constant.EXTRA_PASSWORD_EDIT_TYPE, ActivityAccountSet.PASSWORD_EDIT_TYPE_CHANGE);
            startActivity(intent);
        }
    };

    private CompoundButton.OnCheckedChangeListener mSexManRadioBtnOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                mSexWomanRadioBtn.setChecked(false);
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener mSexWomanRadioBtnOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
           if (isChecked){
               mSexManRadioBtn.setChecked(false);
           }
        }
    };


    private RequestListener mRequestListener = new RequestListener() {
        @Override
        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
            mUserHeadView.setImageDrawable((GlideBitmapDrawable)resource);
            return false;
        }
    };

    private void initView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
        mUserHeadContainView = (RelativeLayout) findViewById(R.id.head_contain_view);
        mChangePasswordView = (RelativeLayout) findViewById(R.id.change_password_contain_view);
        mUserHeadView = (CircleImageView) findViewById(R.id.user_head_view);
        mSexManRadioBtn = (RadioButton) findViewById(R.id.sex_man_radio_btn);
        mSexWomanRadioBtn = (RadioButton) findViewById(R.id.sex_woman_radio_btn);
    }

    private void setViewClickListener() {
        mBackView.setOnClickListener(mBackViewClickListener);
        mUserHeadContainView.setOnClickListener(mUserHeadViewClickListener);
        mChangePasswordView.setOnClickListener(mChangePasswordViewClickListener);
        mSexManRadioBtn.setOnCheckedChangeListener(mSexManRadioBtnOnCheckedChangeListener);
        mSexWomanRadioBtn.setOnCheckedChangeListener(mSexWomanRadioBtnOnCheckedChangeListener);
    }
}

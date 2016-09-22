package com.lichen.teacher.apps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lichen.teacher.R;
import com.lichen.teacher.view.CircleImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;

/**
 * update by xiaowu on 2016/8/20.
 */
public class ActivityUserEdit extends AppCompatActivity {

    private static final String TAG = "ActivityUserEdit";

    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;

    private ImageView mBackView;
    private RelativeLayout mUserHeadContainView;
    private RelativeLayout mSexContainView;
    private RelativeLayout mChangePassworView;
    private CircleImageView mUserHeadView;
    private TextView mSexView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit);

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

    private View.OnClickListener mUserHeadViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            File downloadDir = new File(Environment.getExternalStorageDirectory(), "LichenTeacher/image");
            startActivityForResult(BGAPhotoPickerActivity.newIntent(ActivityUserEdit.this, downloadDir, 1,
                    null), REQUEST_CODE_CHOOSE_PHOTO);
        }
    };

    private View.OnClickListener mSexContainViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityUserEdit.this);
            builder.setItems(R.array.user_edit_sex_menu_item, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.e(TAG, "EEE");
                    if (which == 0) mSexView.setText(R.string.auth_sex_man);
                    else mSexView.setText(R.string.auth_sex_woman);
                }
            });
            builder.show();
        }
    };

    private View.OnClickListener mChangePasswordViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(ActivityUserEdit.this, ActivityChangePassword.class);
            startActivity(intent);
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
        mSexContainView = (RelativeLayout) findViewById(R.id.sex_contain_view);
        mChangePassworView = (RelativeLayout) findViewById(R.id.change_password_contain_view);
        mUserHeadView = (CircleImageView) findViewById(R.id.user_head_view);
        mSexView = (TextView) findViewById(R.id.sex_view);
    }

    private void setViewClickListener() {
        mUserHeadContainView.setOnClickListener(mUserHeadViewClickListener);
        mSexContainView.setOnClickListener(mSexContainViewClickListener);
        mChangePassworView.setOnClickListener(mChangePasswordViewClickListener);
    }
}

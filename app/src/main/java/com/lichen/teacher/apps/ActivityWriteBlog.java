package com.lichen.teacher.apps;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lichen.teacher.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

public class ActivityWriteBlog extends AppCompatActivity {

    private static final String TAG = "ActivityWriteBlog";

    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;
    private static final int MAX_PHOTO_COUNT = 9;

    private BGASortableNinePhotoLayout mAddPhotosView;
    private TextView mSendView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_blog);

        initView();
        setViewListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            mAddPhotosView.setData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {
            mAddPhotosView.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }

    private BGASortableNinePhotoLayout.Delegate mDelegate = new BGASortableNinePhotoLayout.Delegate() {
        @Override
        public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
            File downloadDir = new File(Environment.getExternalStorageDirectory(), "LichenTeacher/image");
            startActivityForResult(BGAPhotoPickerActivity.newIntent(ActivityWriteBlog.this, downloadDir, MAX_PHOTO_COUNT,
                    mAddPhotosView.getData()), REQUEST_CODE_CHOOSE_PHOTO);
        }

        @Override
        public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
            mAddPhotosView.removeItem(position);
        }

        @Override
        public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
            startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(ActivityWriteBlog.this, MAX_PHOTO_COUNT,
                    models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);

        }
    };

    private View.OnClickListener mSendViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List list = mAddPhotosView.getData();
            Log.e(TAG, list.toString());
        }
    };

    private void initView() {
        mAddPhotosView = (BGASortableNinePhotoLayout) findViewById(R.id.add_photos_view);
        mAddPhotosView.init(this);
        mSendView =(TextView) findViewById(R.id.send_view);
        mSendView.setOnClickListener(mSendViewClickListener);
    }

    private void setViewListener() {
        mAddPhotosView.setDelegate(mDelegate);
    }

//    private void choicePhotoWrapper() {
//            startActivityForResult(BGAPhotoPickerActivity.newIntent(this, null, MAX_PHOTO_COUNT, mAddPhotosView.getData()), REQUEST_CODE_CHOOSE_PHOTO);
//    }
}

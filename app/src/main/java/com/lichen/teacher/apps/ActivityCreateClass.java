//package com.lichen.teacher.apps;
//
//import android.content.Intent;
//import android.os.Environment;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.lichen.teacher.R;
//
//import java.io.File;
//import java.util.ArrayList;
//
//import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
//import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
//import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
//
///**
// * Created by xiaowu on 2016/9/21.
// */
//public class ActivityCreateClass extends AppCompatActivity {
//
//    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
//    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;
//
//    private ImageView mBackView;
//    private BGASortableNinePhotoLayout mAddPhotoView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_create_class_view);
//
//        initView();
//        setViewListener();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
//            mAddPhotoView.setData(BGAPhotoPickerActivity.getSelectedImages(data));
//        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {
//            mAddPhotoView.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
//        }
//    }
//
//    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            onBackPressed();
//        }
//    };
//
//    private BGASortableNinePhotoLayout.Delegate mDelegate = new BGASortableNinePhotoLayout.Delegate() {
//        @Override
//        public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
//            File downloadDir = new File(Environment.getExternalStorageDirectory(), "LichenTeacher/image");
//            startActivityForResult(BGAPhotoPickerActivity.newIntent(ActivityCreateClass.this, downloadDir, 1,
//                    mAddPhotoView.getData()), REQUEST_CODE_CHOOSE_PHOTO);
//        }
//
//        @Override
//        public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
//            mAddPhotoView.removeItem(position);
//        }
//
//        @Override
//        public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
//            startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(ActivityCreateClass.this, 1,
//                    models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);
//        }
//    };
//
//    private void initView() {
//        mBackView = (ImageView) findViewById(R.id.back_view);
//        mAddPhotoView = (BGASortableNinePhotoLayout) findViewById(R.id.add_photo_view);
//        mAddPhotoView.init(this);
//    }
//
//    private void setViewListener() {
//        mBackView.setOnClickListener(mBackViewClickListener);
//        mAddPhotoView.setDelegate(mDelegate);
//    }
//}

package com.lichen.teacher.apps;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.lichen.teacher.R;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.models.LiveParcelable;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

/**
 * Created by xiaowu on 2016//21.
 */
public class ActivityLiveRequest extends AppCompatActivity {

    private static final String TAG = "ActivityLiveRequest";

    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;

    private ImageView mBackView;
    private TextView mTitleView;
    private TextView mRightBtn;
    private TextView mDateView;
    private TextView mStartTimeView;
    private TextView mEndTimeView;
    private BGASortableNinePhotoLayout mAddPhotoView;
    private EditText mTitleEditView;
    private EditText mPriceEditView;
    private EditText mTypeEditView;
    private EditText mSummaryEditView;

    private int mStartHour;
    private int mStartMinute;
    private int mEndHour;
    private int mEndMinute;

    private LiveParcelable mLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_request_view);

        initView();
        setViewListener();
        getExtraData();
        setExtraData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            mAddPhotoView.setData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {
            mAddPhotoView.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }

    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private View.OnClickListener mCreateViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List list = mAddPhotoView.getData();
            Log.e(TAG, list.toString());
            if (!checkDateIsRight()) Snackbar.make(mBackView, R.string.live_request_date_error_hint, Snackbar.LENGTH_SHORT).show();

        }
    };

    private View.OnClickListener mDateViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDatePickerAlertDialog();
        }
    };

    private View.OnClickListener mStartTimeViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showTimePickerDialog(mStartTimeSetListener);
        }
    };

    private View.OnClickListener mEndTimeViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showTimePickerDialog(mEndTimeSetListener);
        }
    };

    private DatePickerDialog.OnDateSetListener mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mDateView.setText(year + "-" + monthOfYear + "-" + dayOfMonth);
        }
    };

    private TimePickerDialog.OnTimeSetListener mStartTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mStartHour = hourOfDay;
            mStartMinute = minute;
            String minuteStr = "";
            if (minute < 10) minuteStr = minuteStr + "0" + minute;
            else minuteStr = minuteStr + minute;
            mStartTimeView.setText(hourOfDay + ":" + minuteStr);
        }
    };

    private TimePickerDialog.OnTimeSetListener mEndTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mEndHour = hourOfDay;
            mEndMinute = minute;
            String minuteStr = "";
            if (minute < 10) minuteStr = minuteStr + "0" + minute;
            else minuteStr = minuteStr + minute;
            mEndTimeView.setText(hourOfDay + ":" + minuteStr);
        }
    };

    private BGASortableNinePhotoLayout.Delegate mDelegate = new BGASortableNinePhotoLayout.Delegate() {
        @Override
        public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
            File downloadDir = new File(Environment.getExternalStorageDirectory(), "LichenTeacher/image");
            startActivityForResult(BGAPhotoPickerActivity.newIntent(ActivityLiveRequest.this, downloadDir, 1,
                    mAddPhotoView.getData()), REQUEST_CODE_CHOOSE_PHOTO);
        }

        @Override
        public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
            mAddPhotoView.removeItem(position);
        }

        @Override
        public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
            startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(ActivityLiveRequest.this, 1,
                    models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);
        }
    };

    private void initView() {
        mBackView = (ImageView) findViewById(R.id.back_view);
        mTitleView = (TextView) findViewById(R.id.activity_title_view);
        mRightBtn = (TextView) findViewById(R.id.right_btn);
        mDateView = (TextView) findViewById(R.id.date_view);
        mStartTimeView = (TextView) findViewById(R.id.start_time_view);
        mEndTimeView = (TextView) findViewById(R.id.end_time_view);
        mTitleEditView = (EditText) findViewById(R.id.edit_title_view);
        mPriceEditView = (EditText) findViewById(R.id.edit_price_view);
        mTypeEditView = (EditText) findViewById(R.id.edit_type_view);
        mSummaryEditView = (EditText) findViewById(R.id.edit_summary_view) ;
        mAddPhotoView = (BGASortableNinePhotoLayout) findViewById(R.id.add_photo_view);
        mAddPhotoView.init(this);
    }

    private void setViewListener() {
        mBackView.setOnClickListener(mBackViewClickListener);
        mRightBtn.setOnClickListener(mCreateViewClickListener);
        mDateView.setOnClickListener(mDateViewClickListener);
        mStartTimeView.setOnClickListener(mStartTimeViewClickListener);
        mEndTimeView.setOnClickListener(mEndTimeViewClickListener);
        mAddPhotoView.setDelegate(mDelegate);
    }

    private void showDatePickerAlertDialog() {
        Calendar cal= Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, mOnDateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showTimePickerDialog(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        Calendar cal= Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private boolean checkDateIsRight() {
        if (mStartHour > mEndHour) return false;
        else {
            if (mStartMinute >= mEndMinute) return false;
            else return true;
        }
    }

    private void getExtraData() {
        Intent intent = getIntent();
        mLive = intent.getParcelableExtra(Constant.EXTRA_LIVE_INFO);
    }

    private void setExtraData() {
        if (mLive != null) {
            mTitleView.setText(R.string.live_request_edit_title);
            mRightBtn.setText(R.string.confirm);
            mTitleEditView.setText(mLive.getTitle());
            mDateView.setText(mLive.getDate());
            mStartTimeView.setText(mLive.getStartTime());
            mEndTimeView.setText(mLive.getEndTime());
            mTypeEditView.setText(mLive.getType());
            mPriceEditView.setText(String.valueOf(mLive.getPrice()));
            mSummaryEditView.setText(mLive.getSummary());
            ArrayList list = new ArrayList();
            list.add(mLive.getImageUrl());
            mAddPhotoView.setData(list);
        }
    }


}

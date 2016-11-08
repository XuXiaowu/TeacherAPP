package com.lichen.teacher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lichen.teacher.R;
import com.lichen.teacher.models.LiveResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by xiaowu on 2016/10/4.
 */
public class LiveAdapter extends ListBaseAdapter<LiveResult> {

    private LayoutInflater mLayoutInflater;
    private View.OnClickListener mItemOnClickListener;
    private View.OnClickListener mStatusBtnClickListener;
    boolean mStatusBtnIsAvailable;

    public LiveAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_live_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LiveResult lr = mDataList.get(position);
        ViewHolder vh = (ViewHolder) holder;
        vh.mTitleView.setText(lr.title);
        vh.mWatchCountView.setText(String.valueOf(lr.watchCount));
        vh.mDateView.setText(lr.date + " " + lr.startTime);
        vh.itemView.setTag(lr);
        vh.itemView.setOnClickListener(mItemOnClickListener);
        vh.mStatusBtn.setTag(lr);
        vh.mStatusBtn.setOnClickListener(mStatusBtnClickListener);
        setCover(lr.imageUrl, vh.mCoverView);
        setCountDown(lr, vh);
        setStatusBtn(mStatusBtnIsAvailable, vh.mStatusBtn);

    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private View mItemView;
        private ImageView mCoverView;
        private TextView mTitleView;
        private TextView mWatchCountView;
        private TextView mDateView;
        private TextView mStatusBtn;
        private TextView mCountDownView;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mCoverView = (ImageView) itemView.findViewById(R.id.cover_view);
            mTitleView = (TextView) itemView.findViewById(R.id.activity_title_view);
            mWatchCountView = (TextView) itemView.findViewById(R.id.watch_count_view);
            mDateView = (TextView) itemView.findViewById(R.id.date_view);
            mStatusBtn = (TextView) itemView.findViewById(R.id.status_btn);
            mCountDownView = (TextView) itemView.findViewById(R.id.count_down_view);
        }
    }

    private void setStatusBtn(boolean isAvailable, TextView view) {
        if (isAvailable) {
            view.setText(R.string.live_my_start);
            view.setBackgroundResource(R.drawable.blue_right_angle_btn_selector);
            view.setClickable(true);
        } else {
            view.setText(R.string.live_my_not_start);
            view.setBackgroundResource(R.color.color_primary_gary_dark);
            view.setClickable(false);
        }
    }

    private void setCover(String imageUrl, ImageView view) {
        Glide.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.cover_default)
//                .listener(mRequestListener)
                .into(view);
    }

    private void setCountDown(LiveResult lr, ViewHolder vh) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("mm-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        long currentTime = System.currentTimeMillis();
        String currentDateStr[] = dateFormat.format(currentTime).split("-");
        String currentTimeStr[] = timeFormat.format(currentTime).split(":");
        String startDateStr[] = lr.date.split("-");
        String startTimeStr[] = lr.startTime.split(":");

        int currentDay = Integer.valueOf(currentDateStr[1]);
        int startDay = Integer.valueOf(startDateStr[2]);
        int countDownDay = startDay - currentDay;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTime);
        int apm = calendar.get(Calendar.AM_PM);
        if (countDownDay == 0) {
            int currentHour = Integer.valueOf(currentTimeStr[0]);
            int currentMinute = Integer.valueOf(currentTimeStr[1]);
            int currentSecond = Integer.valueOf(currentTimeStr[2]);
            int startHour = Integer.valueOf(startTimeStr[0]);
            int startMinute = Integer.valueOf(startTimeStr[1]);
            int startSecond = Integer.valueOf(startTimeStr[2]);
            if (apm == 1 && currentHour <=12) currentHour = currentHour + 12;
            int countDownHour = startHour - currentHour;
            int countDownMinute = startMinute - currentMinute;
            int countDownSecond = startSecond - currentSecond;
            if (countDownMinute < 0) {
                countDownMinute = (60 - currentMinute) + startMinute;
                countDownHour = currentHour - 1;
            }
            String countDownMinuteStr = "";
            String countDownSecondStr = "";
            if (countDownMinute < 10) countDownMinuteStr = "0" + countDownMinute;
            else countDownMinuteStr = String.valueOf(countDownMinute);
            if (countDownSecond == 60) countDownSecond  = 0;
            if (countDownSecond < 10) countDownSecondStr = "0" + countDownSecond;
            else countDownSecondStr = String.valueOf(countDownSecond);
            vh.mCountDownView.setText(String.format(mContext.getString(R.string.live_my_count_down_time), countDownHour + ":" + countDownMinuteStr + ":" +countDownSecondStr));
            mStatusBtnIsAvailable = countDownMinute <= 30 ? true : false;
        } else if (countDownDay > 0){
            vh.mCountDownView.setText(String.format(mContext.getString(R.string.live_my_count_down_date), String.valueOf(countDownDay)));
            mStatusBtnIsAvailable = false;
        } else {
            vh.mCountDownView.setText(R.string.live_my_count_down_doing);
            mStatusBtnIsAvailable = true;
        }
    }

    public void setItemOnClickListener(View.OnClickListener onClickListener) {
        mItemOnClickListener = onClickListener;
    }

    public void setStatusBtnClickListener(View.OnClickListener onClickListener) {
        mStatusBtnClickListener = onClickListener;
    }

}

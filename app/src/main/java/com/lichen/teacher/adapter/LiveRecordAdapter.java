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
 * Created by xiaowu on 2016/10/5.
 */
public class LiveRecordAdapter extends ListBaseAdapter<LiveResult> {

    private LayoutInflater mLayoutInflater;
    private View.OnClickListener mItemOnClickListener;

    public LiveRecordAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_live_record_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LiveResult lr = mDataList.get(position);
        ViewHolder vh = (ViewHolder) holder;
        vh.mTitleView.setText(lr.title);
        vh.mWatchCountView.setText(String.valueOf(lr.watchCount));
        vh.mDateView.setText(lr.date + " " + lr.startTime);
        vh.mItemView.setTag(lr);
        vh.mItemView.setOnClickListener(mItemOnClickListener);
        setCover(lr.imageUrl, vh.mCoverView);

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
        }
    }

    private void setCover(String imageUrl, ImageView view) {
        Glide.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.cover_default)
//                .listener(mRequestListener)
                .into(view);
    }

    public void setItemOnClickListener(View.OnClickListener onClickListener) {
        mItemOnClickListener = onClickListener;
    }

}

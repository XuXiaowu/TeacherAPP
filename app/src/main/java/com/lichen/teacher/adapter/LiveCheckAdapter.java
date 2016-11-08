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

/**
 * Created by xiaowu on 2016/9/21.
 */
public class LiveCheckAdapter extends ListBaseAdapter<LiveResult> {

    private LayoutInflater mLayoutInflater;
    private View.OnClickListener mItemOnClickListener;

    public LiveCheckAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_live_check_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LiveResult cpr = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mTitleView.setText(cpr.title);
        viewHolder.mPriceView.setText("¥" + cpr.price);
        viewHolder.mDateView.setText(cpr.date + " " + cpr.startTime + "-" + cpr.endTime);
        viewHolder.mItemView.setTag(cpr);
        viewHolder.mItemView.setOnClickListener(mItemOnClickListener);
        setCover(cpr.imageUrl, viewHolder.mCoverView);
        setStatus(cpr.status, viewHolder.mStatusView);

    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private View mItemView;
        private ImageView mCoverView;
        private TextView mTitleView;
        private TextView mPriceView;
        private TextView mDateView;
        private TextView mStatusView;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mCoverView = (ImageView) itemView.findViewById(R.id.cover_view);
            mTitleView = (TextView) itemView.findViewById(R.id.activity_title_view);
            mPriceView = (TextView) itemView.findViewById(R.id.price_edit_view);
            mDateView = (TextView) itemView.findViewById(R.id.date_view);
            mStatusView = (TextView) itemView.findViewById(R.id.status_view);
        }
    }

    private void setStatus(int status, TextView view) {
        if (status == 0) {
            view.setText(R.string.live_check_status_wait);
            view.setTextColor(mContext.getResources().getColor(R.color.color_green));
        } else {
            view.setText(R.string.live_check_not_pass);
            view.setTextColor(mContext.getResources().getColor(R.color.color_red));
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

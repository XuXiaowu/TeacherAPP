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
import com.lichen.teacher.models.ChatResult;
import com.lichen.teacher.models.LiveResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by xiaowu on 2016/10/5.
 */
public class ChatAdapter extends ListBaseAdapter<ChatResult> {

    private LayoutInflater mLayoutInflater;
    private View.OnClickListener mItemOnClickListener;
    boolean mStatusBtnIsAvailable;

    public ChatAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_chat_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatResult cr = mDataList.get(position);
        ViewHolder vh = (ViewHolder) holder;
        vh.mTitleView.setText(cr.title);
        vh.mSummaryView.setText(cr.summary);
        vh.mItemView.setOnClickListener(mItemOnClickListener);
        setCover(cr.imagerUrl, vh.mCoverView);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private View mItemView;
        private ImageView mCoverView;
        private TextView mTitleView;
        private TextView mSummaryView;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mCoverView = (ImageView) itemView.findViewById(R.id.cover_view);
            mTitleView = (TextView) itemView.findViewById(R.id.title_view);
            mSummaryView = (TextView) itemView.findViewById(R.id.summary_view);
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

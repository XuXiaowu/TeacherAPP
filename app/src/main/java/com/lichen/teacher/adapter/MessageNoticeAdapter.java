package com.lichen.teacher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichen.teacher.R;
import com.lichen.teacher.models.MessageNoticeInfo;

/**
 * Created by xiaowu on 2016/9/21.
 */
public class MessageNoticeAdapter extends ListBaseAdapter<MessageNoticeInfo> {

    private LayoutInflater mLayoutInflater;

    public MessageNoticeAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_message_notice_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageNoticeInfo mni = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mTitleView.setText(mni.getTitle());
        viewHolder.mDateView.setText(mni.getDate());
        viewHolder.mContentView.setText(mni.getContent());
        setNewMarkView(mni.isNew(), viewHolder.mNewMarkView);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mNewMarkView;
        private TextView mTitleView;
        private TextView mDateView;
        private TextView mContentView;

        public ViewHolder(View itemView) {
            super(itemView);
            mNewMarkView = (ImageView) itemView.findViewById(R.id.new_mark_view);
            mTitleView = (TextView) itemView.findViewById(R.id.activity_title_view);
            mDateView = (TextView) itemView.findViewById(R.id.date_view);
            mContentView = (TextView) itemView.findViewById(R.id.content_view);
        }
    }

    private void setNewMarkView(boolean isNew, ImageView view) {
        if (isNew) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }

}

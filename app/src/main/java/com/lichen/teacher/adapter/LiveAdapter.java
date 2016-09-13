package com.lichen.teacher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichen.teacher.R;

/**
 * Created by xiaowu on 2016/8/4.
 */
public class LiveAdapter extends ListBaseAdapter {

    private LayoutInflater mLayoutInflater;

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
//        TopicListResult.Topic item = (TopicListResult.Topic) mDataList.get(position);
//
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mCoverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mCoverView;
        private TextView mUserNameView;

        public ViewHolder(View itemView) {
            super(itemView);
            mCoverView = (ImageView) itemView.findViewById(R.id.cover_view);
            mUserNameView = (TextView) itemView.findViewById(R.id.user_name_view);
        }
    }

}

package com.lichen.teacher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lichen.teacher.R;

/**
 * Created by xiaowu on 2016/8/4.
 */
public class HomeAdapter extends ListBaseAdapter {

    private LayoutInflater mLayoutInflater;

    public HomeAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_home_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        TopicListResult.Topic item = (TopicListResult.Topic) mDataList.get(position);
//
//        ViewHolder viewHolder = (ViewHolder) holder;
//        viewHolder.mUserNameView.setText(item.userName);
//        viewHolder.mContentView.setText(item.content);
    }


    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mUserNameView;
        private TextView mContentView;

        public ViewHolder(View itemView) {
            super(itemView);
            mUserNameView = (TextView) itemView.findViewById(R.id.user_name_view);
//            mContentView = (TextView) itemView.findViewById(R.id.content_view);
        }
    }

}

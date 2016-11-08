package com.lichen.teacher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichen.teacher.R;
import com.lichen.teacher.models.ChatResult;

/**
 * Created by xiaowu on 2016/10/13.
 */
public class ProfitAdapter extends ListBaseAdapter {

    private LayoutInflater mLayoutInflater;
    private View.OnClickListener mItemOnClickListener;

    public ProfitAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_profit_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;
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


    public void setItemOnClickListener(View.OnClickListener onClickListener) {
        mItemOnClickListener = onClickListener;
    }

}

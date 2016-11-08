package com.lichen.teacher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lichen.teacher.R;
import com.lichen.teacher.models.LiveChat;

/**
 * Created by xiaowu on 2016/8/4.
 */
public class LiveChatAdapter extends ListBaseAdapter<LiveChat> {

    private LayoutInflater mLayoutInflater;
    private boolean mIsFullScreenStatus;

    public LiveChatAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_live_chat_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LiveChat lc = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mUserNameView.setText(lc.userName);
        viewHolder.mTimeView.setText(lc.time);
        viewHolder.mContentView.setText(Html.fromHtml(lc.content));
        setUserHead(lc.isTeacher, viewHolder.mHeadView);
        setViewStatus(viewHolder);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private View mItemView;
        private View mVerticalLineView;
        private ImageView mHeadView;
        private TextView mUserNameView;
        private TextView mTimeView;
        private TextView mContentView;
        private View mEmptyView;
        private View mBgView;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mVerticalLineView = itemView.findViewById(R.id.vertical_line_view);
            mHeadView = (ImageView) itemView.findViewById(R.id.user_head_view);
            mUserNameView = (TextView) itemView.findViewById(R.id.user_name_view);
            mTimeView = (TextView) itemView.findViewById(R.id.time_view);
            mContentView = (TextView) itemView.findViewById(R.id.chat_content_view);
            mEmptyView = itemView.findViewById(R.id.empty_view);
            mBgView = itemView.findViewById(R.id.bg_view);
        }
    }

    private void setUserHead(boolean isTeacher, ImageView view) {
        if (isTeacher) view.setImageResource(R.drawable.live_chat_teacher_head_icon);
        else view.setImageResource(R.drawable.live_chat_student_head_icon);
    }

    private void setViewStatus(ViewHolder viewHolder) {
        if (mIsFullScreenStatus) {
            viewHolder.mVerticalLineView.setVisibility(View.GONE);
            viewHolder.mBgView.setBackgroundResource(R.color.color_transparent_gary1);
            viewHolder.mUserNameView.setTextColor(mContext.getResources().getColor(R.color.color_white));
            viewHolder.mContentView.setTextColor(mContext.getResources().getColor(R.color.color_white));
            viewHolder.mTimeView.setTextColor(mContext.getResources().getColor(R.color.color_white));

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.mEmptyView.getLayoutParams();
            layoutParams.height = mContext.getResources().getDimensionPixelSize(R.dimen.one_design_view_size);
            viewHolder.mEmptyView.setLayoutParams(layoutParams);

        } else {
            viewHolder.mVerticalLineView.setVisibility(View.VISIBLE);
            viewHolder.mBgView.setBackgroundResource(R.color.color_transparent);
            viewHolder.mUserNameView.setTextColor(mContext.getResources().getColor(R.color.color_text2));
            viewHolder.mContentView.setTextColor(mContext.getResources().getColor(R.color.color_text2));
            viewHolder.mTimeView.setTextColor(mContext.getResources().getColor(R.color.color_text3));

            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) viewHolder.mEmptyView.getLayoutParams();
            layoutParams.height = 0;
            viewHolder.mEmptyView.setLayoutParams(layoutParams);
        }
    }

    public void setFullScreenStatus(boolean isFullScreenStatus) {
        mIsFullScreenStatus = isFullScreenStatus;
        notifyDataSetChanged();
    }

}

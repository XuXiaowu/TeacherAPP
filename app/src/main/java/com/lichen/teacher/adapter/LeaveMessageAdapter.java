package com.lichen.teacher.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lichen.teacher.R;
import com.lichen.teacher.models.LeaveMessageResult;
import com.lichen.teacher.view.CircleImageView;
import com.lichen.teacher.view.ShrinkTextView;

/**
 * Created by xiaowu on 2016/8/4.
 */
public class LeaveMessageAdapter extends ListBaseAdapter<LeaveMessageResult> {

    private LayoutInflater mLayoutInflater;
    private View.OnClickListener mReplayClickListener;
    private View.OnClickListener mReplayModifyClickListener;
    private View.OnClickListener mReplayDeleteClickListener;
    private View.OnClickListener mLikeContainClickListener;


    public LeaveMessageAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_leave_message_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        LeaveMessageResult lmr = mDataList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
//        viewHolder.mUserNameView.setText(item.userName);
        viewHolder.mContentView.setContent(mDataList, position);
        viewHolder.mReplayStatusBtn.setTag(position);
        viewHolder.mReplayStatusBtn.setOnClickListener(mReplayClickListener);
        viewHolder.mReplayDeleteBtn.setTag(position);
        viewHolder.mReplayDeleteBtn.setOnClickListener(mReplayDeleteClickListener);
        viewHolder.mReplayModifyBtn.setTag(position);
        viewHolder.mReplayModifyBtn.setOnClickListener(mReplayModifyClickListener);
        viewHolder.mLikeContainView.setTag(position);
        viewHolder.mLikeContainView.setOnClickListener(mLikeContainClickListener);
        viewHolder.mLikeCountView.setText(String.valueOf(lmr.likeCount));
        setLikeStatus(lmr.isLike, viewHolder.mLikeStatusView);
        setReplayView(viewHolder, lmr);
        setItemViewMargins(viewHolder, position);

    }


    private void setReplayView(ViewHolder viewHolder, LeaveMessageResult lmr) {
        if (lmr.isReplay) {
            viewHolder.mReplayContainView.setVisibility(View.VISIBLE);
            viewHolder.mReplayContentView.setText(lmr.replayContent);
            viewHolder.mReplayStatusBtn.setTextColor(mContext.getResources().getColor(R.color.color_text3));
            viewHolder.mReplayStatusBtn.setClickable(false);
        } else {
            viewHolder.mReplayContainView.setVisibility(View.GONE);
            viewHolder.mReplayStatusBtn.setTextColor(mContext.getResources().getColor(R.color.color_primary));
            viewHolder.mReplayStatusBtn.setClickable(true);
        }
    }

    private void setLikeStatus(boolean isLike, ImageView view) {
        if (isLike) view.setImageResource(R.drawable.like_red_icon);
         else view.setImageResource(R.drawable.like_gray_icon);
    }

    private void setItemViewMargins(ViewHolder viewHolder, int position) {
        RecyclerView.LayoutParams layoutParams= (RecyclerView.LayoutParams) viewHolder.mItemView.getLayoutParams();
        if (position == getItemCount() - 1) {
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, layoutParams.leftMargin);
        } else {
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin, layoutParams.rightMargin, 0);
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private CardView mItemView;
        private CircleImageView mUserHeadView;
        private TextView mUserNameView;
        private TextView mDateView;
        private RelativeLayout mLikeContainView;
        private ImageView mLikeStatusView;
        private TextView mLikeCountView;
        private TextView mReplayStatusBtn;
        private ShrinkTextView mContentView;
        private RelativeLayout mReplayContainView;
        private TextView mReplayContentView;
        private TextView mReplayModifyBtn;
        private TextView mReplayDeleteBtn;


        public ViewHolder(View itemView) {
            super(itemView);
            mItemView = (CardView) itemView;
            mUserNameView = (TextView) itemView.findViewById(R.id.user_name_view);
            mContentView = (ShrinkTextView) itemView.findViewById(R.id.content_view);
            mLikeContainView = (RelativeLayout) itemView.findViewById(R.id.like_contain_view);
            mLikeCountView = (TextView) itemView.findViewById(R.id.like_count_view);
            mLikeStatusView = (ImageView) itemView.findViewById(R.id.like_status_view);
            mReplayContainView = (RelativeLayout) itemView.findViewById(R.id.replay_contain_view);
            mReplayContentView = (TextView) itemView.findViewById(R.id.replay_content_view);
            mReplayStatusBtn = (TextView) itemView.findViewById(R.id.replay_status_view);
            mReplayModifyBtn = (TextView) itemView.findViewById(R.id.replay_modify_view);
            mReplayDeleteBtn = (TextView) itemView.findViewById(R.id.replay_delete_view);
        }
    }

    public void setRelayBtnClickListener(View.OnClickListener onClickListener) {
        mReplayClickListener = onClickListener;
    }

    public void setModifyBtnClickListener(View.OnClickListener onClickListener) {
        mReplayModifyClickListener = onClickListener;
    }

    public void setDeleteBtnClickListener(View.OnClickListener onClickListener) {
        mReplayDeleteClickListener = onClickListener;
    }

    public void setLikeContainClickListener(View.OnClickListener onClickListener) {
        mLikeContainClickListener = onClickListener;
    }

}

package com.lichen.teacher.apps.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.cundong.recyclerview.util.RecyclerViewUtils;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.ChatAdapter;
import com.lichen.teacher.adapter.LeaveMessageAdapter;
import com.lichen.teacher.models.ChatResult;
import com.lichen.teacher.models.LeaveMessageResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016/10/5.
 */
public class FragmentChatList extends Fragment {

    private View mContentView;
    private LRecyclerView mListView;
    private TextView mLoadingStatusView;
    private LinearLayout mLoadingView;
    private RelativeLayout mLoadingContainView;

    private RelativeLayout mEditContainView;
    private EditText mEditView;
    private TextView mSendBtn;

    private LeaveMessageAdapter mLeaveMessageAdapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<LeaveMessageResult> mDataList;
    private boolean mIsEditContainViewShowed;
    private float mEditContainViewY;
    private int mSelectItemPosition;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_chat_list_view, container, false);
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        initView();
        setViewListener();
        setupListView();
        setTestData();
        setLoadingErrorStatus();
    }

    private View.OnClickListener mLoadingErrorOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mLeaveMessageAdapter.setDataList(mDataList);
            setLoadingNoneStatus();
//            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    };

    private View.OnClickListener mReplayBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showEditContainView();
            mSelectItemPosition = (int) v.getTag();
        }
    };

    private View.OnClickListener mReplayModifyBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showEditContainView();
            mSelectItemPosition = (int) v.getTag();
        }
    };

    private View.OnClickListener mReplayDeleteBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSelectItemPosition = (int) v.getTag();
            mDataList.get(mSelectItemPosition).isReplay = false;
            mLeaveMessageAdapter.notifyItemChanged(mSelectItemPosition);
        }
    };

    private View.OnClickListener mSendBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideEditContainView();
            mDataList.get(mSelectItemPosition).isReplay = true;
            mDataList.get(mSelectItemPosition).replayContent = mEditView.getText().toString();
            mLeaveMessageAdapter.notifyItemChanged(mSelectItemPosition);
        }
    };

    private View.OnClickListener mLikeContainClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mSelectItemPosition = (int) v.getTag();
            if (mDataList.get(mSelectItemPosition).isLike) {
                mDataList.get(mSelectItemPosition).isLike = false;
                mDataList.get(mSelectItemPosition).likeCount--;
            } else {
                mDataList.get(mSelectItemPosition).isLike = true;
                mDataList.get(mSelectItemPosition).likeCount++;
            }
            mLeaveMessageAdapter.notifyItemChanged(mSelectItemPosition);
        }
    };


    private View.OnTouchListener mListViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            hideEditContainView();
            return false;
        }
    };

    private void initView() {
        mListView = (LRecyclerView) mContentView.findViewById(R.id.list_view);
        mLoadingContainView = (RelativeLayout) mContentView.findViewById(R.id.loading_status_contain_view);
        mLoadingStatusView = (TextView) mContentView.findViewById(R.id.loading_status_view);
        mLoadingView = (LinearLayout) mContentView.findViewById(R.id.loading_view);
        mEditContainView = (RelativeLayout) mContentView.findViewById(R.id.edit_contain_view);
        mEditView = (EditText) mContentView.findViewById(R.id.edit_view);
        mSendBtn = (TextView) mContentView.findViewById(R.id.send_btn);
    }

    private void setViewListener() {
        mLoadingContainView.setOnClickListener(mLoadingErrorOnClickListener);
        mListView.setOnTouchListener(mListViewOnTouchListener);
        mSendBtn.setOnClickListener(mSendBtnClickListener);
    }

    private void setupListView() {
        mLeaveMessageAdapter = new LeaveMessageAdapter(getActivity());
        mLeaveMessageAdapter.setRelayBtnClickListener(mReplayBtnClickListener);
        mLeaveMessageAdapter.setDeleteBtnClickListener(mReplayDeleteBtnClickListener);
        mLeaveMessageAdapter.setModifyBtnClickListener(mReplayModifyBtnClickListener);
        mLeaveMessageAdapter.setLikeContainClickListener(mLikeContainClickListener);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(getActivity(), mLeaveMessageAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
//        mLeaveMessageAdapter.setDataList(mDataList);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mListView.setPullRefreshEnabled(false);
//        mListView.setLScrollListener(mLScrollListener);
//        mListView.addOnScrollListener(mOnScrollListener);
    }

    private void setTestData() {
        mDataList = new ArrayList();
        if (mDataList.size() > 0) return;
        for (int i = 0; i < 20; i++) {
            LeaveMessageResult leaveMessageResult = new LeaveMessageResult();
            if (i == 1) {
                leaveMessageResult.content = i + ".分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付";
            } else if (i == 2) {
                leaveMessageResult.content = i + ".分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付"
                + "分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付";
            } else if (i == 3) {
                leaveMessageResult.content = i + ".分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付"
                        + "分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付"
                        + "分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付";
            } else {
                leaveMessageResult.content = i + ".分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付";
            }
//            leaveMessageResult.content = i + ".分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付";

            mDataList.add(leaveMessageResult);
        }
    }

    private void setLoadingNoneStatus() {
        mListView.setVisibility(View.VISIBLE);
        mLoadingContainView.setVisibility(View.GONE);
    }

    private void setLoadingStatus() {
        mListView.setVisibility(View.GONE);
        mLoadingStatusView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
    }

    private void setLoadingErrorStatus() {
        mListView.setVisibility(View.GONE);
        mLoadingContainView.setVisibility(View.VISIBLE);
        mLoadingStatusView.setVisibility(View.VISIBLE);
        mLoadingView.setVisibility(View.GONE);
    }

    private void hideEditContainView() {
        if (mIsEditContainViewShowed) {
            float height = mEditContainView.getHeight();
            ObjectAnimator.ofFloat(mEditContainView, "y", mEditContainViewY, mEditContainViewY + height)
                    .setDuration(300)
                    .start();
            InputMethodManager imm = (InputMethodManager) mEditView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mEditView.getWindowToken(), 0);
            mIsEditContainViewShowed = false;
        }
    }

    private void showEditContainView() {
        if (mEditContainViewY == 0) mEditContainViewY = mEditContainView.getY();
        if (!mIsEditContainViewShowed){
            mEditContainView.setVisibility(View.VISIBLE);
            float y = mEditContainView.getY();
            float height = mEditContainView.getHeight();
            ObjectAnimator animator = ObjectAnimator.ofFloat(mEditContainView, "y", mEditContainViewY + height, mEditContainViewY);
            animator.setDuration(300)
                    .start();
            animator.addListener(mAnimatorListener);
            mIsEditContainViewShowed = true;
        }
    }

    private Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mEditView.requestFocus();
            InputMethodManager imm = (InputMethodManager) mEditView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public void exchangeOrder() {
        List list = new ArrayList<>();
        List dataList = mLeaveMessageAdapter.getDataList();
        for (int i = mDataList.size() - 1; i >= 0; i--) {
            list.add(dataList.get(i));
        }
        dataList.clear();
        dataList.addAll(list);
        mLeaveMessageAdapter.notifyDataSetChanged();
    }


}

package com.lichen.teacher.apps.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.cundong.recyclerview.util.RecyclerViewUtils;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.LeaveMessageAdapter;
import com.lichen.teacher.apps.ActivityLiveCheck;
import com.lichen.teacher.apps.ActivityLiveRecord;
import com.lichen.teacher.apps.ActivityLiveRequest;
import com.lichen.teacher.apps.ActivityMessageNotice;
import com.lichen.teacher.apps.ActivityAuthentication;
import com.lichen.teacher.apps.ActivityProfit;
import com.lichen.teacher.apps.ActivityTabs;
import com.lichen.teacher.database.MessageNoticeProvider;
import com.lichen.teacher.models.LeaveMessageResult;
import com.lichen.teacher.models.MessageNoticeInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016/8/31.
 */
public class FragmentHome extends Fragment {

    private View mContentView;
    private LRecyclerView mListView;
    private View mHeadView;
    private RelativeLayout mLiveRequestView;
    private RelativeLayout mCheckProgressView;
    private RelativeLayout mWaitLiveView;
    private RelativeLayout mLeaveMessageView;
    private RelativeLayout mLiveRecordView;
    private RelativeLayout mMyProfitView;
    private ImageView mUserHeadView;
    private ImageView mMessageNoticeView;
    private ImageView mNewNoticeMarkView;

    private RelativeLayout mEditContainView;
    private EditText mEditView;
    private TextView mSendBtn;

    private RelativeLayout mLeaveMessageContainView;
    private TextView mLeaveMessageStatusView;
    private ProgressBar mLoadingView;

    private LeaveMessageAdapter mHomeAdapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<LeaveMessageResult> mDataList = new ArrayList();
    private int mNotifyItemPosition;

    private boolean mIsEditContainViewShowed;
    private float mEditContainViewY;
    private int mSelectItemPosition;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_home_view, container, false);

        initView();
        setViewListener();
//        setTestData();
        setupListView();
        setViewData();
        updateMessageNoticeNewMark();
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();

//        initView();
//        setViewListener();
////        setTestData();
//        setupListView();
//        setViewData();
//        updateMessageNoticeNewMark();
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.e(TAG,"onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.e(TAG,"onDestroy");
    }

    private View.OnClickListener mFunctionViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            ActivityTabs activityTabs;
            switch (v.getId()) {
                case R.id.live_request_function_view:
                    intent = new Intent();
                    intent.setClass(getActivity(), ActivityLiveRequest.class);
                    startActivity(intent);
                    break;
                case R.id.check_progress_function_view:
                    intent = new Intent();
                    intent.setClass(getActivity(), ActivityLiveCheck.class);
                    startActivity(intent);
                    break;
                case R.id.wait_live_function_view:
                    activityTabs = (ActivityTabs) getActivity();
                    activityTabs.selectPageLive();
//                    intent = new Intent();
//                    intent.setClass(getActivity(), ActivityLiveMy.class);
//                    startActivity(intent);
                    break;
                case R.id.live_leave_message_function_views:
                    activityTabs = (ActivityTabs) getActivity();
                    activityTabs.selectPageChat();
                    break;
                case R.id.live_record_function_view:
                    intent = new Intent();
                    intent.setClass(getActivity(), ActivityLiveRecord.class);
                    startActivity(intent);
                    break;
                case R.id.my_profit_function_view:
                    intent = new Intent();
                    intent.setClass(getActivity(), ActivityProfit.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private View.OnClickListener mAuthBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ActivityAuthentication.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener mLeaveMessageContainViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            setMessageViewLoadingStatus();
            mLeaveMessageContainView.setVisibility(View.GONE);
            setTestData();
            mHomeAdapter.setDataList(mDataList);
//            mHandler.sendEmptyMessageDelayed(1, 500);
        }
    };

    private View.OnClickListener mReplayBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showEditContainView();
            mSelectItemPosition = (int) v.getTag();

            mHomeAdapter.notifyDataSetChanged();
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
            mHomeAdapter.notifyItemChanged(mSelectItemPosition);
        }
    };

    private View.OnClickListener mSendBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            hideEditContainView();
            mDataList.get(mSelectItemPosition).isReplay = true;
            mDataList.get(mSelectItemPosition).replayContent = mEditView.getText().toString();
            mHomeAdapter.notifyItemChanged(mSelectItemPosition);
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
            mHomeAdapter.notifyItemChanged(mSelectItemPosition);
        }
    };


    private View.OnTouchListener mListViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            hideEditContainView();
            return false;
        }
    };

    private View.OnClickListener mMessageNoticeViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ActivityMessageNotice.class);
            startActivity(intent);
        }
    };

    private View.OnClickListener mUserHeadViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ActivityTabs activityTabs = (ActivityTabs) getActivity();
            activityTabs.selectPageMore();
        }
    };

    private RequestListener mRequestListener = new RequestListener() {
        @Override
        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
            mUserHeadView.setImageDrawable((GlideBitmapDrawable)resource);
            return false;
        }
    };

    private void initView() {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        mHeadView = layoutInflater.inflate(R.layout.home_head_view, null);
        mListView = (LRecyclerView) mContentView.findViewById(R.id.list_view);
        mEditContainView = (RelativeLayout) mContentView.findViewById(R.id.edit_contain_view);
        mEditView = (EditText) mContentView.findViewById(R.id.edit_view);
        mSendBtn = (TextView) mContentView.findViewById(R.id.send_btn);

        mLiveRequestView = (RelativeLayout) mHeadView.findViewById(R.id.live_request_function_view);
        mCheckProgressView = (RelativeLayout) mHeadView.findViewById(R.id.check_progress_function_view);
        mWaitLiveView = (RelativeLayout) mHeadView.findViewById(R.id.wait_live_function_view);
        mLeaveMessageView = (RelativeLayout) mHeadView.findViewById(R.id.live_leave_message_function_views);
        mLiveRecordView = (RelativeLayout) mHeadView.findViewById(R.id.live_record_function_view);
        mMyProfitView = (RelativeLayout) mHeadView.findViewById(R.id.my_profit_function_view);
        mUserHeadView = (ImageView) mHeadView.findViewById(R.id.user_head_view);
        mMessageNoticeView = (ImageView) mHeadView.findViewById(R.id.message_notice_view);
        mNewNoticeMarkView = (ImageView) mHeadView.findViewById(R.id.new_icon_view);
        mLeaveMessageContainView = (RelativeLayout) mHeadView.findViewById(R.id.leave_message_status_contain_view);
        mLeaveMessageStatusView = (TextView) mHeadView.findViewById(R.id.leave_message_status_view);
        mLoadingView = (ProgressBar) mHeadView.findViewById(R.id.loading_view);

    }

    private void setViewListener() {
        mLiveRequestView.setOnClickListener(mFunctionViewClickListener);
        mCheckProgressView.setOnClickListener(mFunctionViewClickListener);
        mWaitLiveView.setOnClickListener(mFunctionViewClickListener);
        mLeaveMessageView.setOnClickListener(mFunctionViewClickListener);
        mLiveRequestView.setOnClickListener(mFunctionViewClickListener);
        mLiveRecordView.setOnClickListener(mFunctionViewClickListener);
        mMyProfitView.setOnClickListener(mFunctionViewClickListener);
        mLeaveMessageContainView.setOnClickListener(mLeaveMessageContainViewClickListener);
        mListView.setOnTouchListener(mListViewOnTouchListener);
        mSendBtn.setOnClickListener(mSendBtnClickListener);
        mMessageNoticeView.setOnClickListener(mMessageNoticeViewClickListener);
        mUserHeadView.setOnClickListener(mUserHeadViewClickListener);

    }


    private void setupListView() {
        mHomeAdapter = new LeaveMessageAdapter(getActivity());
        mHomeAdapter.setRelayBtnClickListener(mReplayBtnClickListener);
        mHomeAdapter.setDeleteBtnClickListener(mReplayDeleteBtnClickListener);
        mHomeAdapter.setModifyBtnClickListener(mReplayModifyBtnClickListener);
        mHomeAdapter.setLikeContainClickListener(mLikeContainClickListener);
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(getActivity(), mHomeAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mHomeAdapter.setDataList(mDataList);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mListView.setPullRefreshEnabled(false);
//        mListView.setLScrollListener(mLScrollListener);
//        mListView.addOnScrollListener(mOnScrollListener);
        RecyclerViewUtils.setHeaderView(mListView, mHeadView);
    }

    private void setTestData() {
        if (mDataList.size() > 0) return;
        for (int i = 0; i < 3; i++) {
            LeaveMessageResult leaveMessageResult = new LeaveMessageResult();
            leaveMessageResult.content = i + ".分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付款骂我开发么我们反恐了没我们佛科我么偶方面没我份美味吗我开发么我房门口二维码分二维码分OK么我附魔可我没法可我们佛么我分么我猫科我魔法科目为密封口耳我们反恐么我房门口二维码分可我们佛门为分为非我们佛诶我靠佛门我每位父母房空么哇分忙完没付款骂我开发么我们反恐了没我们佛科我么偶方面没我份美味吗我开发么我房门口二维码分二维码分OK么我附魔可我没法可我们佛么我分么我猫科我魔法科目为密封口耳我们反恐么我房门口二维码分可我们佛门为";
            mDataList.add(leaveMessageResult);
        }
    }

    private void setViewData() {
        Glide.with(getActivity())
                .load("http://a.hiphotos.baidu.com/zhidao/pic/item/f9dcd100baa1cd11aa2ca018bf12c8fcc3ce2d74.jpg")
                .placeholder(R.drawable.user_default_head)
                .listener(mRequestListener)
                .into(mUserHeadView);
    }

    private void setMessageViewLoadingStatus() {
        mLeaveMessageStatusView.setVisibility(View.GONE);
        mLoadingView.setVisibility(View.VISIBLE);
        mLeaveMessageContainView.setClickable(false);
    }

    private void hideMessageStatusView() {
        mLeaveMessageContainView.setVisibility(View.GONE);
    }

    private void hideEditContainView() {
        if (mIsEditContainViewShowed) {
//            float y = mEditContainView.getY();
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


//    public class UpdateMessageNoticeReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            updateMessageNotice();
//        }
//    }

    public void updateMessageNotice() {
        List<MessageNoticeInfo> list = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        for (int i = 0; i < 20; i++) {
            MessageNoticeInfo mni = new MessageNoticeInfo();
            mni.setTitle("测试标题" + i);
            mni.setDate(format.format(System.currentTimeMillis()));
            mni.setContent("测试内容测试内容" + i);
            mni.setNew(true);
            list.add(mni);
        }
        MessageNoticeProvider.saveOrUpdateMessage(list);
        updateMessageNoticeNewMark();
    }

    private void updateMessageNoticeNewMark() {
        boolean hasNewMessage = MessageNoticeProvider.checkHasNewMessage();
        if (hasNewMessage) mNewNoticeMarkView.setVisibility(View.VISIBLE);
        else mNewNoticeMarkView.setVisibility(View.GONE);
    }

}

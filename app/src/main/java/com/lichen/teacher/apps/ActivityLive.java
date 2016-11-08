package com.lichen.teacher.apps;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gensee.callback.IChatCallBack;
import com.gensee.callback.IRoomCallBack;
import com.gensee.callback.IVideoCallBack;
import com.gensee.common.ServiceType;
import com.gensee.entity.InitParam;
import com.gensee.net.RtComp;
import com.gensee.room.RtSdk;
import com.gensee.routine.State;
import com.gensee.routine.UserInfo;
import com.gensee.taskret.OnTaskRet;
import com.gensee.view.GSVideoView;
import com.gensee.view.ILocalVideoView;
import com.gensee.view.LocalVideoViewEx;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.LiveChatAdapter;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.models.LiveChat;
import com.lichen.teacher.models.LiveParcelable;
import com.lichen.teacher.util.ShowUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by xiaowu on 2016/10/2.
 */
public class ActivityLive extends AppCompatActivity {

    private static final String TAG = "ActivityLive";
    private static final boolean DEBUG_ENABLE = true;

    private static final int WHAT_JOIN_ROOM = 0;
    private static final int WHAT_OPEN_LIVE = 1;
    private static final int WHAT_HIDE_LOADING_VIEW = 2;
    private static final int WHAT_UPDATE_USER_NUM = 3;
    private static final int WHAT_END_TIME_COUNTDOWN = 4;

    private static final int LIVE_FINISH_REQUEST_CODE = 1001;

    private RtComp mRtComp;
    private RtSdk mRtSdk;
    private InitParam mInitParam;

    private RelativeLayout mLoadingStatusContainView;
    private TextView mLoadingTextView;
    private LocalVideoViewEx mLocalVideoViewEx;
    private GSVideoView mCastVideoView;
    private RecyclerView mChatListView;
    private EditText mChatEditView;
    private TextView mChatSendBtn;
    private View mActivityRootView;
    private View mTitleBar;
    private ImageView mBackView;
    private ImageView mMenuBtn;
    private RelativeLayout mTopCountdownContainView;
    private TextView mTopCountdownView;
    private RelativeLayout mTopUserNumContainView;
    private TextView mTopUserNumView;
    private TextView mCenterUserNumView;
    private RelativeLayout mCharTitleView;
    private TextView mCenterCountdownView;
    private RelativeLayout mEditContainView;
    private ImageView mFullScreenBtn;
    private ImageView mHalfScreenBtn;
    private ImageView mPauseAndResumeIconView;
    private TextView mPauseAndResumeTextView;
    private LinearLayout mControlBtnContainView;
    private ImageView mChangeCameraBtn;
    private ImageView mPauseAndResumeBtn;
    private ImageView mChatBtn;
    private ImageView mFinishBtn;
    private MenuPopupWindow mMenuPopupWindow;

    private UserInfo mUserInfo;
    private LiveParcelable mLive;

    private boolean mIsScreenFulled;
    private boolean mIsFrontCamera = true;
    private boolean mTitleBarVisibility = true;
    private boolean mEditContainViewVisibility = false;
    private boolean mPublishPaused;
    private boolean mCheckStartCountdownThreadRunFlag = true;
    private boolean mCountdownThreadRunFlag = true;

    private LiveChatAdapter mChatAdapter;

    private float mVideoWhScale;
    private float mVideoHwScale;
    private float mScreenHwScale;
    private int mPreviewHeight;
    private int mPreviewWidth;
    private int mContentViewHeight;
    private int mContentViewWidth;
    private int mUserNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_view);

        mRtSdk = new RtSdk();
        initView();
        setLoadingStatus();
        initContentViewSize();
//        setupLocalVideoSize();   ------
        setViewListener();
        getExtraData();
        setupChatListView();
//        computeScreenWhScale();    -----
        initWithGensee();
        hideControlBtnContainView();
        new CheckStartCountdownThread().start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mCheckStartCountdownThreadRunFlag = false;
        mCountdownThreadRunFlag = false;
    }

    @Override
    public void onBackPressed() {
        ShowUtils.showDialog(ActivityLive.this, R.string.app_name, R.string.live_pause_dialog_message,
                mSurePauseBtnClickListener, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LIVE_FINISH_REQUEST_CODE) finish();
    }

    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private View.OnClickListener mMenuBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mMenuPopupWindow == null) {
                mMenuPopupWindow = new MenuPopupWindow();
            }
            mMenuPopupWindow.showPopupWindow(mMenuBtn);
        }
    };

    private View.OnClickListener mPauseAndResumeBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPublishPaused) {
                mRtSdk.roomPublish(State.S_RUNNING.getValue(), mPublishResumeOnTaskRet); //开启直播  State.S_RUNNING  暂停直播State.S_PAUSED  停止直播 State.S_STOPPED
                mRtSdk.videoOpenCamera(null);
                mPauseAndResumeBtn.setImageResource(R.drawable.live_pause_white_icon);
                if (mPauseAndResumeIconView != null) {
                    mPauseAndResumeIconView.setImageResource(R.drawable.live_pause_black_icon);
                    mPauseAndResumeTextView.setText(R.string.live_menu_pause);
                }
                mPublishPaused = false;
            } else {
                mRtSdk.roomPublish(State.S_PAUSED.getValue(), mPublishPauseOnTaskRet); //开启直播  State.S_RUNNING  暂停直播State.S_PAUSED  停止直播 State.S_STOPPED
                mRtSdk.videoCloseCamera(null);
                mPauseAndResumeBtn.setImageResource(R.drawable.live_play_white_icon);
                if (mPauseAndResumeIconView != null) {
                    mPauseAndResumeIconView.setImageResource(R.drawable.live_play_black_icon);
                    mPauseAndResumeTextView.setText(R.string.live_menu_resume);
                }
                mPublishPaused = true;
            }
            if (mMenuPopupWindow != null && mMenuPopupWindow.isShowing())
                mMenuPopupWindow.dismiss();
        }
    };

    private View.OnClickListener mChatBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mEditContainViewVisibility) {
                startEditContainViewAnim();
            } else {
                mEditContainView.setVisibility(View.VISIBLE);
                ObjectAnimator.ofFloat(mEditContainView, "alpha", 0, 1).setDuration(300).start();
                setChatListViewMargin(true);
                mChatListView.scrollToPosition(mChatAdapter.getItemCount() - 1);
                mEditContainViewVisibility = true;
            }
        }
    };

    private View.OnClickListener mFinishBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ShowUtils.showDialog(ActivityLive.this, R.string.app_name, R.string.live_finish_dialog_message,
                    mSureFinishBtnClickListener, null);
            if (mMenuPopupWindow != null && mMenuPopupWindow.isShowing())
                mMenuPopupWindow.dismiss();
        }
    };

    private DialogInterface.OnClickListener mSureFinishBtnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mRtSdk.roomPublish(State.S_STOPPED.getValue(), mPublishPauseOnTaskRet);
            mRtSdk.release(null);
            Intent intent = new Intent();
            intent.setClass(ActivityLive.this, ActivityLiveFinish.class);
            intent.putExtra(Constant.EXTRA_LIVE_INFO, mLive);
            startActivityForResult(intent, LIVE_FINISH_REQUEST_CODE);
        }
    };

    private DialogInterface.OnClickListener mSurePauseBtnClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            mRtSdk.roomPublish(State.S_PAUSED.getValue(), mPublishPauseOnTaskRet);
            mRtSdk.release(null);
            finish();
        }
    };

    private View.OnClickListener mLocalVideoViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mTitleBarVisibility) {
                ObjectAnimator.ofFloat(mTitleBar, "alpha", 1, 0).setDuration(300).start();
                ObjectAnimator.ofFloat(mCenterCountdownView, "alpha", 1, 0).setDuration(300).start();
                ObjectAnimator.ofFloat(mFullScreenBtn, "alpha", 1, 0).setDuration(300).start();
                if (mIsScreenFulled) {
                    startHideControlBtnContainViewAnim();
                    startEditContainViewAnim();
                    ObjectAnimator.ofFloat(mTopCountdownContainView, "alpha", 1, 0).setDuration(300).start();
                    ObjectAnimator.ofFloat(mTopUserNumContainView, "alpha", 1, 0).setDuration(300).start();
                }
                mTitleBarVisibility = false;
            } else {
                ObjectAnimator.ofFloat(mTitleBar, "alpha", 0, 1).setDuration(300).start();
                ObjectAnimator.ofFloat(mCenterCountdownView, "alpha", 0, 1).setDuration(300).start();
                ObjectAnimator.ofFloat(mFullScreenBtn, "alpha", 0, 1).setDuration(300).start();
                if (mIsScreenFulled) {
                    mControlBtnContainView.setVisibility(View.VISIBLE);
                    ObjectAnimator.ofFloat(mControlBtnContainView, "alpha", 0, 1).setDuration(300).start();
                    ObjectAnimator.ofFloat(mTopCountdownContainView, "alpha", 0, 1).setDuration(300).start();
                    ObjectAnimator.ofFloat(mTopUserNumContainView, "alpha", 0, 1).setDuration(300).start();
                }
                mTitleBarVisibility = true;
            }
        }
    };

    private View.OnClickListener mFullScreenBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLocalVideoViewEx.getLayoutParams();
            if (mScreenHwScale > mVideoWhScale) {
                mPreviewHeight = mContentViewHeight;
                mPreviewWidth = (int) (mContentViewHeight * mVideoHwScale);
            } else {
                mPreviewWidth = mContentViewWidth;
                mPreviewHeight = (int) (mContentViewWidth * mVideoWhScale);
            }
            layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
            layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            mLocalVideoViewEx.setLayoutParams(layoutParams);
            mLocalVideoViewEx.onPreviewSize(mPreviewWidth, mPreviewHeight);
            mTopCountdownContainView.setVisibility(View.VISIBLE);
            mTopUserNumContainView.setVisibility(View.VISIBLE);
            mControlBtnContainView.setVisibility(View.VISIBLE);
            mEditContainView.setVisibility(View.GONE);
            mCharTitleView.setVisibility(View.GONE);
            mCenterCountdownView.setVisibility(View.GONE);
            mFullScreenBtn.setVisibility(View.GONE);
            mMenuBtn.setVisibility(View.GONE);
            mChatAdapter.setFullScreenStatus(true);
            setChatListViewMargin(false);
            mIsScreenFulled = true;
        }
    };

    private View.OnClickListener mHalfScreenBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLocalVideoViewEx.getLayoutParams();
            mPreviewHeight = (int) (mContentViewWidth * mVideoWhScale);
            mPreviewWidth = mContentViewWidth;
            layoutParams.height = mContentViewHeight / 2;
            mLocalVideoViewEx.setLayoutParams(layoutParams);
            mLocalVideoViewEx.onPreviewSize(mPreviewWidth, mPreviewHeight);
            mEditContainView.setVisibility(View.VISIBLE);
            mEditContainView.setAlpha(1);
            mCharTitleView.setVisibility(View.VISIBLE);
            mCenterCountdownView.setVisibility(View.VISIBLE);
            mFullScreenBtn.setVisibility(View.VISIBLE);
            mMenuBtn.setVisibility(View.VISIBLE);
            mFullScreenBtn.setVisibility(View.VISIBLE);
            mTopCountdownContainView.setVisibility(View.GONE);
            mTopUserNumContainView.setVisibility(View.GONE);
            mControlBtnContainView.setVisibility(View.GONE);
            mChatAdapter.setFullScreenStatus(false);
            setChatListViewMargin(true);
            mChatListView.scrollToPosition(mChatAdapter.getItemCount() - 1);
            mIsScreenFulled = false;
        }
    };

    private View.OnClickListener mChatSendBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String chat = mChatEditView.getText().toString();
            mRtSdk.chatWithPublic(chat, "", mSendChatOnTaskRet);
        }
    };

    private View.OnClickListener mChangeCameraBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mLocalVideoViewEx.doCameraSwitch();
            if (mIsFrontCamera) {
                mLocalVideoViewEx.setOrientation(ILocalVideoView.ORIENTATION_PORTRAIT);
                mIsFrontCamera = false;
            } else {
                mLocalVideoViewEx.setOrientation(ILocalVideoView.ORIENTATION_LANDSCAPE);
                mLocalVideoViewEx.onPreviewSize(mPreviewWidth, mPreviewHeight);
                mIsFrontCamera = true;
            }
            if (mMenuPopupWindow != null && mMenuPopupWindow.isShowing())
                mMenuPopupWindow.dismiss();
        }
    };

    private View.OnLayoutChangeListener mRootViewOnLayoutChangeListener = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            int keyHeight = mContentViewHeight / 3; //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
            if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight) && mIsScreenFulled) { //软键盘关闭
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLocalVideoViewEx.getLayoutParams();
                layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
                layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                mLocalVideoViewEx.setLayoutParams(layoutParams);
                mLocalVideoViewEx.onPreviewSize(mPreviewWidth, mPreviewHeight);
            }

        }
    };

    private Animator.AnimatorListener mHideControlBtnContainViewAnimListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mControlBtnContainView.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private Animator.AnimatorListener mEditContainViewAnimListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mEditContainView.setVisibility(View.GONE);
            InputMethodManager imm = (InputMethodManager) mChatEditView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mChatEditView.getWindowToken(), 0);
            setChatListViewMargin(false);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private OnTaskRet mOpenCameraOnTaskRet = new OnTaskRet() {
        @Override
        public void onTaskRet(boolean b, int i, String s) {
            if (b) {
                mUserNum = mRtSdk.getAllUsers().size();
                mHandler.sendEmptyMessage(WHAT_HIDE_LOADING_VIEW);
                mRtSdk.roomPublish(State.S_RUNNING.getValue(), mPublishOnTaskRet); //开启直播  State.S_RUNNING  暂停直播State.S_PAUSED  停止直播 State.S_STOPPED
            } else {
                if (DEBUG_ENABLE) Log.e(TAG, "open camera error");
            }
        }
    };

    private OnTaskRet mPublishOnTaskRet = new OnTaskRet() {
        @Override
        public void onTaskRet(boolean b, int i, String s) {
            if (!b) Log.e(TAG, "roomPublish RUNNING Error");

        }
    };

    private OnTaskRet mPublishPauseOnTaskRet = new OnTaskRet() {
        @Override
        public void onTaskRet(boolean b, int i, String s) {
            if (b) {
                Snackbar.make(mCastVideoView, R.string.live_publish_pause, Snackbar.LENGTH_LONG).show();
            } else {
                Log.e(TAG, "RtSdk roomPublish PAUSED error");
            }
        }
    };

    private OnTaskRet mPublishResumeOnTaskRet = new OnTaskRet() {
        @Override
        public void onTaskRet(boolean b, int i, String s) {
            if (b) {
                Snackbar.make(mCastVideoView, R.string.live_publish_resume, Snackbar.LENGTH_LONG).show();
            } else {
                Log.e(TAG, "RtSdk roomPublish RUNNING error");
            }
        }
    };

    private OnTaskRet mSendChatOnTaskRet = new OnTaskRet() {
        @Override
        public void onTaskRet(boolean b, int i, String s) {
            if (b) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatEditView.setText("");
                        if (mIsScreenFulled) startEditContainViewAnim();
                    }
                });
            }
        }
    };

    private RtComp.Callback mRtCompCallback = new RtComp.Callback() {
        @Override
        public void onInited(String s) {
            mRtSdk.initWithParam("", s, mIRoomCallBack);
            mHandler.sendEmptyMessage(WHAT_JOIN_ROOM);
        }

        @Override
        public void onErr(int i) {
            switch (i) {
                case ERR_DOMAIN:
                    if (DEBUG_ENABLE) Log.e(TAG, "RtCompCallback ERR_DOMAIN");
                    break;
                case ERR_NUMBER_UNEXIST:
                    if (DEBUG_ENABLE) Log.e(TAG, "RtCompCallback ERR_NUMBER_UNEXIST");
                    break;
                case ERR_TOKEN:
                    if (DEBUG_ENABLE) Log.e(TAG, "RtCompCallback ERR_TOKEN");
                    break;
                case ERR_SERVICE:
                    if (DEBUG_ENABLE) Log.e(TAG, "RtCompCallback ERR_SERVICE");
                    break;
                case ERR_UN_NET:
                    if (DEBUG_ENABLE) Log.e(TAG, "RtCompCallback ERR_UN_NET");
                    break;
                case ERR_TIME_OUT:
                    if (DEBUG_ENABLE) Log.e(TAG, "RtCompCallback ERR_TIME_OUT");
                    break;
                case ERR_PARAM:
                    if (DEBUG_ENABLE) Log.e(TAG, "RtCompCallback ERR_PARAM");
                    break;
                case ERR_THIRD_CERTIFICATION_AUTHORITY:
                    if (DEBUG_ENABLE) Log.e(TAG, "RtCompCallback ERR_THIRD_CERTIFICATION_AUTHORITY");
                    break;
                default:
                    if (DEBUG_ENABLE) Log.e(TAG, "RtCompCallback OTHER ERROR");
                    break;
            }
        }
    };

    private IRoomCallBack mIRoomCallBack = new IRoomCallBack() {
        @Override
        public void onInit(boolean b) {
            if (b) {

//                rtSdk.setVoteCallback(voteHolderFragement);
//                rtSdk.setAsCallBack(this);
//                rtSdk.setQACallback(this);
//                rtSdk.setAudioCallback(this);
//                mRtSdk.setGSDocViewGx(docView);
//                rtSdk.setLodCallBack(this);
                mRtSdk.setLocalVideoView(mLocalVideoViewEx);
                mRtSdk.setVideoCallBack(mIVideoCallBack);
                mRtSdk.setChatCallback(mIChatCallBack);
                mRtSdk.join(null);
            }
        }

        @Override
        public void onJoin(boolean b) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onJoin");
        }

        @Override
        public void onRoomJoin(int i, UserInfo userInfo, boolean b) {

            switch (i) {
                case JR_OK:
                    mUserInfo = userInfo;
                    mRtSdk.videoOpenCamera(mOpenCameraOnTaskRet);
                    mHandler.sendEmptyMessage(WHAT_OPEN_LIVE);
                    break;
                case JR_ERROR_HOST:
                    if (DEBUG_ENABLE) Log.e(TAG, "IRoomCallBack JR_ERROR_HOST");
                    break;
                case JR_ERROR_GETPARAM:
                    if (DEBUG_ENABLE) Log.e(TAG, "IRoomCallBack JR_ERROR_GETPARAM");
                    break;
                case JR_ERROR_LICENSE:
                    if (DEBUG_ENABLE) Log.e(TAG, "IRoomCallBack JR_ERROR_LICENSE");
                    break;
                case JR_ERROR_LOCKED:
                    if (DEBUG_ENABLE) Log.e(TAG, "IRoomCallBack JR_ERROR_LOCKED");
                    break;
                case JR_ERROR_CODEC:
                    if (DEBUG_ENABLE) Log.e(TAG, "IRoomCallBack JR_ERROR_CODEC");
                    break;
                default:
                    if (DEBUG_ENABLE) Log.e(TAG, "IRoomCallBack ERROR");
                    break;
            }
        }

        @Override
        public void onRoomLeave(int i) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomLeave");
        }

        @Override
        public void onRoomReconnecting() {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomReconnecting");
        }

        @Override
        public void onRoomLock(boolean b) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomLock");
        }

        @Override
        public void onRoomUserJoin(UserInfo userInfo) {
            mUserNum = mRtSdk.getAllUsers().size();
            mHandler.sendEmptyMessage(WHAT_UPDATE_USER_NUM);
        }

        @Override
        public void onRoomUserUpdate(UserInfo userInfo) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomUserUpdate");
        }

        @Override
        public void onRoomUserLeave(UserInfo userInfo) {
            mUserNum = mRtSdk.getAllUsers().size();
            mHandler.sendEmptyMessage(WHAT_UPDATE_USER_NUM);
        }

        @Override
        public Context onGetContext() {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onGetContext");
            return ActivityLive.this;
        }

        @Override
        public ServiceType getServiceType() {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack getServiceType");
            return null;
        }

        @Override
        public void onRoomPublish(State state) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomPublish");
        }

        @Override
        public void onRoomRecord(State state) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomRecord");
        }

        @Override
        public void onRoomData(String s, long l) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomData");
        }

        @Override
        public void onRoomBroadcastMsg(String s) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomBroadcastMsg");
        }

        @Override
        public void onRoomRollcall(int i) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomRollcall");
        }

        @Override
        public void onRoomRollcallAck(long l) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomRollcallAck");
        }

        @Override
        public void onRoomHandup(long l, String s) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomHandup");
        }

        @Override
        public void onRoomHanddown(long l) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomHanddown");
        }

        @Override
        public void OnUpgradeNotify(String s) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack OnUpgradeNotify");
        }

        @Override
        public void onChatMode(int i) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onChatMode");
        }

        @Override
        public void onFreeMode(boolean b) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onFreeMode");
        }

        @Override
        public void onLottery(byte b, String s) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onLottery");
        }

        @Override
        public void onSettingSet(String s, int i) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onSettingSet");
        }

        @Override
        public void onSettingSet(String s, String s1) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onSettingSet");
        }

        @Override
        public int onSettingQuery(String s, int i) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onSettingQuery");
            return 0;
        }

        @Override
        public String onSettingQuery(String s) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onSettingQuery");
            return null;
        }

        @Override
        public void onNetworkReport(byte b) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onNetworkReport");
        }

        @Override
        public void onNetworkBandwidth(int i, int i1) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onNetworkBandwidth");
        }

        @Override
        public void onRoomPhoneServiceStatus(boolean b) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomPhoneServiceStatus");
        }

        @Override
        public void onRoomPhoneCallingStatus(String s, int i, int i1) {
            if (DEBUG_ENABLE) Log.i(TAG, "IRoomCallBack onRoomPhoneCallingStatus");
        }
    };

    private IVideoCallBack mIVideoCallBack = new IVideoCallBack() {
        @Override
        public void onVideoJoinConfirm(boolean b) {
            if (DEBUG_ENABLE) Log.i(TAG, "mIVideoCallBack onVideoJoinConfirm");
        }

        @Override
        public void onVideoCameraAvailiable(boolean b) {
            if (DEBUG_ENABLE) Log.i(TAG, "mIVideoCallBack onVideoCameraAvailiable");
        }

        @Override
        public void onVideoCameraOpened() {
            mRtSdk.videoActive(mUserInfo.getId(), true, null);
        }

        @Override
        public void onVideoCameraClosed() {
            if (DEBUG_ENABLE) Log.i(TAG, "mIVideoCallBack onVideoCameraClosed");
        }

        @Override
        public void onVideoJoin(UserInfo userInfo) {
            if (UserInfo.LOD_USER_ID == userInfo.getId()) {
                mRtSdk.displayVideo(userInfo.getId(), null);
            }
        }

        @Override
        public void onVideoLeave(long l) {
            if (DEBUG_ENABLE) Log.i(TAG, "mIVideoCallBack onVideoLeave");
        }

        @Override
        public void onVideoActived(UserInfo userInfo, boolean b) {
            long userId = userInfo.getId();
            if (b) mRtSdk.displayVideo(userId, null);
            else mRtSdk.unDisplayVideo(userId, null);
        }

        @Override
        public void onVideoDisplay(UserInfo userInfo) {
            if (DEBUG_ENABLE) Log.i(TAG, "mIVideoCallBack onVideoDisplay");
        }

        @Override
        public void onVideoUndisplay(long l) {
            if (DEBUG_ENABLE) Log.i(TAG, "mIVideoCallBack onVideoUndisplay");
        }

        @Override
        public void onVideoDataRender(long userId, int width, int height, int frameFormat, float displayRatio, byte[] data) {
            mCastVideoView.onReceiveFrame(data, width, height);
        }
    };

    private IChatCallBack mIChatCallBack = new IChatCallBack() {
        @Override
        public void onChatJoinConfirm(boolean b) {
            if (DEBUG_ENABLE) Log.i(TAG, "mIChatCallBack onChatJoinConfirm");
        }

        @Override
        public void onChatWithPersion(UserInfo userInfo, String s, String s1) {
            if (DEBUG_ENABLE) Log.i(TAG, "mIChatCallBack onChatWithPersion");
        }

        @Override
        public void onChatWithPublic(final UserInfo userInfo, final String s, final String s1) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
                    long currentTime = System.currentTimeMillis();
                    LiveChat lc = new LiveChat();
                    lc.userName = userInfo.getName();
                    lc.isTeacher = userInfo.getClientType() == 9 ? true : false;
                    lc.time = timeFormat.format(currentTime);
                    lc.content = s;
                    mChatAdapter.addItem(lc);
                    mChatListView.scrollToPosition(mChatAdapter.getItemCount() - 1);
                }
            });
        }

        @Override
        public void onChatToPersion(long l, String s, String s1) {
            if (DEBUG_ENABLE) Log.i(TAG, "mIChatCallBack onChatToPersion");
        }

        @Override
        public void onChatEnable(boolean b) {
            if (DEBUG_ENABLE) Log.i(TAG, "mIChatCallBack onChatEnable");
        }
    };

    private ILocalVideoView.OnCameraInfoListener mOnCameraInfoListener = new ILocalVideoView.OnCameraInfoListener() {
        @Override
        public void onCameraInfo(Camera camera, Camera.CameraInfo cameraInfo, int i) {
            if (DEBUG_ENABLE) Log.i(TAG, "mOnCameraInfoListener onCameraInfo");
        }

        @Override
        public void onPreviewSize(int w, int h) {
            if (mVideoWhScale == 0) {
                float fw = w;
                float fh = h;
                mVideoWhScale = fw / fh;
                mVideoHwScale = fh / fw;
                initLocalVideoViewExSize();
            }

            if (mPreviewWidth != w || mPreviewHeight != h) {
                mLocalVideoViewEx.onPreviewSize(mPreviewWidth, mPreviewHeight);
            }
        }
    };


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_JOIN_ROOM:
                    mLoadingTextView.setText(R.string.live_join_room);
                    break;
                case WHAT_OPEN_LIVE:
                    mLoadingTextView.setText(R.string.live_open_live);
                    break;
                case WHAT_HIDE_LOADING_VIEW:
                    mLoadingStatusContainView.setVisibility(View.GONE);
                    break;
                case WHAT_UPDATE_USER_NUM:
                    mTopUserNumView.setText(String.valueOf(mUserNum));
                    mCenterUserNumView.setText("在线人数:" + mUserNum);
                    break;
                case WHAT_END_TIME_COUNTDOWN:
                    countdown();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void initView() {
        mLocalVideoViewEx = (LocalVideoViewEx) findViewById(R.id.local_video_view);
        mChatListView = (RecyclerView) findViewById(R.id.chat_list_view);
        mChatEditView = (EditText) findViewById(R.id.chat_edit_view);
        mChatSendBtn = (TextView) findViewById(R.id.chat_send_btn);
        mCastVideoView = (GSVideoView) findViewById(R.id.cast_video_view);
        mCastVideoView.setRenderMode(GSVideoView.RenderMode.RM_ADPT_XY);

        mActivityRootView = findViewById(R.id.root_view);
        mLoadingStatusContainView = (RelativeLayout) findViewById(R.id.loading_status_contain_view);
        mLoadingTextView = (TextView) findViewById(R.id.loading_text_view);
        mTitleBar = findViewById(R.id.title_bar_view);
        mBackView = (ImageView) findViewById(R.id.back_view);
        mMenuBtn = (ImageView) findViewById(R.id.menu_btn);
        mTopCountdownContainView = (RelativeLayout) findViewById(R.id.top_countdown_contain_view);
        mTopCountdownView = (TextView) findViewById(R.id.top_countdown_view);
        mTopUserNumContainView = (RelativeLayout) findViewById(R.id.top_user_num_contain_view);
        mTopUserNumView = (TextView) findViewById(R.id.top_user_num_view);
        mCenterCountdownView = (TextView) findViewById(R.id.center_countdown_view);
        mCenterUserNumView = (TextView) findViewById(R.id.center_user_num_view);
        mCharTitleView = (RelativeLayout) findViewById(R.id.chat_title_view);
        mEditContainView = (RelativeLayout) findViewById(R.id.edit_contain_view);
        mFullScreenBtn = (ImageView) findViewById(R.id.full_screen_btn);
        mHalfScreenBtn = (ImageView) findViewById(R.id.half_screen_btn);
        mChatBtn = (ImageView) findViewById(R.id.chat_btn);
        mControlBtnContainView = (LinearLayout) findViewById(R.id.control_btn_contain_view);
        mChangeCameraBtn = (ImageView) findViewById(R.id.change_camera_btn);
        mPauseAndResumeBtn = (ImageView) findViewById(R.id.pause_and_resume_btn);
        mFinishBtn = (ImageView) findViewById(R.id.finish_btn);

    }

    private void setupLocalVideoSize() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLocalVideoViewEx.getLayoutParams();
        layoutParams.height = mContentViewHeight / 2;
        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        mLocalVideoViewEx.setLayoutParams(layoutParams);
        mLocalVideoViewEx.setVideoSize(640, 480); //1280x720
    }

    private void setupChatListView() {
        mChatAdapter = new LiveChatAdapter(this);
        mChatListView.setAdapter(mChatAdapter);
        mChatListView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setViewListener() {

        mLocalVideoViewEx.setOnCameraInfoListener(mOnCameraInfoListener);
        mLocalVideoViewEx.setOnClickListener(mLocalVideoViewClickListener);
        mActivityRootView.addOnLayoutChangeListener(mRootViewOnLayoutChangeListener);
        mBackView.setOnClickListener(mBackViewClickListener);
        mMenuBtn.setOnClickListener(mMenuBtnClickListener);
        mFullScreenBtn.setOnClickListener(mFullScreenBtnClickListener);
        mHalfScreenBtn.setOnClickListener(mHalfScreenBtnClickListener);
        mChatSendBtn.setOnClickListener(mChatSendBtnClickListener);
        mChangeCameraBtn.setOnClickListener(mChangeCameraBtnClickListener);
        mPauseAndResumeBtn.setOnClickListener(mPauseAndResumeBtnClickListener);
        mFinishBtn.setOnClickListener(mFinishBtnClickListener);
        mChatBtn.setOnClickListener(mChatBtnClickListener);
    }

    private void initWithGensee() {
        mInitParam = new InitParam();
        mInitParam.setDomain("lichen.gensee.com");
        mInitParam.setNumber("23031971");
        mInitParam.setLoginAccount("admin@lichen.com");
        mInitParam.setLoginPwd("2016lichenjy");
        mInitParam.setNickName("Android教师客户端");
        mInitParam.setJoinPwd("902405");
        mInitParam.setServiceType(ServiceType.ST_CASTLINE);
        mRtComp = new RtComp(this, mRtCompCallback);
        mRtComp.initWithGensee(mInitParam);
    }

    private void initLocalVideoViewExSize() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLocalVideoViewEx.getLayoutParams();
        mPreviewHeight = (int) (mContentViewWidth * mVideoWhScale);
        mPreviewWidth = mContentViewWidth;
        mLocalVideoViewEx.onPreviewSize(mPreviewWidth, mPreviewHeight);
        layoutParams.height = mContentViewHeight / 2;
        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        mIsScreenFulled = false;
        mLocalVideoViewEx.setLayoutParams(layoutParams);
    }

    private void initContentViewSize() {
        final View view = findViewById(android.R.id.content);
        mContentViewWidth = ShowUtils.getScreenWidth(ActivityLive.this);
//        mContentViewHeight = ShowUtils.getScreenHeight(ActivityLive.this);
        view.post(new Runnable() {
            @Override
            public void run() {
                mContentViewHeight = view.getMeasuredHeight();
                setupLocalVideoSize();
                computeScreenWhScale();
            }
        });

    }

    private void computeScreenWhScale() {
        float fw = mContentViewWidth;
        float fh = mContentViewHeight;
        mScreenHwScale = fh / fw;
    }

    private class CheckStartCountdownThread extends Thread {

        @Override
        public void run() {
            while (mCheckStartCountdownThreadRunFlag) {
                try {
                    synchronized (this) {
                        checkStartCountdown();
                        wait(60000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class CountdownThread extends Thread {

        @Override
        public void run() {
            while (mCountdownThreadRunFlag) {
                try {
                    synchronized (this) {
                        wait(1000);
                        mHandler.sendEmptyMessage(WHAT_END_TIME_COUNTDOWN);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class MenuPopupWindow extends PopupWindow {

        private View contentView;
        private View mChangeCameraMenu;
        private View mPauseAndResumeMenu;
        private View mFinishMenu;

        public MenuPopupWindow() {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            contentView = inflater.inflate(R.layout.live_control_menu_view, null);
            this.setContentView(contentView);
            this.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
            this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
            this.setFocusable(true);
            this.setOutsideTouchable(true);
            ColorDrawable dw = new ColorDrawable(0xF000000);
            this.setBackgroundDrawable(dw); //点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
            initView();
            setViewClickListener();
            this.update();
        }

        public void showPopupWindow(View parent) {
            if (!this.isShowing()) {
                this.showAsDropDown(parent, 0, 0);
            } else {
                this.dismiss();
            }
        }

        private void initView() {
            mChangeCameraMenu = contentView.findViewById(R.id.menu_change_camera);
            mPauseAndResumeMenu = contentView.findViewById(R.id.menu_pause_and_resume);
            mFinishMenu = contentView.findViewById(R.id.menu_finish);
            mPauseAndResumeIconView = (ImageView) contentView.findViewById(R.id.pause_and_resume_icon_view);
            mPauseAndResumeTextView = (TextView) contentView.findViewById(R.id.pause_and_resume_text_view);
        }

        private void setViewClickListener() {
            mChangeCameraMenu.setOnClickListener(mChangeCameraBtnClickListener);
            mPauseAndResumeMenu.setOnClickListener(mPauseAndResumeBtnClickListener);
            mFinishMenu.setOnClickListener(mFinishBtnClickListener);
        }
    }

    private void checkStartCountdown() {
        String startTime = "14:35:60";
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
        long currentTime = System.currentTimeMillis();

        String currentTimeStr[] = timeFormat.format(currentTime).split(":");
        String startTimeStr[] = startTime.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTime);
        int apm = calendar.get(Calendar.AM_PM);
        int currentHour = Integer.valueOf(currentTimeStr[0]);
        int currentMinute = Integer.valueOf(currentTimeStr[1]);
        int startHour = Integer.valueOf(startTimeStr[0]);
        int startMinute = Integer.valueOf(startTimeStr[1]);
        if (apm == 1 && currentHour < 12) currentHour = currentHour + 12;
        int countdownHour = startHour - currentHour;
        int countdownMinute = startMinute - currentMinute;
        if (countdownHour == 0 && countdownMinute <= 10) {
            new CountdownThread().start();
            mCheckStartCountdownThreadRunFlag = false;
        }
    }

    private void countdown() {
        String startTime = "14:35:60";
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        long currentTime = System.currentTimeMillis();

        String currentTimeStr[] = timeFormat.format(currentTime).split(":");
        String startTimeStr[] = startTime.split(":");

        int currentMinute = Integer.valueOf(currentTimeStr[0]);
        int currentSecond = Integer.valueOf(currentTimeStr[1]);
        int startMinute = Integer.valueOf(startTimeStr[1]);
        int startSecond = Integer.valueOf(startTimeStr[2]);
        int countdownMinute = startMinute - currentMinute;
        int countdownSecond = startSecond - currentSecond;

        String countDownMinuteStr = "";
        String countDownSecondStr = "";
        if (countdownMinute < 10) countDownMinuteStr = "0" + countdownMinute;
        else countDownMinuteStr = String.valueOf(countdownMinute);
        if (countdownSecond == 60) countdownSecond = 0;
        if (countdownSecond < 10) countDownSecondStr = "0" + countdownSecond;
        else countDownSecondStr = String.valueOf(countdownSecond);
        String countdownTime = "00:" + countDownMinuteStr + ":" + countDownSecondStr;
        mTopCountdownView.setText(countdownTime);
        mCenterCountdownView.setText(countdownTime);
        if (countdownMinute == 0 && countdownSecond == 0) mCountdownThreadRunFlag = false;
    }

    private void setLoadingStatus() {
        mLoadingStatusContainView.setVisibility(View.VISIBLE);
    }

    private void startHideControlBtnContainViewAnim() {
        mControlBtnContainView.setVisibility(View.VISIBLE);
        ObjectAnimator mmControlBtnContainViewAnim = ObjectAnimator.ofFloat(mControlBtnContainView, "alpha", 1, 0).setDuration(300);
        mmControlBtnContainViewAnim.addListener(mHideControlBtnContainViewAnimListener);
        mmControlBtnContainViewAnim.start();
    }

    private void startEditContainViewAnim() {
        if (!mEditContainViewVisibility) return;
        mEditContainView.setVisibility(View.VISIBLE);
        ObjectAnimator mEditContainViewAnim = ObjectAnimator.ofFloat(mEditContainView, "alpha", 1, 0).setDuration(300);
        mEditContainViewAnim.addListener(mEditContainViewAnimListener);
        mEditContainViewAnim.start();
        mEditContainViewVisibility = false;
    }

    private void setChatListViewMargin(boolean isSetMarginBottom) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mChatListView.getLayoutParams();
        if (isSetMarginBottom)
            layoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.five_design_view_size));
        else layoutParams.setMargins(0, 0, 0, 0);
    }

    private void hideControlBtnContainView() {
        mControlBtnContainView.setVisibility(View.GONE);
    }

    private void getExtraData() {
        Intent intent = getIntent();
        mLive = intent.getParcelableExtra(Constant.EXTRA_LIVE_INFO);
    }

}

package com.lichen.teacher.apps;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
import com.gensee.view.LocalVideoViewEx;
import com.lichen.teacher.R;

public class ActivityLive extends AppCompatActivity {

    private static final String TAG = "ActivityLive";

    private RtComp mRtComp;
    private RtSdk mRtSdk;
    private InitParam mInitParam;

    private Button mJoinBtn;
    private Button mExitBtn;
    private Button mVideoBtn;
    private Button mPublishBtn;
    private LocalVideoViewEx mLocalVideoViewEx;
    private GSVideoView mCastVideoView;

    private boolean mIsVideoOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_view);

        mRtSdk = new RtSdk();
        initView();
        setViewListener();
    }

    private RtComp.Callback mRtCompCallback = new RtComp.Callback() {
        @Override
        public void onInited(String s) {
            mRtSdk.initWithParam("", s, mIRoomCallBack);
        }

        @Override
        public void onErr(int i) {
            switch (i) {
                case ERR_DOMAIN:
                    Log.e(TAG, "INFO");
                    break;
                case ERR_NUMBER_UNEXIST:
                    Log.e(TAG, "INFO");
                    break;
                case ERR_TOKEN:
                    Log.e(TAG, "INFO");
                    break;
                case ERR_SERVICE:
                    Log.e(TAG, "INFO");
                    break;
                case ERR_UN_NET:
                    Log.e(TAG, "INFO");
                    break;
                case ERR_TIME_OUT:
                    Log.e(TAG, "INFO");
                    break;
                case ERR_PARAM:
                    Log.e(TAG, "INFO");
                    break;
                case ERR_THIRD_CERTIFICATION_AUTHORITY:
                    Log.e(TAG, "INFO");
                    break;
                default:
                    Log.e(TAG, "INFO");
                    break;
            }
        }
    };

    private IRoomCallBack mIRoomCallBack = new IRoomCallBack() {
        @Override
        public void onInit(boolean b) {
            if (b) {
//                mRtSdk.setGSDocViewGx(docView);
                mRtSdk.setLocalVideoView(mLocalVideoViewEx);
                mRtSdk.setVideoCallBack(mIVideoCallBack);
//                rtSdk.setAudioCallback(this);
//                rtSdk.setLodCallBack(this);
//                rtSdk.setChatCallback(this);
//                rtSdk.setVoteCallback(voteHolderFragement);
//                rtSdk.setAsCallBack(this);
//                rtSdk.setQACallback(this);
                mRtSdk.join(new OnTaskRet() {

                    @Override
                    public void onTaskRet(boolean ret, int id, String desc) {
                        Log.e(TAG, "INFO");
                        if (!ret) {
                            Log.e(TAG, "INFO");
                        }
                    }
                });
            }
        }

        @Override
        public void onJoin(boolean b) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomJoin(int i, UserInfo userInfo, boolean b) {

            switch (i) {
                case JR_OK:
                    Log.e(TAG, "INFO");
                    break;
                case JR_ERROR_HOST:
                    Log.e(TAG, "INFO");
                    break;
                case JR_ERROR_GETPARAM:
                    Log.e(TAG, "INFO");
                    break;
                case JR_ERROR_LICENSE:
                    Log.e(TAG, "INFO");
                    break;
                case JR_ERROR_LOCKED:
                    Log.e(TAG, "INFO");
                    break;
                case JR_ERROR_CODEC:
                    Log.e(TAG, "INFO");
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onRoomLeave(int i) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomReconnecting() {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomLock(boolean b) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomUserJoin(UserInfo userInfo) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomUserUpdate(UserInfo userInfo) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomUserLeave(UserInfo userInfo) {
            Log.e(TAG, "INFO");
        }

        @Override
        public Context onGetContext() {
            Log.e(TAG, "INFO");
            return ActivityLive.this;
        }

        @Override
        public ServiceType getServiceType() {
            Log.e(TAG, "INFO");
            return null;
        }

        @Override
        public void onRoomPublish(State state) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomRecord(State state) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomData(String s, long l) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomBroadcastMsg(String s) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomRollcall(int i) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomRollcallAck(long l) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomHandup(long l, String s) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomHanddown(long l) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void OnUpgradeNotify(String s) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onChatMode(int i) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onFreeMode(boolean b) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onLottery(byte b, String s) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onSettingSet(String s, int i) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onSettingSet(String s, String s1) {
            Log.e(TAG, "INFO");
        }

        @Override
        public int onSettingQuery(String s, int i) {
            Log.e(TAG, "INFO");
            return 0;
        }

        @Override
        public String onSettingQuery(String s) {
            Log.e(TAG, "INFO");
            return null;
        }

        @Override
        public void onNetworkReport(byte b) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onNetworkBandwidth(int i, int i1) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomPhoneServiceStatus(boolean b) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onRoomPhoneCallingStatus(String s, int i, int i1) {
            Log.e(TAG, "INFO");
        }
    };

    private View.OnClickListener mJoinBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initWithGensee();
        }
    };

    private View.OnClickListener mExitBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mRtSdk.release(new OnTaskRet() {
                @Override
                public void onTaskRet(boolean b, int i, String s) {
                    Log.e(TAG, "INFO");
                }
            });
        }
    };

    private OnTaskRet mOpenCameraOnTaskRet = new OnTaskRet() {
        @Override
        public void onTaskRet(boolean b, int i, String s) {
            Log.e(TAG, "INFO");
            if (b) mIsVideoOpened = true;
        }
    };

    private OnTaskRet mCloseCameraOnTaskRet = new OnTaskRet() {
        @Override
        public void onTaskRet(boolean b, int i, String s) {
            Log.e(TAG, "INFO");
            if (b) mIsVideoOpened = false;
        }
    };

    private OnTaskRet mPublishOnTaskRet = new OnTaskRet() {
        @Override
        public void onTaskRet(boolean b, int i, String s) {
            Log.e(TAG, "INFO");
        }
    };

    private View.OnClickListener mVideoBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!mIsVideoOpened) mRtSdk.videoOpenCamera(mOpenCameraOnTaskRet);
            else mRtSdk.videoCloseCamera(mCloseCameraOnTaskRet);
        }
    };

    private View.OnClickListener mPublishBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //开启直播  State.S_RUNNING  暂停直播State.S_PAUSED  停止直播 State.S_STOPPED
            mRtSdk.roomPublish(State.S_RUNNING.getValue(), mPublishOnTaskRet);

            //开启录制 State.S_RUNNING 暂停录制State.S_PAUSED  停止录制 State.S_STOPPED
            mRtSdk.roomRecord(State.S_RUNNING.getValue(), mPublishOnTaskRet);
        }
    };

    private IVideoCallBack mIVideoCallBack = new IVideoCallBack() {
        @Override
        public void onVideoJoinConfirm(boolean b) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onVideoCameraAvailiable(boolean b) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onVideoCameraOpened() {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onVideoCameraClosed() {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onVideoJoin(UserInfo userInfo) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onVideoLeave(long l) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onVideoActived(UserInfo userInfo, boolean b) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onVideoDisplay(UserInfo userInfo) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onVideoUndisplay(long l) {
            Log.e(TAG, "INFO");
        }

        @Override
        public void onVideoDataRender(long userId, int width, int height, int frameFormat, float displayRatio, byte[] data) {
            mCastVideoView.onReceiveFrame(data, width, height);
        }
    };

    private void initView() {
        mJoinBtn = (Button) findViewById(R.id.join_btn);
        mExitBtn = (Button) findViewById(R.id.exit_btn);
        mVideoBtn = (Button) findViewById(R.id.video_btn);
        mPublishBtn = (Button) findViewById(R.id.publish_btn);
        mLocalVideoViewEx = (LocalVideoViewEx) findViewById(R.id.local_video_view);
        mCastVideoView = (GSVideoView) findViewById(R.id.cast_video_view);
    }

    private void setViewListener() {
        mJoinBtn.setOnClickListener(mJoinBtnClickListener);
        mExitBtn.setOnClickListener(mExitBtnClickListener);
        mVideoBtn.setOnClickListener(mVideoBtnClickListener);
        mPublishBtn.setOnClickListener(mPublishBtnClickListener);
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
}

package com.lichen.teacher.apps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lichen.teacher.R;
import com.lichen.teacher.apps.fragments.FragmentChat;
import com.lichen.teacher.apps.fragments.FragmentHome;
import com.lichen.teacher.apps.fragments.FragmentLive;
import com.lichen.teacher.apps.fragments.FragmentMore;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.util.ShowUtils;
import com.lichen.teacher.view.NoScrollViewPager;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;



/**
 * update by xiaowu on 2016/8/18.
 */
@SuppressWarnings("deprecation")
public class ActivityTabs extends FragmentActivity implements OnClickListener {

    private static final String TAG = "ActivityTabs";

    private static final int PAGE_POSITION_HOME = 0;
    private static final int PAGE_POSITION_LIVE = 1;
    private static final int PAGE_POSITION_CHAT = 2;
    private static final int PAGE_POSITION_MORE = 3;

    private NoScrollViewPager mViewPager;
    private TextView mDockTitleHomeView;
    private TextView mDockTitleChatView;
    private TextView mDockTitleLiveView;
    private TextView mDockTitleUserView;

    private ImageView mDockIconHomeView;
    private ImageView mDockIconChatView;
    private ImageView mDockIconLiveView;
    private ImageView mDockIconUserView;

    private LinearLayout mDockHomeView;
    private LinearLayout mDockChatView;
    private LinearLayout mDockLiveView;
    private LinearLayout mDockUserView;

    private List<Fragment> mFragmentList = new ArrayList();
    private List<ImageView> mDockIconViews = new ArrayList<>();
    private List<TextView> mDockTitleViews = new ArrayList<>();
    private List<LinearLayout> mDockViews = new ArrayList();
    private String mNewVersionDownloadUrl;

    private FragmentHome mFragmentHome;
    private FragmentLive mFragmentLive;
//    private FragmentHome.UpdateMessageNoticeReceiver mUpdateMessageNoticeReceiver;
//    private FragmentLive.NotifyListReceiver mNotifyListReceiver;
    private Receiver mReceiver;
    private  LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_view);

        initView();
        checkHomeDockView();
        addRbViewList();
        addListener();
        setUpViewPager();
        registerReceiver();
//        checkUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dock_home_view:
                selectPageHome();

//                intent = new Intent(Constant.UPDATE_MESSAGE_NOTICE_ACTION);
//                mLocalBroadcastManager.sendBroadcast(intent);

//                mFragmentHome.updateMessageNotice();
                mFragmentLive.setCountdownEnable(false);
                break;
            case R.id.dock_live_view:
                selectPageLive();

//                intent = new Intent(Constant.NOTIFY_LIST_ACTION);
//                intent.putExtra(Constant.EXTRA_FRAGMENT_LIVE_COUNT_DOWN_TAG, FragmentLive.COUNT_DOWN_START);
//                mLocalBroadcastManager.sendBroadcast(intent);
                mFragmentLive.setCountdownEnable(true);
                break;
            case R.id.dock_chat_view:
                selectPageChat();
                mFragmentLive.setCountdownEnable(false);
                break;
            case R.id.dock_user_view:
                selectPageMore();
                mFragmentLive.setCountdownEnable(false);
                break;
            default:
                break;
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ShowUtils.showMsg(ActivityTabs.this, getString(R.string.exit_application));
                exitTime = System.currentTimeMillis();
            } else {
                Intent startMain = new Intent(Intent.ACTION_MAIN);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(startMain);
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    private DialogInterface.OnClickListener mSureUpdateClickListener = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            if (Utils.getNetworkType(ActivityTabs.this) != Utils.NETTYPE_WIFI) {
//                ShowUtils.showDilog(ActivityTabs.this, R.string.app_name, R.string.setting_not_wifi_download_prompt, mOkBtnClickListener , null);
//            } else {
//                downloadNewVersion();
//            }
//        }
//    };
//
//    private DialogInterface.OnClickListener mOkBtnClickListener = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            downloadNewVersion();
//        }
//    };
//
//
//    private Callback.CommonCallback mCallback = new Callback.CommonCallback<String>() {
//        @Override
//        public void onSuccess(String result) {
//            String decodeStr = "";
//            PackageInfo packageInfo = null;
//            try {
//                decodeStr = URLDecoder.decode(result, "UTF-8");
//                packageInfo = getPackageManager().
//                        getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            } catch (NameNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            String visionInfo[] = decodeStr.split("\\$");
//            int versionCode = Integer.parseInt(visionInfo[0]);
//            mNewVersionDownloadUrl = visionInfo[1];
//            String versionPrompt = "";
//            for (int i = 2; i < visionInfo.length; i++) {
//                versionPrompt = versionPrompt + visionInfo[i] + "\n";
//            }
//            if (packageInfo.versionCode < versionCode) {
//                ShowUtils.showUpdateDilog(ActivityTabs.this, versionPrompt, mSureUpdateClickListener);
//            }
//        }
//
//        @Override
//        public void onError(Throwable ex, boolean isOnCallback) {
////            Log.e(TAG, "onError");
//        }
//
//        @Override
//        public void onCancelled(CancelledException cex) {
//
//        }
//
//        @Override
//        public void onFinished() {
////            Log.e(TAG, "onFinished");
//        }
//    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

//            Intent intent;
//            if (position == PAGE_POSITION_LIVE) {
//                intent = new Intent(Constant.NOTIFY_LIST_ACTION);
//                intent.putExtra(Constant.EXTRA_FRAGMENT_LIVE_COUNT_DOWN_TAG, FragmentLive.COUNT_DOWN_START);
//                mLocalBroadcastManager.sendBroadcast(intent);
//            } else if (position == PAGE_POSITION_HOME){
////                intent = new Intent(Constant.UPDATE_MESSAGE_NOTICE_ACTION);
////                mLocalBroadcastManager.sendBroadcast(intent);
//
////                mFragmentHome.updateMessageNotice();
//
//                intent = new Intent(Constant.NOTIFY_LIST_ACTION);
//                intent.putExtra(Constant.EXTRA_FRAGMENT_LIVE_COUNT_DOWN_TAG, FragmentLive.COUNT_DOWN_STOP);
//                mLocalBroadcastManager.sendBroadcast(intent);
//            }else {
//                intent = new Intent(Constant.NOTIFY_LIST_ACTION);
//                intent.putExtra(Constant.EXTRA_FRAGMENT_LIVE_COUNT_DOWN_TAG, FragmentLive.COUNT_DOWN_STOP);
//                mLocalBroadcastManager.sendBroadcast(intent);
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return mFragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

    }

    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case Constant.SELECT_CHAT_PAGE_ACTION:
                    selectPageChat();
                    break;
                case Constant.SELECT_HOME_PAGE_ACTION:
                    selectPageHome();
                    break;
            }
        }
    }

    private void initView() {
        mDockIconHomeView = (ImageView) findViewById(R.id.dock_home_icon_view);
        mDockIconChatView = (ImageView) findViewById(R.id.dock_chat_icon_view);
        mDockIconLiveView = (ImageView) findViewById(R.id.dock_live_icon_view);
        mDockIconUserView = (ImageView) findViewById(R.id.dock_user_icon_view);

        mDockHomeView = (LinearLayout) findViewById(R.id.dock_home_view);
        mDockChatView = (LinearLayout) findViewById(R.id.dock_chat_view);
        mDockLiveView = (LinearLayout) findViewById(R.id.dock_live_view);
        mDockUserView = (LinearLayout) findViewById(R.id.dock_user_view);

        mDockTitleHomeView = (TextView) findViewById(R.id.dock_home_title_view);
        mDockTitleHomeView.setTextColor(getResources().getColor(R.color.color_primary));
        mDockTitleChatView = (TextView) findViewById(R.id.dock_chat_title_view);
        mDockTitleLiveView = (TextView) findViewById(R.id.dock_live_title_view);
        mDockTitleUserView = (TextView) findViewById(R.id.dock_user_title_view);

        mViewPager = (NoScrollViewPager) findViewById(R.id.view_page);
    }

    private void addRbViewList() {
        mDockIconViews.add(mDockIconHomeView);
        mDockIconViews.add(mDockIconLiveView);
        mDockIconViews.add(mDockIconChatView);
        mDockIconViews.add(mDockIconUserView);

        mDockTitleViews.add(mDockTitleHomeView);
        mDockTitleViews.add(mDockTitleLiveView);
        mDockTitleViews.add(mDockTitleChatView);
        mDockTitleViews.add(mDockTitleUserView);

        mDockViews.add(mDockHomeView);
        mDockViews.add(mDockLiveView);
        mDockViews.add(mDockChatView);
        mDockViews.add(mDockUserView);
    }

    private void addListener() {
        mDockHomeView.setOnClickListener(this);
        mDockChatView.setOnClickListener(this);
        mDockLiveView.setOnClickListener(this);
        mDockUserView.setOnClickListener(this);
    }

    private void setUpViewPager() {
        mFragmentHome = new FragmentHome();
        mFragmentLive = new FragmentLive();
        mFragmentList.add(mFragmentHome);
        mFragmentList.add(mFragmentLive);
        mFragmentList.add(new FragmentChat());
        mFragmentList.add(new FragmentMore());
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(0);
        mViewPager.setNoScroll(true);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }


//    private void checkUpdate() {
//        RequestParams requestParams = new RequestParams(Address.UPDATA_URL);
//        x.http().get(requestParams, mCallback);
//    }


    public NoScrollViewPager getViewPager() {
        return mViewPager;
    }

    public List<Fragment> getFragmentList() {
        return mFragmentList;
    }

    public void selectDockView(int position) {
        for (int i = 0; i < mDockIconViews.size(); i++) {
            mDockIconViews.get(i).setSelected(false);
            mDockTitleViews.get(i).setTextColor(getResources().getColor(R.color.color_text3));
            mDockViews.get(i).setClickable(true);
        }
        mDockIconViews.get(position).setSelected(true);
        mDockTitleViews.get(position).setTextColor(getResources().getColor(R.color.color_primary));
        mDockViews.get(position).setClickable(false);

    }

    private void registerReceiver() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

//        mUpdateMessageNoticeReceiver = mFragmentHome.new UpdateMessageNoticeReceiver();
//        IntentFilter updateMessageNoticeIntentFilter = new IntentFilter();
//        updateMessageNoticeIntentFilter.addAction(Constant.UPDATE_MESSAGE_NOTICE_ACTION);
//        mLocalBroadcastManager.registerReceiver(mUpdateMessageNoticeReceiver, updateMessageNoticeIntentFilter);

//        FragmentLive.NotifyListReceiver notifyListReceiver = mFragmentLive.new NotifyListReceiver();
//        IntentFilter notifyListIntentFilter = new IntentFilter();
//        notifyListIntentFilter.addAction(Constant.NOTIFY_LIST_ACTION);
//        mLocalBroadcastManager.registerReceiver(notifyListReceiver, notifyListIntentFilter);

        mReceiver = new Receiver();
        IntentFilter receiverIntentFilter = new IntentFilter();
        receiverIntentFilter.addAction(Constant.SELECT_CHAT_PAGE_ACTION);
        receiverIntentFilter.addAction(Constant.SELECT_HOME_PAGE_ACTION);
        mLocalBroadcastManager.registerReceiver(mReceiver,receiverIntentFilter);
    }

    private void unregisterReceiver() {
//        mLocalBroadcastManager.unregisterReceiver(mUpdateMessageNoticeReceiver);
//        mLocalBroadcastManager.unregisterReceiver(mNotifyListReceiver);
        mLocalBroadcastManager.unregisterReceiver(mReceiver);
    }

    private void checkHomeDockView() {
        mDockHomeView.setSelected(true);
        mDockHomeView.setClickable(false);
    }

    public void selectPageHome() {
        mViewPager.setCurrentItem(PAGE_POSITION_HOME);
        selectDockView(PAGE_POSITION_HOME);
    }

    public void selectPageLive() {
        mViewPager.setCurrentItem(PAGE_POSITION_LIVE);
        selectDockView(PAGE_POSITION_LIVE);
    }

    public void selectPageChat() {
        mViewPager.setCurrentItem(PAGE_POSITION_CHAT);
        selectDockView(PAGE_POSITION_CHAT);
    }

    public void selectPageMore() {
        mViewPager.setCurrentItem(PAGE_POSITION_MORE);
        selectDockView(PAGE_POSITION_MORE);
    }

//    private void downloadNewVersion() {
//        Intent intent = new Intent();
//        intent.putExtra(global.Constant.EXTRA_DOWNLOAD_URL, mNewVersionDownloadUrl);
//        intent.setClass(ActivityTabs.this, DownloadService.class);
//        startService(intent);
//        Toast.makeText(getApplicationContext(), R.string.downloading_new_version, Toast.LENGTH_SHORT).show();
//    }


}

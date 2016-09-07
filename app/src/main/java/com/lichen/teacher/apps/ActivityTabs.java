package com.lichen.teacher.apps;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lichen.teacher.R;
import com.lichen.teacher.apps.fragments.FragmentCommunity;
import com.lichen.teacher.apps.fragments.FragmentHome;
import com.lichen.teacher.apps.fragments.FragmentUser;
import com.lichen.teacher.util.ShowUtils;
import com.lichen.teacher.view.NoScrollViewPager;
import java.util.ArrayList;
import java.util.List;


//import com.lecloud.download.control.LeDownloadService;

/**
 * update by xiaowu on 2016/8/18.
 */
@SuppressWarnings("deprecation")
public class ActivityTabs extends FragmentActivity implements OnClickListener {

    private static final String TAG = "ActivityTabs";

    private NoScrollViewPager mViewPager;
    private TextView mDockTitleHomeView;
    private TextView mDockTitleCommunityView;
    private TextView mDockTitleUserView;

    //首页底部的导航五个按钮依序,首页,课程分类,行业资讯,考试系统,我
    private ImageView mDockIconHomeView;
    private ImageView mDockIconCommunityView;
    private ImageView mDockIconUserView;

    private LinearLayout mDockHomeView;
    private LinearLayout mDockCommunityView;
    private LinearLayout mDockUserView;

    private List<Fragment> mFragmentList = new ArrayList();
    private List<ImageView> mDockIconViews = new ArrayList<>();
    private List<TextView> mDockTitleViews = new ArrayList<>();
    private String mNewVersionDownloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_view);

        initView();
        addRbViewList();
        addListener();
        setUpViewPager();
//        checkUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dock_home_view:
                mViewPager.setCurrentItem(0);
                selectDockView(0);
                break;
            case R.id.dock_community_view:
                mViewPager.setCurrentItem(1);
                selectDockView(1);
//                ShowUtils.showMsg(ActivityTabs.this, "即将上线");
                break;
            case R.id.dock_user_view:
                mViewPager.setCurrentItem(2);
                selectDockView(2);
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

    private void initView() {
        mDockIconHomeView = (ImageView) findViewById(R.id.dock_home_icon_view);
        mDockIconCommunityView = (ImageView) findViewById(R.id.dock_community_icon_view);
        mDockIconUserView = (ImageView) findViewById(R.id.dock_user_icon_view);

        mDockHomeView = (LinearLayout) findViewById(R.id.dock_home_view);
        mDockHomeView.setSelected(true);
        mDockCommunityView = (LinearLayout) findViewById(R.id.dock_community_view);
        mDockUserView = (LinearLayout) findViewById(R.id.dock_user_view);

        mDockTitleHomeView = (TextView) findViewById(R.id.dock_home_title_view);
        mDockTitleCommunityView = (TextView) findViewById(R.id.dock_community_title_view);
        mDockTitleUserView = (TextView) findViewById(R.id.dock_user_title_view);
        mDockTitleHomeView.setTextColor(getResources().getColor(R.color.color_primary));

        mViewPager = (NoScrollViewPager) findViewById(R.id.view_page);
    }

    private void addRbViewList() {
        mDockIconViews.add(mDockIconHomeView);
        mDockIconViews.add(mDockIconCommunityView);
        mDockIconViews.add(mDockIconUserView);
        mDockTitleViews.add(mDockTitleHomeView);
        mDockTitleViews.add(mDockTitleCommunityView);
        mDockTitleViews.add(mDockTitleUserView);
    }

    private void addListener() {
        mDockHomeView.setOnClickListener(this);
        mDockCommunityView.setOnClickListener(this);
        mDockUserView.setOnClickListener(this);
    }

    private void setUpViewPager() {
//        fragmentList.add(new Activity_One());
//		fragmentList.add(new Activity_Allcourse());
//		fragmentList.add(new Activity_Exam());
//		fragmentList.add(new Activity_Three());
//		fragmentList.add(new Activity_Four());

        //new vision
        mFragmentList.add(new FragmentHome());
        mFragmentList.add(new FragmentCommunity());
        mFragmentList.add(new FragmentUser());
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(0);
        mViewPager.setNoScroll(true);
        mViewPager.setOffscreenPageLimit(5);
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
        }
        mDockIconViews.get(position).setSelected(true);
        mDockTitleViews.get(position).setTextColor(getResources().getColor(R.color.color_primary));

    }

//    private void downloadNewVersion() {
//        Intent intent = new Intent();
//        intent.putExtra(global.Constant.EXTRA_DOWNLOAD_URL, mNewVersionDownloadUrl);
//        intent.setClass(ActivityTabs.this, DownloadService.class);
//        startService(intent);
//        Toast.makeText(getApplicationContext(), R.string.downloading_new_version, Toast.LENGTH_SHORT).show();
//    }


}

package com.lichen.teacher.apps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.lichen.teacher.R;
import com.lichen.teacher.application.MyApplication;
import com.lichen.teacher.global.Constant;
import com.lichen.teacher.util.PreferenceUtils;
import com.lichen.teacher.util.Utils;

/**
 * update by xiaowu on 2016/8/18.
 */
public class ActivityLoading extends Activity {

	private Handler mHandler;
	private int mFirstLaunchTag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loding_view);

		mHandler = new Handler();
		mFirstLaunchTag = PreferenceUtils.getIntPref(Constant.FIRST_LAUNCH_TAG, 0);
		if(mFirstLaunchTag == 0){
			mHandler.postDelayed(new MyAction(), 3000); //5000
			PreferenceUtils.setIntPref(Constant.FIRST_LAUNCH_TAG, 1);
		}else{
			mHandler.postDelayed(new MyAction(), 3000);
		}
	}

	class MyAction implements Runnable {

		public void run() {
			Intent intent = new Intent();
			if(mFirstLaunchTag == 0){
				intent.setClass(ActivityLoading.this, ActivityTabs.class);
				startActivity(intent);
				finish();
				// TODO: 2016/10/30 进入引导页 
			}else{
				boolean isLogin = Utils.isLogin();
				if (isLogin) {
//					intent = new Intent(LoadingActivity.this, ActivitySelectClassType.class);
					MyApplication.USER_ID = PreferenceUtils.getStringPref(Constant.USER_ID, "");
					intent.setClass(ActivityLoading.this, ActivityTabs.class);
					finish();
				} else {
//					intent.putExtra(Constant.EXTRA_IS_FROM_ACTIVITY_LOADING, true);
					intent.setClass(ActivityLoading.this, ActivityUserLogin.class);
				}
				startActivity(intent);
				finish();
			}
		}
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			return false;
//		}
//		return false;
//	}
}

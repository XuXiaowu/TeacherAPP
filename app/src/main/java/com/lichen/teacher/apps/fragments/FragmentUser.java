package com.lichen.teacher.apps.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.lichen.teacher.R;
import com.lichen.teacher.apps.ActivitySetting;
import com.lichen.teacher.apps.ActivityUserEdit;
import com.lichen.teacher.view.CircleImageView;

/**
 * Created by Administrator on 2016/9/2.
 */
public class FragmentUser extends Fragment {

    private View mContentView;
    private RelativeLayout mSettingView;
    private CircleImageView mUserHeadView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_user_view, container, false);
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        initView();
        setViewClickListener();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private View.OnClickListener mFunctionViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.setting_view:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), ActivitySetting.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    private View.OnClickListener mUserHeadClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ActivityUserEdit.class);
            startActivity(intent);
        }
    };

    private void initView(){
        mSettingView = (RelativeLayout) mContentView.findViewById(R.id.setting_view);
        mUserHeadView = (CircleImageView) mContentView.findViewById(R.id.user_head_view);
    }

    private void setViewClickListener() {
        mSettingView.setOnClickListener(mFunctionViewClickListener);
        mUserHeadView.setOnClickListener(mUserHeadClickListener);
    }


}

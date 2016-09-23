package com.lichen.teacher.apps.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.LRecyclerView;
import com.cundong.recyclerview.util.RecyclerViewUtils;
import com.lichen.teacher.R;
import com.lichen.teacher.adapter.HomeAdapter;
import com.lichen.teacher.apps.ActivityAlreadyLive;
import com.lichen.teacher.apps.ActivityAuditingMessage;
import com.lichen.teacher.apps.ActivityAuthentication;
import com.lichen.teacher.apps.ActivityLive;
import com.lichen.teacher.apps.ActivityLiveRequest;
import com.lichen.teacher.apps.ActivityMyClass;
import com.lichen.teacher.apps.ActivityMyProfit;
import com.lichen.teacher.apps.ActivityWaitLive;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowu on 2016/8/31.
 */
public class FragmentHome extends Fragment {

    private View mContentView;
    private LRecyclerView mListView;
    private View mHeadView;
    private RelativeLayout mMessageView;
    private RelativeLayout mAlreadyTeachingView;
    private RelativeLayout mNotTeachingView;
    private RelativeLayout mLiveRequestView;
    private RelativeLayout mClassManageView;
    private RelativeLayout mMyProfitView;
    private ImageView mUserHeadView;
    private Button mAuthBtn;

    private HomeAdapter mHomeAdapter;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private LinearLayoutManager mLayoutManager;
    private List mDataList = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_home_view, container, false);
        return mContentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        initView();
        setViewClickListener();
        setTestData();
        setupListView();
        setViewData();
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
            switch (v.getId()) {
                case R.id.message_function_view:
                    intent = new Intent();
                    intent.setClass(getActivity(), ActivityAuditingMessage.class);
                    startActivity(intent);
                    break;
                case R.id.already_teaching_function_view:
                    intent = new Intent();
                    intent.setClass(getActivity(), ActivityAlreadyLive.class);
                    startActivity(intent);
                    break;
                case R.id.not_teaching_function_view:
                    intent = new Intent();
                    intent.setClass(getActivity(), ActivityWaitLive.class);
                    startActivity(intent);
                    break;
                case R.id.live_request_function_view:
                    intent = new Intent();
//                    intent.setClass(getActivity(), ActivityLiveRequest.class);
                    intent.setClass(getActivity(), ActivityLive.class);
                    startActivity(intent);
                    break;
                case R.id.class_manage_function_view:
                    intent = new Intent();
                    intent.setClass(getActivity(), ActivityMyClass.class);
                    startActivity(intent);
                    break;
                case R.id.my_profit_function_view:
                    intent = new Intent();
                    intent.setClass(getActivity(), ActivityMyProfit.class);
                    startActivity(intent);
                    break;
            }
//            Intent intent = new Intent(getActivity(), ActivityExamView.class);
//            startActivity(intent);
        }
    };

    private View.OnClickListener mAuthBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), ActivityAuthentication.class);
            startActivity(intent);
        }
    };

    private void initView() {
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        mHeadView = layoutInflater.inflate(R.layout.home_head_view, null);
        mListView = (LRecyclerView) mContentView.findViewById(R.id.list_view);
        mMessageView = (RelativeLayout) mHeadView.findViewById(R.id.message_function_view);
        mAlreadyTeachingView = (RelativeLayout) mHeadView.findViewById(R.id.already_teaching_function_view);
        mNotTeachingView = (RelativeLayout) mHeadView.findViewById(R.id.not_teaching_function_view);
        mLiveRequestView = (RelativeLayout) mHeadView.findViewById(R.id.live_request_function_view);
        mClassManageView = (RelativeLayout) mHeadView.findViewById(R.id.class_manage_function_view);
        mMyProfitView = (RelativeLayout) mHeadView.findViewById(R.id.my_profit_function_view);
        mUserHeadView = (ImageView) mHeadView.findViewById(R.id.user_head_view);
        mAuthBtn = (Button) mHeadView.findViewById(R.id.authentication_btn);
    }

    private void setViewClickListener() {
        mMessageView.setOnClickListener(mFunctionViewClickListener);
        mAlreadyTeachingView.setOnClickListener(mFunctionViewClickListener);
        mNotTeachingView.setOnClickListener(mFunctionViewClickListener);
        mLiveRequestView.setOnClickListener(mFunctionViewClickListener);
        mClassManageView.setOnClickListener(mFunctionViewClickListener);
        mMyProfitView.setOnClickListener(mFunctionViewClickListener);
        mAuthBtn.setOnClickListener(mAuthBtnClickListener);
    }

    private void setupListView() {
        mHomeAdapter = new HomeAdapter(getActivity());
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(getActivity(), mHomeAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mHomeAdapter.setDataList(mDataList);
        mListView.setLayoutManager(mLayoutManager);
        mListView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        RecyclerViewUtils.setHeaderView(mListView, mHeadView);
    }

    private void setTestData() {
        for (int i = 0; i < 10; i++) {
            mDataList.add(0);
        }
    }

    private void setViewData() {
        Glide.with(getActivity())
                .load("http://a.hiphotos.baidu.com/zhidao/pic/item/f9dcd100baa1cd11aa2ca018bf12c8fcc3ce2d74.jpg")
                .placeholder(R.drawable.user_default_head)
                .listener(mRequestListener)
                .into(mUserHeadView);
        mUserHeadView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

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

}

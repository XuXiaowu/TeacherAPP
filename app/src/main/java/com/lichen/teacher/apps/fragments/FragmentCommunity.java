//package com.lichen.teacher.apps.fragments;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Environment;
//import android.support.annotation.Nullable;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
//import com.cundong.recyclerview.LRecyclerView;
//import com.lichen.teacher.R;
//import com.lichen.teacher.adapter.BlogAdapter;
//import com.lichen.teacher.apps.ActivityWriteBlog;
//import com.lichen.teacher.global.WebServiceConfigure;
//import com.lichen.teacher.http.HttpService;
//import com.lichen.teacher.models.BlogListResult;
//
//import java.io.File;
//import java.util.List;
//
//
//import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
//import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
//import truecolor.webdataloader.WebListener;
//
///**
// * Created by xiaowu on 2016/9/7.
// */
//public class FragmentCommunity extends Fragment {
//
//    private View mContentView;
//    private LRecyclerView mListView;
//    private TextView mLoadingStatusView;
//    private LinearLayout mLoadingView;
//    private FloatingActionButton mAddBtn;
//
//    private BlogAdapter mBlogAdapter;
//
//    private static final int BLOG_TYPE_ALL = 1;
//    private int mCurrentPage = 1;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        mContentView = inflater.inflate(R.layout.fragment_community_view, container, false);
//        return mContentView;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        initView();
//        setupListView();
//        getData();
//
//    }
//
//    private WebListener mWebListener = new WebListener() {
//        @Override
//        public void onDataLoadFinished(int service, Bundle params, Object result) {
//            if (service == WebServiceConfigure.GET_BLOG_LIST) {
//                if (result != null) {
//                    BlogListResult blogListResult = (BlogListResult) result;
//                    mBlogAdapter.addAll(blogListResult.queryBlogAndReplyList);
//                    setLoadingNone();
//                }
//            }
//        }
//    };
//
//    private BGANinePhotoLayout.Delegate mDelegate = new BGANinePhotoLayout.Delegate() {
//        @Override
//        public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
//            photoPreviewWrapper(ninePhotoLayout);
//        }
//
//        @Override
//        public boolean onLongClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
//            return false;
//        }
//    };
//
//    private View.OnClickListener mAddBtnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
////            Intent intent = BGAPhotoPickerActivity.newIntent(getActivity(), null, 9, null);
//            Intent intent = new Intent(getActivity(), ActivityWriteBlog.class);
//            startActivity(intent);
//        }
//    };
//
//    private void initView() {
//        mListView = (LRecyclerView) mContentView.findViewById(R.id.list_view);
//        mLoadingStatusView = (TextView) mContentView.findViewById(R.id.loading_status_view);
//        mLoadingView = (LinearLayout) mContentView.findViewById(R.id.loading_view);
//        mAddBtn = (FloatingActionButton) mContentView.findViewById(R.id.add_btn);
//        mAddBtn.setOnClickListener(mAddBtnClickListener);
//    }
//
//    private void setupListView() {
//        if (mBlogAdapter != null) return;
//        mBlogAdapter = new BlogAdapter(getActivity());
//        mBlogAdapter.setDelegate(mDelegate);
//        HeaderAndFooterRecyclerViewAdapter hafrva = new HeaderAndFooterRecyclerViewAdapter(getActivity(), mBlogAdapter);
//        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mListView.setAdapter(hafrva);
//        mListView.setPullRefreshEnabled(false);
//    }
//
//    private void getData() {
//        if (mBlogAdapter.getDataList().size() > 0) return;
//        HttpService.getBlogList(BLOG_TYPE_ALL, mCurrentPage, mWebListener);
//    }
//
//    private void setLoadingNone() {
//        mLoadingStatusView.setVisibility(View.GONE);
//        mLoadingView.setVisibility(View.GONE);
//    }
//
//    private void photoPreviewWrapper(BGANinePhotoLayout ninePhotoLayout) {
//        // 要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
//        File downloadDir = new File(Environment.getExternalStorageDirectory(), "LichenTeacher/image");
//        if (ninePhotoLayout.getItemCount() == 1) {
//            // 预览单张图片
//            startActivity(BGAPhotoPreviewActivity.newIntent(getActivity(), downloadDir, ninePhotoLayout.getCurrentClickItem()));
//        } else if (ninePhotoLayout.getItemCount() > 1) {
//            // 预览多张图片
//            startActivity(BGAPhotoPreviewActivity.newIntent(getActivity(), downloadDir, ninePhotoLayout.getData(), ninePhotoLayout.getCurrentClickItemPosition()));
//        }
//    }
//
//}

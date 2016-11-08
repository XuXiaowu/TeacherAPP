//package com.lichen.teacher.adapter;
//
//import android.app.Activity;
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.lichen.teacher.R;
//import com.lichen.teacher.models.BlogListResult;
//import com.lichen.teacher.view.CircleImageView;
//
//
//import java.util.ArrayList;
//
//import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
//
///**
// * Created by xiaowu on 2016/9/7.
// */
//public class BlogAdapter extends ListBaseAdapter<BlogListResult.Blog>{
//
//    private LayoutInflater mLayoutInflater;
//
//    private View.OnClickListener mShareClickListener;
//    private View.OnClickListener mCommentClickListener;
//    private View.OnClickListener mLikeClickListener;
//    private View.OnClickListener mItemClickListener;
//    private BGANinePhotoLayout.Delegate mDelegate;
//
//    private ArrayList<String> mPhotoUrlList;
//
//    public BlogAdapter(Context context) {
//        mLayoutInflater = LayoutInflater.from(context);
//        mContext = context;
//
//        setTestPhotos();
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_blog_view, parent, false));
//    }
//
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        BlogListResult.Blog blog = mDataList.get(position);
//        ViewHolder itemViewHolder = (ViewHolder) holder;
////        ImageLoader.getInstance().displayImage(Address.IMAGE_BASE_URL + blog.avatar, itemViewHolder.mCoverView, mDisplayImageOptions);
//        itemViewHolder.mUserView.setText(blog.showName);
//        itemViewHolder.mContentView.setText(blog.summary);
//        itemViewHolder.mShareLayoutView.setTag(blog);
//        itemViewHolder.mShareLayoutView.setOnClickListener(mShareClickListener);
//        itemViewHolder.mCommentLayoutView.setOnClickListener(mCommentClickListener);
//        itemViewHolder.mLikeLayoutView.setOnClickListener(mLikeClickListener);
//        itemViewHolder.mItemView.setTag(blog);
//        itemViewHolder.mItemView.setOnClickListener(mItemClickListener);
//        itemViewHolder.mPhotosView.init((Activity) mContext);
//        itemViewHolder.mPhotosView.setDelegate(mDelegate);
//        itemViewHolder.mPhotosView.setData(mPhotoUrlList);
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        public RelativeLayout mItemView;
//        public CircleImageView mCoverView;
//        public TextView mUserView;
//        public TextView mContentView;
//        public LinearLayout mShareLayoutView;
//        public LinearLayout mCommentLayoutView;
//        public LinearLayout mLikeLayoutView;
//        public BGANinePhotoLayout mPhotosView;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            mItemView = (RelativeLayout) itemView.findViewById(R.id.item_view);
//            mCoverView = (CircleImageView) itemView.findViewById(R.id.cover_view);
//            mUserView = (TextView) itemView.findViewById(R.id.activity_title_view);
//            mContentView = (TextView) itemView.findViewById(R.id.content_view);
//            mShareLayoutView = (LinearLayout) itemView.findViewById(R.id.share_layout_view);
//            mCommentLayoutView = (LinearLayout) itemView.findViewById(R.id.comment_layout_view);
//            mLikeLayoutView = (LinearLayout) itemView.findViewById(R.id.like_layout_view);
//            mPhotosView = (BGANinePhotoLayout) itemView.findViewById(R.id.photos_view);
//        }
//    }
//
//    public void setShareClickListener(View.OnClickListener onClickListener){
//        mShareClickListener = onClickListener;
//    }
//
//    public void setCommentClickListener(View.OnClickListener onClickListener){
//        mCommentClickListener = onClickListener;
//    }
//
//    public void setLikeClickListener(View.OnClickListener onClickListener) {
//        mLikeClickListener = onClickListener;
//    }
//
//    public void setOnItemClickListener(View.OnClickListener onClickListener) {
//        mItemClickListener = onClickListener;
//    }
//
//    public void setDelegate(BGANinePhotoLayout.Delegate delegate) {
//        mDelegate = delegate;
//    }
//
//    private void setTestPhotos() {
//        mPhotoUrlList = new ArrayList<>();
//        for (int i = 0; i < 9; i++) {
//            mPhotoUrlList.add("http://b.hiphotos.baidu.com/zhidao/pic/item/0823dd54564e9258706bb38b9f82d158ccbf4e73.jpg");
//        }
//    }
//
//}

package com.lichen.teacher.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lichen.teacher.R;

/**
 * Created by xiaoww on 2016/8/20.
 */
public class SchoolAreaDetailsAdapter extends ListBaseAdapter {
    private LayoutInflater mLayoutInflater;

    public SchoolAreaDetailsAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_school_area_details_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


    private class ViewHolder extends RecyclerView.ViewHolder {

//        private ImageView mCoverView;
//        private TextView mTitleView;
//        private TextView mSubjectView;
//        private TextView mTeacherView;

        public ViewHolder(View itemView) {
            super(itemView);
//            mCoverView = (ImageView) itemView.findViewById(R.id.cover_view);
//            mTitleView = (TextView) itemView.findViewById(R.id.title_view);
//            mSubjectView = (TextView) itemView.findViewById(R.id.user_view);
//            mTeacherView = (TextView) itemView.findViewById(R.id.teacher_view);
        }
    }



}

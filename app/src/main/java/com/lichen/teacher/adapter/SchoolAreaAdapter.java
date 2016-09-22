package com.lichen.teacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.lichen.teacher.R;
import com.lichen.teacher.models.SchoolArea;

import java.util.List;

/**
 * Created by xiaowu on 2016/8/8.
 */
public class SchoolAreaAdapter extends BaseExpandableListAdapter {

    private List<SchoolArea> mDataList;
    private LayoutInflater mLayoutInflater;
    private View.OnClickListener mChildItemClickListener;

    public SchoolAreaAdapter(Context context, List list) {
        mLayoutInflater = LayoutInflater.from(context);
        mDataList = list;
    }

    @Override
    public int getGroupCount() {
        return mDataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mDataList.get(groupPosition).citys.length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mDataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mDataList.get(groupPosition).citys[childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_school_area_group_view, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.mArrowView = (ImageView) convertView.findViewById(R.id.arrow_view);
            groupViewHolder.mTextView = (TextView) convertView.findViewById(R.id.text_view);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.mTextView.setText(mDataList.get(groupPosition).province);
        if (isExpanded) groupViewHolder.mArrowView.setImageResource(R.drawable.arrow_gray_up);
        else groupViewHolder.mArrowView.setImageResource(R.drawable.arrow_gray_down);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_school_area_child_view, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.mItemView = (RelativeLayout) convertView;
            childViewHolder.mTextView = (TextView) convertView.findViewById(R.id.text_view);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.mTextView.setText(mDataList.get(groupPosition).citys[childPosition].city);
        childViewHolder.mItemView.setOnClickListener(mChildItemClickListener);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private class GroupViewHolder {
        private ImageView mArrowView;
        private TextView mTextView;
    }

    private class ChildViewHolder {
        private RelativeLayout mItemView;
        private TextView mTextView;
    }

    public void setChildItemClickListener(View.OnClickListener onClickListener) {
        mChildItemClickListener = onClickListener;
    }

}

package com.lichen.teacher.apps;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lichen.teacher.R;
import com.lichen.teacher.apps.fragments.FragmentChatList;

import java.util.ArrayList;
import java.util.List;

public class ActivityChatDetails extends AppCompatActivity {

    private TextView mTitleView;
    private ImageView mBackView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView mSortBtn;

    private ChatAdapter mChatAdapter;
    private List<String> mTitleList;
    private List<FragmentChatList> mFragmentList;
    private int mPagePosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_details_view);

        initView();
        setViewListener();
        setupViewPager();
        setupTabLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(mOnPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mPagePosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private View.OnClickListener mBackViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private View.OnClickListener mSortBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentChatList fcl = mFragmentList.get(mPagePosition);
            fcl.exchangeOrder();
        }
    };

    private class ChatAdapter extends FragmentPagerAdapter {

        public ChatAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);
        }
    }

    private void initView() {
        mTitleView = (TextView) findViewById(R.id.title_view);
        mBackView = (ImageView) findViewById(R.id.back_view);
        mTabLayout = (TabLayout) findViewById(R.id.tab_view);
        mViewPager = (ViewPager) findViewById(R.id.page_view);
        mSortBtn = (ImageView) findViewById(R.id.sort_btn);
    }

    private void setViewListener() {
        mBackView.setOnClickListener(mBackViewClickListener);
        mSortBtn.setOnClickListener(mSortBtnClickListener);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    private void setupViewPager(){
        if (mChatAdapter != null) return;
        FragmentChatList fcl1 = new FragmentChatList();
        FragmentChatList fcl2 = new FragmentChatList();
        FragmentChatList fcl3 = new FragmentChatList();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(fcl1);
        mFragmentList.add(fcl2);
        mFragmentList.add(fcl3);
        mChatAdapter = new ChatAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mChatAdapter);
        mViewPager.setOffscreenPageLimit(3);
    }

    private void setupTabLayout() {
        if (mTitleList != null) return;
        mTitleList = new ArrayList<>();
        mTitleList.add(getString(R.string.chat_all));
        mTitleList.add(getString(R.string.chat_not_replay));
        mTitleList.add(getString(R.string.chat_already_replay));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.setupWithViewPager(mViewPager);
    }



}

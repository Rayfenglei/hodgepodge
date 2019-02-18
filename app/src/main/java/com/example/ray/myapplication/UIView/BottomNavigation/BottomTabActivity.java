package com.example.ray.myapplication.UIView.BottomNavigation;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ray.myapplication.R;
import com.example.ray.myapplication.UIView.Fragments.FragmentOne;
import com.example.ray.myapplication.UIView.Fragments.FragmentThree;
import com.example.ray.myapplication.UIView.Fragments.FragmentTwo;

import java.util.ArrayList;
import java.util.List;

public class BottomTabActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Fragment> pagerList;
    private MyFragmentPagerAdapter1 mAdapter;
    private TabLayout tabLayout;
    private List<String> title;
    private List<Integer> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);
        init();
        mAdapter = new MyFragmentPagerAdapter1(this,getSupportFragmentManager(),pagerList,title);
        mViewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        // 为绑定viewpager后的TabLayout的tabs设置自定义view
        for (int i = 0; i < mAdapter.getCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(mAdapter.setCustomView(i));
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void init(){
        mViewPager = findViewById(R.id.viewpager_my);
        tabLayout = findViewById(R.id.tablayout_my);
        pagerList = new ArrayList<>();
        pagerList.add(new FragmentOne());
        pagerList.add(new FragmentTwo());
        pagerList.add(new FragmentThree());
        title = new ArrayList<>();
        title.add("Strategy");
        title.add("Food");
        title.add("Track");
        images = new ArrayList<>();


    }

    private class MyFragmentPagerAdapter1 extends FragmentPagerAdapter {
        private Context mContext;
        private List<Fragment> mList;
        private List<String> mTitle;
        private int []mImage = {R.drawable.ic_tab_strip_icon_category,R.drawable.ic_tab_strip_icon_feed,R.drawable.ic_tab_strip_icon_pgc};
        private TextView textView;
        private ImageView imageView;
        public MyFragmentPagerAdapter1(Context mContext, FragmentManager fm, List<Fragment> list, List<String> title) {
            super(fm);
            this.mContext = mContext;
            this.mList = list;
            mTitle = title;
        }
        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
        //自定义导航栏
        public View setCustomView(int position) {

            View view = LayoutInflater.from(mContext).inflate(R.layout.test_tab,null);
            textView = view.findViewById(R.id.tv_tab);
            imageView = view.findViewById(R.id.iv_tab);
            textView.setText(mTitle.get(position));
            imageView.setImageResource(mImage[position]);
            return view;
        }
    }
}

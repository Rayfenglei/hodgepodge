package com.example.ray.myapplication.UIView.BottomNavigation;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_tab);
        init();
        mAdapter = new MyFragmentPagerAdapter1(this,getSupportFragmentManager(),pagerList);
        mViewPager.setAdapter(mAdapter);
        //TabLayout与ViewPager关联
        tabLayout.setupWithViewPager(mViewPager);
        //设置监听
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //改变Tab 状态
                (tab.getCustomView().findViewById(R.id.iv_tab)).setSelected(true);
                (tab.getCustomView().findViewById(R.id.tv_tab)).setSelected(true);
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                (tab.getCustomView().findViewById(R.id.iv_tab)).setSelected(false);
                (tab.getCustomView().findViewById(R.id.tv_tab)).setSelected(false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // 为绑定viewpager后的TabLayout的tabs设置自定义view
        for (int i = 0; i < 3; i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(mAdapter.setCustomView(i));
        }
        //打开指定界面
        tabLayout.getTabAt(1).select();
    }

    private void init(){
        mViewPager = findViewById(R.id.viewpager_my);
        tabLayout = findViewById(R.id.tablayout_my);
        pagerList = new ArrayList<>();
        pagerList.add(new FragmentOne());
        pagerList.add(new FragmentTwo());
        pagerList.add(new FragmentThree());
    }

    private class MyFragmentPagerAdapter1 extends FragmentPagerAdapter {
        private List<Fragment> mList;
        private Context mContext;
        public MyFragmentPagerAdapter1(Context mContext, FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
            this.mContext = mContext;
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
        private View setCustomView(int position) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.test_tab,null);
            TextView tv = view.findViewById(R.id.tv_tab);
            ImageView iv = view.findViewById(R.id.iv_tab);
            switch (position){
                case 0:
                    //drawable代码在文章最后贴出
                    iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tab_define_strategy_selector,null));
                    tv.setText("strategy");
                    break;
                case 1:
                    iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tab_define_food_selector,null));
                    tv.setText("food");
                    break;
                case 2:
                    iv.setImageDrawable(mContext.getResources().getDrawable(R.drawable.tab_define_my_selector,null));
                    tv.setText("my");
                    break;
            }
            return view;

        }
    }


}

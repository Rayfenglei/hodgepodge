package com.example.ray.myapplication.UIView.BottomNavigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ray.myapplication.R;
import com.example.ray.myapplication.UIView.Fragments.FragmentFour;
import com.example.ray.myapplication.UIView.Fragments.FragmentOne;
import com.example.ray.myapplication.UIView.Fragments.FragmentThree;
import com.example.ray.myapplication.UIView.Fragments.FragmentTwo;

import java.util.ArrayList;
import java.util.List;

public class BottomRadioActivity extends AppCompatActivity {
    private List<Fragment> pagerList;
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private MyFragmentPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        initPager();
    }

    private void initPager(){
        mRadioGroup = findViewById(R.id.tabs_rg);
        mViewPager = findViewById(R.id.viewpager_home);
        pagerList = new ArrayList<>();
        pagerList.add(new FragmentOne());
        pagerList.add(new FragmentTwo());
        pagerList.add(new FragmentThree());
        pagerList.add(new FragmentFour());

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),pagerList);
        mViewPager.setAdapter(mAdapter);
        //两个监听，实现导航栏与页面联动
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mRadioGroup.setOnCheckedChangeListener(mRadioChangeListener);
    }
    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }
        @Override
        public void onPageSelected(int i) {
            RadioButton radioButton = (RadioButton) mRadioGroup.getChildAt(i);
            radioButton.setChecked(true);
        }
        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private RadioGroup.OnCheckedChangeListener mRadioChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                if (radioGroup.getChildAt(i).getId() == checkedId) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }

        }
    };

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mList;

        public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(mPageChangeListener);
    }
}

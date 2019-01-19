package com.example.ray.myapplication.UIView.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ray.myapplication.R;
import com.example.ray.myapplication.UIView.Fragments.FragmentFour;
import com.example.ray.myapplication.UIView.Fragments.FragmentOne;
import com.example.ray.myapplication.UIView.Fragments.FragmentThree;
import com.example.ray.myapplication.UIView.Fragments.FragmentTwo;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private View view1,view2,view3;
    private ViewPager viewPager,fragmentPager;
    private TabLayout tabLayout;
    //viewpager
    private List<View> viewlist = new ArrayList<>();
    private List<String> titlelist = new ArrayList<>();
    //FragmentPager
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);

        //viewpager
        initView();
        viewPager.setAdapter(new ViewPagerAdapter(viewlist,titlelist));

        //FragmentPager
        initFragment();
        Fragdapter fragdapter = new Fragdapter(getSupportFragmentManager(),fragmentList,this);
        fragmentPager.setAdapter(fragdapter);
        //绑定，要在viewpager设置完数据后，调用此方法，否则不显示 tabs文本
        tabLayout.setupWithViewPager(fragmentPager);
        // 为绑定viewpager后的TabLayout的tabs设置自定义view
        for (int i = 0; i < fragdapter.getCount(); i++) {
            tabLayout.getTabAt(i).setCustomView(fragdapter.setCustomView(i));
        }

        SharedPreferences preferences = getSharedPreferences("data",Context.MODE_PRIVATE);
        int data = preferences.getInt("data",0);
        Log.i("data ViewPagerActivity",""+data);
    }

    private void initView(){
        viewPager = findViewById(R.id.viewpager);
        LayoutInflater inflater = getLayoutInflater();

        view1 = inflater.inflate(R.layout.viewpager_one,null);
        view2 = inflater.inflate(R.layout.viewpager_two,null);
        view3 = inflater.inflate(R.layout.viewpager_three,null);

        viewlist.add(view1);
        viewlist.add(view2);
        viewlist.add(view3);
        titlelist.add("pink");
        titlelist.add("green");
        titlelist.add("blue");

    }

    private void initFragment(){

        fragmentPager = findViewById(R.id.fragmentpager);
        fragmentList.add(new FragmentOne());
        fragmentList.add(new FragmentTwo());
        fragmentList.add(new FragmentThree());
        fragmentList.add(new FragmentFour());

        tabLayout =  findViewById(R.id.tab_layout);
    /*
        tabLayout.addTab(tabLayout.newTab().setCustomView(setCustomView(R.mipmap.ic_wb_cloudy_black_18dp,"云图")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(setCustomView(R.mipmap.ic_wb_incandescent_black_18dp,"白灯")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(setCustomView(R.mipmap.ic_wb_sunny_black_18dp,"太阳")));
        tabLayout.addTab(tabLayout.newTab().setCustomView(setCustomView(R.mipmap.ic_wb_iridescent_black_18dp,"彩虹")));
    */

    }

    public class ViewPagerAdapter extends PagerAdapter{
        private List<View> viewlist;
        private List<String> titlelist;

        ViewPagerAdapter(List<View> viewlist, List<String> titlelist){
            this.viewlist = viewlist;
            this.titlelist = titlelist;
        }
        //返回滑动的个数
        @Override
        public int getCount() {
            return viewlist.size();
        }

        //第一：将当前视图添加到container中，第二：返回当前View
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(viewlist.get(position));
            //把当前新增视图的位置（position）作为Key传过去
            return position;
        }

        //判断instantiateItem(ViewGroup, int)函数所返回来的Key与一个页面视图是否是代表的同一个视图
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            //根据传来的key，找到view,判断与传来的参数View object是不是同一个视图
            return view == viewlist.get((int)Integer.parseInt(object.toString()));
        }

        //从当前container中删除指定位置（position）的View
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(viewlist.get(position));
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titlelist.get(position);
        }
    }

    public class Fragdapter extends FragmentStatePagerAdapter{
        private Context context;
        List<Fragment> fragments;
        String[] tabs = {"云图","白灯","太阳","彩虹"};
        int[] imgs ={R.mipmap.ic_wb_cloudy_black_18dp,R.mipmap.ic_wb_incandescent_black_18dp,
                R.mipmap.ic_wb_sunny_black_18dp,R.mipmap.ic_wb_iridescent_black_18dp};

        Fragdapter(FragmentManager fm,List<Fragment> fragments,Context context){
            super(fm);
            this.fragments = fragments;
            this.context = context;
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            //Tab标题为对应页通过getPageTitle()返回的文本
            return tabs[position];
        }

        private View setCustomView(int position) {
            View view = View.inflate(context, R.layout.viewpager_tablayout, null);
            ImageView iv =  view.findViewById(R.id.iv);
            TextView tv =  view.findViewById(R.id.tv);
            iv.setImageResource(imgs[position]);
            tv.setText(tabs[position]);
            return view;
        }
    }

     /*  private View setCustomView(int drawableId,String tabText) {
        View view = View.inflate(this, R.layout.viewpager_tablayout, null);
        ImageView iv =  view.findViewById(R.id.iv);
        TextView tv =  view.findViewById(R.id.tv);
        iv.setImageResource(drawableId);
        tv.setText(tabText);
        return view;
    }*/

}

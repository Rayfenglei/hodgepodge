package com.example.ray.myapplication.UIView.Fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.ray.myapplication.MapView.GDLocations.GDPoiFragment;
import com.example.ray.myapplication.R;

public class FragmentsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button1,button2,button3;
    private FragmentManager mFragmentManager;
    private FrameLayout framelayout;
    private Fragment mFragmentOne,mFragmentTwo,mfragment,fragmentNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments);
        init();
        initDefaultFragment();
    }

    private void init(){
        button1 = findViewById(R.id.one_fragment_btn);
        button2 = findViewById(R.id.two_fragment_btn);
        button3 = findViewById(R.id.gaode_poi_fragment_btn);
        framelayout = findViewById(R.id.fragment_layout);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        //实例化
        mFragmentOne = new FragmentOne();
        mFragmentTwo = new FragmentTwo();
        mfragment = new GDPoiFragment();
        //获取管理
        mFragmentManager = getSupportFragmentManager();
    }

    //初始化默认fragment的加载
    private void initDefaultFragment() {

        //开启一个事务
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        //add：往碎片集合中添加一个碎片；
        //参数：1.公共父容器的的id  2.fragment的碎片
        fragmentTransaction.add(R.id.fragment_layout, mFragmentOne);
        fragmentTransaction.addToBackStack(null);
        //提交事务
        fragmentTransaction.commit();
        fragmentNow = mFragmentOne;

    }
    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.one_fragment_btn:
                if (mFragmentOne.isAdded()) {
                    //如果fragmentOne已经存在，则隐藏当前的fragment，
                    //然后显示fragmentOne（不会重新初始化，只是加载之前隐藏的fragment）
                    fragmentTransaction.hide(fragmentNow).show(mFragmentOne);
                } else {
                    //如果fragmentOne不存在，则隐藏当前的fragment，
                    //然后添加fragmentOne（此时是初始化）
                    fragmentTransaction.hide(fragmentNow).add(R.id.fragment_layout, mFragmentOne);
                    fragmentTransaction.addToBackStack(null);
                }
                fragmentNow = mFragmentOne;
                fragmentTransaction.commit();
                break;
            case R.id.two_fragment_btn:
                if (mFragmentTwo.isAdded()) {
                    fragmentTransaction.hide(fragmentNow).show(mFragmentTwo);
                } else {
                    fragmentTransaction.hide(fragmentNow).add(R.id.fragment_layout, mFragmentTwo);
                    fragmentTransaction.addToBackStack(null);
                }
                fragmentNow = mFragmentTwo;
                fragmentTransaction.commit();
                break;
            case R.id.gaode_poi_fragment_btn:
                if (mfragment.isAdded()) {
                    fragmentTransaction.hide(fragmentNow).show(mfragment);
                } else {
                    fragmentTransaction.hide(fragmentNow).add(R.id.fragment_layout, mfragment);
                    fragmentTransaction.addToBackStack(null);
                }
                fragmentNow = mfragment;
                fragmentTransaction.commit();
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        //在上面给事务对象添加addToBackStack(null)，
        //下面就可以通过碎片管理对象（mFragmentManager）调用popBackStack()方法来返回上一个碎片（此时碎片管理器只有两个碎片）
        //因为我们是通过add的方法添加fragment的，而且只是添加的两次，其余都是显示和隐藏来实现
        //又因为我们当前占了一个fragment，所以我们只能回退一次，第二次回退就会是空的fragment
        mFragmentManager.popBackStack();
        finish();
    }
}

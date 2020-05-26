package com.example.ray.myapplication.UIView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import org.greenrobot.eventbus.EventBus;

import com.example.ray.myapplication.Function.AlarmManager.AlarmSearchActivity;
import com.example.ray.myapplication.Function.News.GetYwnNewsActivity;
import com.example.ray.myapplication.UIView.Animation.AnimationActivity;
import com.example.ray.myapplication.UIView.Animation.LayoutAnimationActivity;
import com.example.ray.myapplication.UIView.BannerView.BannerActivity;
import com.example.ray.myapplication.Bean.MessageEvent;
import com.example.ray.myapplication.NetWork.ClientActivity;
import com.example.ray.myapplication.NetWork.Download.DownloadActivity;
import com.example.ray.myapplication.UIView.BottomNavigation.BottomRadioActivity;
import com.example.ray.myapplication.UIView.BottomNavigation.BottomTabActivity;
import com.example.ray.myapplication.UIView.Dialogs.DialogsActivity;
import com.example.ray.myapplication.UIView.Fragments.FragmentsActivity;
import com.example.ray.myapplication.NetWork.HttpConnect.OKhttp;
import com.example.ray.myapplication.UIView.LettersNavigation.LettersActivity;
import com.example.ray.myapplication.MapView.LocationsActivity;
import com.example.ray.myapplication.UIView.MaterailU.MaterailActivity;
import com.example.ray.myapplication.DataParse.Parsing.ParseActivity;
import com.example.ray.myapplication.R;
import com.example.ray.myapplication.DataParse.SQLite.OrderActivity;
import com.example.ray.myapplication.Function.ThreadActivity;
import com.example.ray.myapplication.UIView.Fragments.ViewPagerActivity;
import com.example.ray.myapplication.UIView.RecyclerMove.RecyclerMoveActivity;
import com.example.ray.myapplication.UIView.StickyRecycler.StickyRecyclerActivity;
import com.example.ray.myapplication.UIView.Web.GetUrlActivity;
import com.example.ray.myapplication.UIView.recycleview.RecycleActivity;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView1, textView2, textView3, textView4;
    private Button menubtn, mClientBtn, mBannerBtn, mLayoutBtn, mSqlBtn,mAlarmBtn,mSticky,mMove;
    private Button mRecycleBtn, mThreadBtn,mDownBtn,mWebBtn, mParseBtn, mViewPagerBtn;
    private Button mAnmationBtn, mFragmentBtn,mMaterailBtn,mLocations,mLetters,btDialog;
    private Button mRadioBtn,mTabLayoutBtn;
    private ToggleButton toggleButton;
    private Switch aSwitch;
    //private ScrollView scrollView;
    private SharedPreferences preferences;
    private boolean mIsMenuOpen = false;
    private MessageEvent messageEvent;
    private OKhttp oKhttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewItems();
        viewText2Show();
        viewText3Show();
        // scrollViewShow();

        //SharedPreferences
        preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        //处于可编辑
        SharedPreferences.Editor editor = preferences.edit();
        //存放数据
        editor.putInt("data", 10);
        //提交数据
        editor.apply();

        oKhttp = new OKhttp();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.meun_btn:
                if (!mIsMenuOpen) {
                    mIsMenuOpen = true;
                    doOpen();
                } else {
                    mIsMenuOpen = false;
                    doClose();
                }
                break;
            case R.id.client_btn:
                startActivity(new Intent(this, GetYwnNewsActivity.class));
                //添加activity启动和退出动画，必须在startActivity之后 或者在finish()中super.finish()之后
                //overridePendingTransition(R.anim.enter_anima,R.anim.out_anima);
                break;
            case R.id.banner_btn:
                startActivity(new Intent(this, BannerActivity.class));
                break;
            case R.id.sql_btn:
                startActivity(new Intent(this, OrderActivity.class));
                break;
            case R.id.anmation_btn:
                messageEvent = new MessageEvent("hehe");
                //postSticky粘性事件 只能收到最后一条
                EventBus.getDefault().postSticky(messageEvent);
                startActivity(new Intent(this, AnimationActivity.class));
                break;
            case R.id.layoutAnimation_btn:
                startActivity(new Intent(this, LayoutAnimationActivity.class));
                break;

            case R.id.fragment_btn:
                startActivity(new Intent(this, FragmentsActivity.class));
                break;
            case R.id.recycle_btn:
                startActivity(new Intent(this, RecycleActivity.class));
                break;
            case R.id.thread_btn:
                startActivity(new Intent(this, ThreadActivity.class));
                break;
            case R.id.down_btn:
                startActivity(new Intent(this, DownloadActivity.class));
                break;
            case R.id.web_btn:
                startActivity(new Intent(this, GetUrlActivity.class));
                break;
            case R.id.parse_btn:
                startActivity(new Intent(this, ParseActivity.class));
                break;
            case R.id.materail_btn:
                startActivity(new Intent(this, MaterailActivity.class));
                break;
            case R.id.viewpager_btn:
                startActivity(new Intent(this, ViewPagerActivity.class));
                break;
            case R.id.alarm_search_btn:
                startActivity(new Intent(this, AlarmSearchActivity.class));
                break;
            case R.id.locations_btn:
                startActivity(new Intent(this, LocationsActivity.class));
                break;
            case R.id.letters_navigation_btn:
                startActivity(new Intent(this, LettersActivity.class));
                break;
            case R.id.sticky_recycler_btn:
                startActivity(new Intent(this, StickyRecyclerActivity.class));
                break;
            case R.id.move_recycler_btn:
                startActivity(new Intent(this, RecyclerMoveActivity.class));
                break;
            case R.id.bt_dialog_list:
                startActivity(new Intent(this, DialogsActivity.class));
                break;
            case R.id.bt_bottom_radio:
                startActivity(new Intent(this, BottomRadioActivity.class));
                break;
            case R.id.bt_bottom_tab:
                startActivity(new Intent(this, BottomTabActivity.class));
                break;
            default:
                break;
        }

    }

    private void viewItems() {
        textView1 = findViewById(R.id.text);
        textView2 = findViewById(R.id.text2);
        textView3 = findViewById(R.id.text3);

        menubtn = findViewById(R.id.meun_btn);
        mClientBtn = findViewById(R.id.client_btn);
        mBannerBtn = findViewById(R.id.banner_btn);
        mLayoutBtn = findViewById(R.id.layoutAnimation_btn);
        mSqlBtn = findViewById(R.id.sql_btn);
        mAnmationBtn = findViewById(R.id.anmation_btn);

        mFragmentBtn = findViewById(R.id.fragment_btn);
        mRecycleBtn = findViewById(R.id.recycle_btn);
        mThreadBtn = findViewById(R.id.thread_btn);
        mDownBtn = findViewById(R.id.down_btn);
        mWebBtn = findViewById(R.id.web_btn);
        mParseBtn = findViewById(R.id.parse_btn);

        mMaterailBtn = findViewById(R.id.materail_btn);
        mViewPagerBtn = findViewById(R.id.viewpager_btn);
        mAlarmBtn = findViewById(R.id.alarm_search_btn);
        mBannerBtn = findViewById(R.id.banner_btn);
        toggleButton = findViewById(R.id.togglebtn);
        aSwitch = findViewById(R.id.switchbtn);

        mLocations = findViewById(R.id.locations_btn);
        mLetters = findViewById(R.id.letters_navigation_btn);
        //scrollView = findViewById(R.id.scrollView);
        //textView4 = findViewById(R.id.text4);
        mSticky = findViewById(R.id.sticky_recycler_btn);
        mMove = findViewById(R.id.move_recycler_btn);
        btDialog = findViewById(R.id.bt_dialog_list);
        mRadioBtn = findViewById(R.id.bt_bottom_radio);
        mTabLayoutBtn = findViewById(R.id.bt_bottom_tab);

        menubtn.setOnClickListener(this);
        mClientBtn.setOnClickListener(this);
        mBannerBtn.setOnClickListener(this);
        mLayoutBtn.setOnClickListener(this);
        mSqlBtn.setOnClickListener(this);
        mAnmationBtn.setOnClickListener(this);

        mFragmentBtn.setOnClickListener(this);
        mRecycleBtn.setOnClickListener(this);
        mThreadBtn.setOnClickListener(this);
        mDownBtn.setOnClickListener(this);
        mWebBtn.setOnClickListener(this);

        mParseBtn.setOnClickListener(this);
        mMaterailBtn.setOnClickListener(this);
        mViewPagerBtn.setOnClickListener(this);
        mAlarmBtn.setOnClickListener(this);

        mLocations.setOnClickListener(this);
        mLetters.setOnClickListener(this);
        mSticky.setOnClickListener(this);
        mMove.setOnClickListener(this);
        btDialog.setOnClickListener(this);
        mRadioBtn.setOnClickListener(this);
        mTabLayoutBtn.setOnClickListener(this);
    }

    private void viewText2Show() {
        String s2 = "<font color='blue'><b>百度一下：</b></font><br>";
        s2 += "<a href = 'http://www.baidu.com'>百度</a>";
        textView2.setText(Html.fromHtml(s2));
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void viewText3Show() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append("friend" + i + "、");
        }
        String likeUsers = sb.substring(0, sb.lastIndexOf("、")).toString();
        textView3.setMovementMethod(LinkMovementMethod.getInstance());
        textView3.setText(addClickPart(likeUsers), TextView.BufferType.SPANNABLE);

    }

    //textview3
    private SpannableStringBuilder addClickPart(String str) {
        ImageSpan imageSpan = new ImageSpan(MainActivity.this, R.mipmap.ic_launcher);//获取图片
        SpannableString spanStr = new SpannableString("p.");//text显示以 p. 开始
        spanStr.setSpan(imageSpan, 0, 1, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);//在0-1的位置替换图片 imageSpan
        SpannableStringBuilder ssb = new SpannableStringBuilder(spanStr);
        ssb.append(str);
        String[] likes = str.split("、");
        if (likes.length > 0) {
            for (int i = 0; i < likes.length; i++) {
                final String name = likes[i];
                final int start = str.indexOf(name) + spanStr.length();
                ssb.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, name, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        //删除下划线，设置字体颜色为蓝色
                        ds.setColor(Color.BLUE);
                        ds.setUnderlineText(false);
                    }
                }, start, start + name.length(), 0);
            }
        }
        return ssb.append("等" + likes.length + "个人觉得很赞");
    }

    /*    private void scrollViewShow(){
            StringBuilder data = new StringBuilder();
            for (int i = 0; i < 30;i++){
                data.append("data +"+i+"\n");
            }
            textView4.setText(data.toString());
        }*/

    //菜单栏打开和关闭的动画
    private void doAnimationOpen(View v, int index, int total, int radius) {
        if (v.getVisibility() != View.VISIBLE) {
            v.setVisibility(View.VISIBLE);
        }
        //计算弧度和坐标
        double degree = Math.toRadians(90)/(total - 1) * index;
        int translationX = (int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(v,"translationX",0,translationX),
                ObjectAnimator.ofFloat(v,"translationY",0,translationY),
                ObjectAnimator.ofFloat(v,"scaleX",0f,1f),
                ObjectAnimator.ofFloat(v,"scaleY",0f,1f),
                ObjectAnimator.ofFloat(v,"alpha",0f,1f)
                );
        if (radius ==200){
            animatorSet.setStartDelay(50*index);
        }else if (radius ==350){
            animatorSet.setStartDelay(50*(index+5));
        }else {
            animatorSet.setStartDelay(50*10);
        }

        animatorSet.setDuration(500).start();
    }
    private void doOpen()
    {
        doAnimationOpen(mClientBtn,0,5,200);
        doAnimationOpen(mBannerBtn,1,5,200);
        doAnimationOpen(mLayoutBtn,2,5,200);
        doAnimationOpen(mSqlBtn,3,5,200);
        doAnimationOpen(mAnmationBtn,4,5,200);

        doAnimationOpen(mFragmentBtn,0,5,350);
        doAnimationOpen(mRecycleBtn,1,5,350);
        doAnimationOpen(mThreadBtn,2,5,350);
        doAnimationOpen(mDownBtn,3,5,350);
        doAnimationOpen(mWebBtn,4,5,350);

        doAnimationOpen(mParseBtn,2,5,500);
        doAnimationOpen(mMaterailBtn,1,5,500);
        doAnimationOpen(mViewPagerBtn,3,5,500);
        doAnimationOpen(mLocations,0,5,500);
        doAnimationOpen(mLetters,4,5,500);
    }
    private void doAnimationClose(View v, int index, int total, int radius) {
        if (v.getVisibility() != View.VISIBLE) {
            v.setVisibility(View.VISIBLE);
        }
        double degree = Math.toRadians(90)/(total - 1) * index;
        int translationX = (int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(v,"translationX",translationX,0),
                ObjectAnimator.ofFloat(v,"translationY",translationY,0),
                ObjectAnimator.ofFloat(v,"scaleX",1f,0.01f),
                ObjectAnimator.ofFloat(v,"scaleY",1f,0.01f),
                ObjectAnimator.ofFloat(v,"alpha",1f,0f)
        );
        animatorSet.setDuration(500).start();
    }
    private void doClose()
    {
        doAnimationClose(mClientBtn,0,5,200);
        doAnimationClose(mBannerBtn,1,5,200);
        doAnimationClose(mLayoutBtn,2,5,200);
        doAnimationClose(mSqlBtn,3,5,200);
        doAnimationClose(mAnmationBtn,4,5,200);

        doAnimationClose(mFragmentBtn,0,5,350);
        doAnimationClose(mRecycleBtn,1,5,350);
        doAnimationClose(mThreadBtn,2,5,350);
        doAnimationClose(mDownBtn,3,5,350);
        doAnimationClose(mWebBtn,4,5,350);

        doAnimationClose(mParseBtn,2,5,500);
        doAnimationClose(mMaterailBtn,1,5,500);
        doAnimationClose(mViewPagerBtn,3,5,500);
        doAnimationClose(mLocations,0,5,500);
        doAnimationClose(mLetters,4,5,500);
    }

    /*
    当activity不再为用户可见时调用此方法
    */
    @Override
    protected void onStop() {
        Log.i("MainActivity.this","onStop");
        super.onStop();
    }
    /*
    在activity停止后，在再次启动之前被调用
    */
    @Override
    protected void onRestart() {
        Log.i("MainActivity.this","onRestart");
        super.onRestart();
    }
    /*
    暂停，可见，但不可操作，此方法主要用来将未保存的变化进行持久化，停止类似动画这样耗费CPU的动作等
    */
    @Override
    protected void onPause() {
        Log.i("MainActivity.this","onPause");
        super.onPause();
    }
    /*
    取得控制权,可以对此Activity进行操作此时activity位于堆栈顶部，并接受用户输入
    */
    @Override
    protected void onResume() {
        Log.i("MainActivity.this","onResume");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.i("MainActivity.this","onDestroy");
        super.onDestroy();
    }


}

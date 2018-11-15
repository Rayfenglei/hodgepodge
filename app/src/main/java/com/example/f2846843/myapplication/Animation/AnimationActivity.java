package com.example.f2846843.myapplication.Animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f2846843.myapplication.Bean.MessageEvent;
import com.example.f2846843.myapplication.MainActivity;
import com.example.f2846843.myapplication.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/*
* 参考文章 CSDN启舰 https://blog.csdn.net/harvic880925/article/details/50995268
* */
public class AnimationActivity extends Activity implements View.OnClickListener{

    private Button mScaleBtn,mAlphaBtn,mTranslateBtn,mRotateBtn,mAllBtn,mGatherBtn;
    private TextView mShowAnima,mSetShow;
    private DrawPoint mDrawPoint,mDrawPoint1;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        init();
        //EventBus
        EventBus.getDefault().register(this);

    }
    public void init(){
        mScaleBtn = findViewById(R.id.scale_btn);
        mAlphaBtn = findViewById(R.id.propertyvalues_btn);
        mTranslateBtn = findViewById(R.id.keyframe_btn);
        mRotateBtn = findViewById(R.id.rotate_btn);
        mAllBtn = findViewById(R.id.all_anima_btn);
        mShowAnima = findViewById(R.id.show_animation_text);
        mSetShow = findViewById(R.id.show_set_text);
        mDrawPoint = findViewById(R.id.drawpoint);
        mGatherBtn = findViewById(R.id.animationset_btn);

        mScaleBtn.setOnClickListener(this);
        mAlphaBtn.setOnClickListener(this);
        mTranslateBtn.setOnClickListener(this);
        mRotateBtn.setOnClickListener(this);
        mAllBtn.setOnClickListener(this);
        mGatherBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.scale_btn:
                animation  = AnimationUtils.loadAnimation(this, R.anim.scaleanima);
                mShowAnima.startAnimation(animation);
  /*            doMobileAnimatoin();
                doColorAnimation();
                doCharAnimation();*/
                mDrawPoint.doPointAnimation();
                doRotation();
                doAlpha();
                break;
            case R.id.propertyvalues_btn:
                //animation  = AnimationUtils.loadAnimation(this, R.anim.alphaanima);
                //mShowAnima.startAnimation(animation);
                doPropertyValuesHolder();
                break;
            case R.id.keyframe_btn:
                //animation  = AnimationUtils.loadAnimation(this, R.anim.translateanima);
                //mShowAnima.startAnimation(animation);
                doKeyFrame();
                break;
            case R.id.rotate_btn:
                //animation  = AnimationUtils.loadAnimation(this, R.anim.rotateanima);
                //mShowAnima.startAnimation(animation);
                break;
            case R.id.all_anima_btn:
                animation  = AnimationUtils.loadAnimation(this, R.anim.addall);
                mShowAnima.startAnimation(animation);
                break;
            case R.id.animationset_btn:
                doAnimationSet();
                break;
            default:
                break;

        }
    }
    /*
     位置移动动画
     */
    public void doMobileAnimatoin(){
        //动画运动值 0-400-300-500-350 数值变化区间
        ValueAnimator mobileAnimator = ValueAnimator.ofInt(0,400,300,500,350);
        //监听计算过程
        mobileAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //数值变化过程的值
                int curValue = (int)valueAnimator.getAnimatedValue();
                // layout(int l, int t, int r, int b) 左端、顶端、右端和底端距父控件坐标的距离
                mShowAnima.layout(curValue,curValue,curValue+mShowAnima.getWidth(),curValue+mShowAnima.getHeight());
                Log.i("curValue width height"," "+curValue+" "+mShowAnima.getWidth()+" "+mShowAnima.getHeight());
            }
        });
        //监听动画过程
        mobileAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.i("haha","animation start");
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                Log.i("haha","animation end");
            }
            @Override
            public void onAnimationCancel(Animator animator) {
                Log.i("haha","animation cancel");
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
                Log.i("haha","animation repeat");
            }
        } );
        //设置插值器
        mobileAnimator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float v) {
                return v;
                //return 1-v;  //动画逆向进行
            }
        });
        mobileAnimator.setRepeatCount(2);//设置循环次数 ValueAnimation.INFINITE表示无限循环
        mobileAnimator.setRepeatMode(ValueAnimator.REVERSE);//用于设置循环模式，取值为ValueAnimation.RESTART时,表示正序重新开始，当取值为ValueAnimation.REVERSE表示倒序重新开始
       // mobileAnimator.setStartDelay(2000);//动画开始的延迟时间
        //动画时间
        mobileAnimator.setDuration(2000);

        mobileAnimator.start();
    }
    /*
    颜色变化动画
    */
    public void doColorAnimation(){
        final ValueAnimator colorAnimation = ValueAnimator.ofInt(0xffffff00,0xff0000ff);
        //设置动画类型
        //colorAnimation.setEvaluator(new ArgbEvaluator()); //默认颜色变化
        colorAnimation.setEvaluator(new ColorEvaluator());  //自定义颜色变化

        colorAnimation.setDuration(2000);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int curValue = (int) colorAnimation.getAnimatedValue();
                mShowAnima.setBackgroundColor(curValue);
               //
            }
        });
        colorAnimation.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                //透明度变化
                mShowAnima.startAnimation(AnimationUtils.loadAnimation(AnimationActivity.this, R.anim.alphaanima));

            }
        });
        colorAnimation.setRepeatCount(2);//设置循环次数 ValueAnimator.INFINITE表示无限循环
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.start();
    }
    /*
    自定义颜色变化过程
    */
    public class ColorEvaluator implements TypeEvaluator{

        public Object evaluate(float fraction, Object startValue, Object endValue){
            int startInt = (Integer) startValue;
            int startA = (startInt >> 24);
            int startR = (startInt >> 16) & 0xCC;
            int startG = (startInt >> 8) & 0xCC;
            int startB = startInt & 0xCC;

            int endInt = (Integer) endValue;
            int endA = (endInt >> 24);
            int endR = (endInt >> 16) & 0xCC;
            int endG = (endInt >> 8) & 0xCC;
            int endB = endInt & 0xCC;

            return (int)((startA + (int)(fraction * (endA - startA))) << 24) |
                    (int)((startR + (int)(fraction * (endR - startR))) << 16) |
                    (int)((startG + (int)(fraction * (endG - startG))) << 8) |
                    (int)((startB + (int)(fraction * (endB - startB))));
        }

    }

    /*
    字符变化动画
    */
    public void doCharAnimation()
    {
        final ValueAnimator charAnimation = ValueAnimator.ofObject(new CharEvaluator(),new Character('A'),new Character('Z'));
        charAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                char text = (char) charAnimation.getAnimatedValue();
                Log.i("Scaletext",""+text);
                mShowAnima.setText(String.valueOf(text));
            }
        });
        charAnimation.setDuration(6000);
        charAnimation.start();
    }
    public class CharEvaluator implements TypeEvaluator<Character>{
        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            int startInt = (int) startValue;
            int endInt = (int) endValue;
            int curInt = (int) (startInt + fraction * (endInt - startInt));
            char result = (char) curInt;
            return result;
        }
    }
    /*ObjectAnimator
    透明度：alpha
    旋转：rotation rotationX rotationY
    平移：translationX translationY
    缩放：scaleX scaleY
    */
    public void doRotation(){
        ObjectAnimator rotationAnimaton = ObjectAnimator.ofFloat(mShowAnima,"rotation",0,270,0);
        rotationAnimaton.setRepeatCount(ValueAnimator.INFINITE);
        rotationAnimaton.setDuration(2000);
        rotationAnimaton.start();
    }
    public void doAlpha(){
        ObjectAnimator animaton = ObjectAnimator.ofFloat(mDrawPoint,"alpha",0,1);
        animaton.setRepeatCount(ValueAnimator.INFINITE);
        animaton.setDuration(1000);
        animaton.start();
    }


    /*
    PropertyValuesHolder 保存动画的设置
    可以实现多种动画一起进行
    */
    public void doPropertyValuesHolder() {
        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("BackgroundColor", 0xffffffff, 0xffff00ff, 0xffffff00, 0xff0000ff);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mShowAnima, rotationHolder, colorHolder);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    /*
    关键帧
   */
    public void doKeyFrame(){
         //左右震动效果
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        //frame0.setInterpolator(new BounceInterpolator());//添加插值器
        //frame0.setFraction(0);设置fraction值
        //frame0.setValue(0);设置value值
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, -20f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, -20f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);

        PropertyValuesHolder frameHolder1 = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame2, frame4, frame6, frame8, frame10);

        //scaleX放大1.1倍
        Keyframe scaleXframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleXframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleXframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleXframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleXframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleXframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder frameHolder2 = PropertyValuesHolder.ofKeyframe("ScaleX",scaleXframe0,scaleXframe2,scaleXframe4,scaleXframe6,scaleXframe8,scaleXframe10);

        //scaleY放大1.1倍
        Keyframe scaleYframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleYframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleYframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleYframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleYframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleYframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder frameHolder3 = PropertyValuesHolder.ofKeyframe("ScaleY",scaleYframe0,scaleYframe2,scaleYframe4,scaleYframe6,scaleYframe8,scaleYframe10);

        Animator animator = ObjectAnimator.ofPropertyValuesHolder(mShowAnima, frameHolder1,frameHolder2,frameHolder3);
        animator.setDuration(3000);
        animator.start();
    }

    /*
    ObjectAnimation + AnimationSet
    多种动画一起进行
    */
    public void doAnimationSet(){

        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mShowAnima,"alpha",1,0,1);
        animator1.setDuration(1000000);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mSetShow,"translationX",mShowAnima.getWidth(),mShowAnima.getWidth()+100,mShowAnima.getWidth());
        animator2.setStartDelay(1000);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mShowAnima,"scaleY",0,2,1);

        AnimatorSet animationSet = new AnimatorSet();

        //执行顺序 3->1&2
        animationSet.play(animator1).with(animator2).after(animator3);

        //Set设置属性后，原其余动画的相同属性被覆盖，执行Set的设置 setDuration() setInterpolator()
        //如果Set没有统一设置属性，则按照各自的动画属性执行动画
        animationSet.setDuration(2000);

        //特殊setStartDelay
        //animationSet的动画延迟 == animationSet.setStartDelay + 第一个动画的setStartDelay
        animationSet.setStartDelay(2000);//设置开始延迟的时间不会覆盖单个动画的延时

        //animationSet.playTogether(animator1,animator2,animator3); //一起播放
        //animationSet.playSequentially(animator1,animator2,animator3);//依次执行动画
        animationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.i("animationSet","start");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.i("animationSet","Cancel");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.i("animationSet","End");
            }
        });
        animationSet.setTarget(mSetShow);//设置动画执行目标控件，其余控件的动画转目标控件执行，原控件不执行
        animationSet.start();
    }

    //EventBus sticky粘性事件 只能收到最后一条
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Event(MessageEvent messageEvent)
    {
        //mShowAnima.setText(messageEvent.getmMassage());
        Toast.makeText(AnimationActivity.this,""+messageEvent.getmMassage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

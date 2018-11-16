package com.example.ray.myapplication.Animation;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.example.ray.myapplication.Bean.AnimaPoint;
import com.example.ray.myapplication.R;

public class DrawPoint extends View {
    public AnimaPoint mPoint;
    public float mCX;
    public float mCY;
    public DrawPoint(Context context, AttributeSet attr){
        super(context,attr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPoint != null){
            Paint mPaint = new Paint();
            mPaint.setAntiAlias(true);          //抗锯齿
            mPaint.setColor(getResources().getColor(R.color.colorPrimary));//画笔颜色
            mPaint.setStyle(Paint.Style.FILL);  //画笔风格
            canvas.drawCircle(500,800,mPoint.getRadius(),mPaint);
        }
    }

    public void doPointAnimation(){
        final ValueAnimator pointAnimation =ValueAnimator.ofObject(new PointEvaluator(),new AnimaPoint(0),new AnimaPoint(200));
        pointAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mPoint = (AnimaPoint) pointAnimation.getAnimatedValue();

                invalidate();
            }
        });
        pointAnimation.setDuration(1000);
        pointAnimation.setInterpolator(new BounceInterpolator());
        pointAnimation.setRepeatCount(ValueAnimator.INFINITE);//设置循环次数 ValueAnimation.INFINITE表示无限循环
        pointAnimation.setRepeatMode(ValueAnimator.REVERSE);
        pointAnimation.start();
    }


    public class PointEvaluator implements TypeEvaluator<AnimaPoint>{

        @Override
        public AnimaPoint evaluate(float fraction, AnimaPoint startValue, AnimaPoint endValue) {
            int start = startValue.getRadius();
            int end  = endValue.getRadius();
            int curValue = (int)(start + fraction * (end - start));
            return new AnimaPoint(curValue);

        }
    }

}

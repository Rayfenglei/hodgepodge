package com.example.ray.myapplication.Bean;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.ray.myapplication.R;

public class MyView extends View {
    private Paint mPaint;
    private Context context;
    public MyView(Context context){
        super(context);
        this.context = context;
        init();
    }
    public MyView(Context context, AttributeSet attrs){
        super(context,attrs);
        this.context = context;
        init();
    }
    public MyView(Context context, AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        this.context = context;
        init();
    }
    private void init(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);          //抗锯齿
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));//画笔颜色
        mPaint.setStyle(Paint.Style.FILL);  //画笔风格
        mPaint.setTextSize(36);             //绘制文字大小，单位px
        mPaint.setStrokeWidth(5);           //画笔粗细

    }



    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(getResources().getColor(R.color.colorAccent));   //设置画布背景颜色

        canvas.drawCircle(200, 200, 100, mPaint);           //画实心圆


        /*
         //画点
        drawPoint(float x, float y, Paint paint)
        //画线
        drawLine(float startX, float startY, float stopX, float stopY, Paint paint)

        //画多点 或者多条直线
        float []pts={x1,y1,x2,y2,x3,y3....};两两组合成一个坐标
        drawPoints(float[] pts, Paint paint)
        drawLines(float[] pts, Paint paint)

        //矩形构造
        RectF(float left, float top, float right, float bottom)
        Rect(int left, int top, int right, int bottom)

        //画矩形
        drawRect(float left, float top, float right, float bottom, Paint paint);
        drawRect(RectF rect, Paint paint)
        drawRect(Rect r, Paint paint)

        //圆角矩形
        void drawRoundRect (RectF rect, float rx, float ry, Paint paint)
        float rx:生成圆角的椭圆的X轴半径
        float ry:生成圆角的椭圆的Y轴半径

        //画椭圆
        drawOval (RectF oval, Paint paint)
        RectF oval：用来生成椭圆的矩形

        //画弧
        drawArc (RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
        RectF oval:生成椭圆的矩形
        float startAngle：弧开始的角度，以X轴正方向为0度
        float sweepAngle：弧持续的角度
        boolean useCenter:是否有弧的两边，True，还两边，False，只有一条弧


        drawText("最喜欢看曹神日狗了~",50,50,mPaint);    //绘制文字

        Path path = new Path();
        path.moveTo(50,50);//起点
        path.lineTo(100, 100);//第一条线的终点，第二条的起点
        path.lineTo(200, 200);
        path.lineTo(300, 300);
        path.close();//闭环，路径的首尾相连
        drawPath(path,paint);

        canvas.drawTextOnPath("最喜欢看曹神日狗了~", path, 50, 50, mPaint);    //绘制文字*/
    }
}

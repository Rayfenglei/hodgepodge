package com.example.ray.myapplication.Bean;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ImageButton;

import com.example.ray.myapplication.R;

public class MyButton extends android.support.v7.widget.AppCompatImageButton {
    private static final int INVALIDATE_DURATION = 15;     //每次刷新的时间间隔
    private static int DIFFUSE_GAP = 10;                  //扩散半径增量
    private static int TAP_TIMEOUT;                         //判断点击和长按的时间

    private int viewWidth, viewHeight;                   //控件宽高
    private int pointX, pointY;                          //控件原点坐标（左上角）
    private int maxRadio;                             //扩散的最大半径
    private int shaderRadio;                        //扩散的半径

    private Paint bottomPaint, colorPaint;          //画笔:背景和水波纹
    private boolean isPushButton;                 //记录是否按钮被按下

    private int eventX, eventY;                  //触摸位置的X,Y坐标
    private long downTime = 0;                 //按下的时间

    public MyButton (Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
        initPaint();
        TAP_TIMEOUT = ViewConfiguration.getLongPressTimeout();
    }

  /*
    初始化画笔
    */
    private void initPaint()
    {
        colorPaint = new Paint();
        bottomPaint = new Paint();
        colorPaint.setColor(getResources().getColor(R.color.colorPrimary));
        bottomPaint.setColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        int lastX = 0;
        int lastY = 0;
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                    if (downTime ==0) downTime = SystemClock.elapsedRealtime();
                    eventX = (int) event.getX();
                    eventY = (int) event.getY();
                    countMaxRadio();
                    isPushButton = true;
                    postInvalidateDelayed(INVALIDATE_DURATION);

                    //记录触摸点的坐标
                    lastX = rawX;
                    lastY = rawY;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (SystemClock.elapsedRealtime() - downTime < TAP_TIMEOUT)
                {
                 DIFFUSE_GAP = 30;
                 postInvalidate();
                }else {
                    clearData();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //计算偏移量
                int officeX = rawX - lastX;
                int officeY = rawY - lastY;
                //在当前的left,top,right,bottom基础上加上偏移量
                layout(getLeft()+officeX,getTop()+officeY,getRight()+officeX,getBottom()+officeY);
                //重新设置初始值
                lastX = rawX;
                lastY = rawY;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(!isPushButton) return; //如果按钮没有被按下则返回
        //绘制按下后的整个背景
        canvas.drawRect(pointX, pointY, pointX + viewWidth, pointY + viewHeight, bottomPaint);
        canvas.save();
        //绘制扩散圆形背景
        canvas.clipRect(pointX, pointY, pointX + viewWidth, pointY + viewHeight);
        canvas.drawCircle(eventX, eventY, shaderRadio, colorPaint);
        canvas.restore();
        //直到半径等于最大半径
        if(shaderRadio < maxRadio){
            postInvalidateDelayed(INVALIDATE_DURATION,
                    pointX, pointY, pointX + viewWidth, pointY + viewHeight);
            shaderRadio += DIFFUSE_GAP;
        }else{
            clearData();
        }
    }

    private void countMaxRadio()
    {
        if (viewWidth > viewHeight){
            if (eventX < viewWidth/2){
                maxRadio = viewWidth - eventX;
            }else {
                maxRadio = viewWidth/2 + eventX;
            }
        }else {
            if (eventY < viewHeight /2)
            {
                maxRadio = viewHeight - eventY;
            }else {
                maxRadio = viewHeight/2 + eventY;
            }
        }

    }

    private void clearData(){
        downTime = 0;
        DIFFUSE_GAP = 10;
        isPushButton = false;
        shaderRadio = 0;
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.viewWidth = w;
        this.viewHeight = h;
    }
}

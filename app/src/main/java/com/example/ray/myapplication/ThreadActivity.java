package com.example.ray.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadActivity extends Activity {

    private final int CORE_POOL_SIZE = 4;//核心线程数
    private final int MAX_POOL_SIZE = 5;//最大线程数
    private final long KEEP_ALIVE_TIME = 10;//空闲线程超时时间
    private ThreadPoolExecutor executorPool;
    private int songIndex = 0;

    private Button mButtonDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        //创建线程池
        executorPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE  //核心线程池大小，也就是是线程池中的最小线程数
                ,MAX_POOL_SIZE  //最大线程池大小,当活动线程数达到这个值，后续任务会被阻塞
                ,KEEP_ALIVE_TIME
                ,TimeUnit.SECONDS //线程池维护线程所允许的空闲时间的单位
                ,new LinkedBlockingQueue<Runnable>() //线程池的缓存队列
                ,Executors.defaultThreadFactory() //线程工厂，为线程池提供创建新线程的功能，它是一个接口，只有一个方法：Thread newThread(Runnable r)
                ,new ThreadPoolExecutor.AbortPolicy());

        mButtonDown = findViewById(R.id.button_down);
        mButtonDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songIndex++;
                    try {

                        //添加线程到线程池中
                        executorPool.execute(new WorkerThread("歌曲"+songIndex));
                        executorPool.execute(new WorkerThread2("歌曲"+songIndex));

                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }
        });

        SharedPreferences preferences = getSharedPreferences("data",Context.MODE_PRIVATE);
        int data = preferences.getInt("data",0);
        Log.i("data ThreadActivity",""+data);
    }

    //线程执行的具体任务
    public class WorkerThread implements Runnable{
        private String threadName;
        WorkerThread(String threadName){
            this.threadName = threadName;
        }

        @Override
            public synchronized void run() {
            boolean flag = true;
            try {
                while (flag) {
                    String tn = Thread.currentThread().getName();
                    //模拟耗时操作
                    Thread.sleep(5000);
                    Log.e("threadtest", "线程\"" + tn + "\"耗时了(" + 5 + "秒)下载了第<" + threadName + ">");
                    //下载完了跳出循环
                    flag = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //开启UI线程 更新UI操作
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }

    }
    public class WorkerThread2 implements Runnable{
        private String threadName;
        WorkerThread2(String threadName){
            this.threadName = threadName;
        }

        @Override
        public synchronized void run() {
            boolean flag = true;
            try {
                while (flag) {
                    String tn = Thread.currentThread().getName();
                    //模拟耗时操作
                    Thread.sleep(5000);
                    Log.e("threadtest", "线程2\"" + tn + "\"耗时了(" + 5 + "秒)下载了第<" + threadName + ">");
                    //下载完了跳出循环
                    flag = false;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

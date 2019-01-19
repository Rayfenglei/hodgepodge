package com.example.ray.myapplication.UIView.RemoteViews;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.example.ray.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {
    private Timer timer;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updataView();
            }
        },0,1000);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updataView(){

        String time = simpleDateFormat.format(new Date());
        RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.remoteview);
        remoteView.setTextViewText(R.id.widget_main_tv_time,time);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName componentName = new ComponentName(getApplicationContext(), WidgetProvider.class);
        appWidgetManager.updateAppWidget(componentName, remoteView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer = null;
    }
}

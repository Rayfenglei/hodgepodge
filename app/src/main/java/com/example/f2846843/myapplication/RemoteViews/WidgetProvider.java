package com.example.f2846843.myapplication.RemoteViews;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WidgetProvider extends AppWidgetProvider {
    public WidgetProvider() {
        super();
    }
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        Log.i("remote","onEnabled");
        context.startService(new Intent(context, TimerService.class));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.i("remote","onDeleted");
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onDisabled(Context context) {
        Log.i("remote","onDisabled");
        super.onDisabled(context);
        context.stopService(new Intent(context, TimerService.class));
    }
}

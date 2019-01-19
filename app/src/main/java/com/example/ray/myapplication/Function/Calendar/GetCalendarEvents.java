package com.example.ray.myapplication.Function.Calendar;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.text.format.DateUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetCalendarEvents{

    private static final String[] PROJECTION = new String[]{
            CalendarContract.Instances.TITLE, // 0
            CalendarContract.Instances.ALL_DAY, // 1
            CalendarContract.Instances.BEGIN, // 2
            CalendarContract.Instances.END, // 3
            CalendarContract.Instances.EVENT_ID, // 4
    };

    private EventsAdapter eventsAdapter;

    public ArrayList<EventsItem> getcalendar(Context mContext){

        ArrayList<EventsItem> eventsItems = new ArrayList<>();

        //Cursor eventCursor = context.getContentResolver().query(Uri.parse("content://com.android.calendar/events"), null, null, null, null);

        Uri.Builder builder = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
        long now = new Date().getTime();
        ContentUris.appendId(builder, now);
        ContentUris.appendId(builder, now + DateUtils.WEEK_IN_MILLIS);
        Cursor eventCursor = mContext.getContentResolver().query(builder.build(),PROJECTION,null,
                null, CalendarContract.Instances.BEGIN + " ASC");

        while (eventCursor.moveToNext()){
            EventsItem event = new EventsItem();
            event.setEventTitle(eventCursor.getString(0));//标题
            event.setAllday(eventCursor.getInt(1) != 0);//是否全天
            event.setStartTime(timeStamp2Time(eventCursor.getLong(2)));//开始时间
            event.setEndTime(timeStamp2Time(eventCursor.getLong(3)));//结束时间
            event.setStartDate(timeStamp2Date(eventCursor.getLong(2)));//开始日期
            event.setEndDate(timeStamp2Date(eventCursor.getLong(3)));//结束日期
            event.setEventID(eventCursor.getLong(4));//事件ID
            //判断是否是当天的事件
            if (isTodayEvent(timeStamp2Date(eventCursor.getLong(2)))){
                eventsItems.add(event);
                Log.i("GetCalendarEvents 1 :",event.toString());
            }
        }
        return eventsItems;
    }


    /**
     * 时间戳转换为字符串
     * @param time:时间戳
     * @return
     */
    private String timeStamp2Time(long time) {
        String format = "HH:mm"; //"yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    private String timeStamp2Date(long time) {
        String format = "MM月dd日";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(time));
    }

    private boolean isTodayEvent(String eventStart){
        SimpleDateFormat dateFm = new SimpleDateFormat("MM月dd日");
        String today = dateFm.format(new Date());
        if (eventStart.equals(today)){
            return true;
        }else {
            return false;
        }
    }


}

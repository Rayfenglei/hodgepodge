package com.example.ray.myapplication.Function.Calendar;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ray.myapplication.R;

import java.util.ArrayList;

import static android.provider.CalendarContract.EXTRA_EVENT_BEGIN_TIME;
import static android.provider.CalendarContract.EXTRA_EVENT_END_TIME;

public class CalendarActivity extends AppCompatActivity {

    private GetCalendarEvents getEvents = new GetCalendarEvents();
    private ArrayList<EventsItem> events = new ArrayList<>();
    private RecyclerView mEventsRv;
    private EventsAdapter mEventAdapter;
    private ContentResolver resolver = null;
    private Observer observer = null;
    private Context mContext;


    @Override
    protected void onResume() {
        super.onResume();
        checkPermissionForNormal();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        observer = new Observer(new Handler());
        resolver = getContentResolver();
        resolver.registerContentObserver(CalendarContract.Events.CONTENT_URI,true,observer);
        initView();

        setEventsList();

    }
    private void initView() {
        mEventsRv = findViewById(R.id.rv_events);
    }
    private void setEventsList() {
        events = getEvents.getcalendar(mContext);
        mEventsRv.setLayoutManager(new LinearLayoutManager(mContext));
        mEventAdapter = new EventsAdapter(events, mContext);
        mEventsRv.setAdapter(mEventAdapter);
        mEventAdapter.setOnClickItemListener(new EventsAdapter.OnItemClickListener(){
            @Override
            public void OnItemClick(View view) {

                EventsItem e = events.get(mEventsRv.getChildAdapterPosition(view));
//                if (e!=null){
//                    goCalendar(e);
//                }else {
//                    Intent intent = new Intent("com.android.calendar");
//                    startActivity(intent);
//                }
            }
        });
    }
    class Observer extends ContentObserver {

        public Observer(Handler handler) {
            super(handler);
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.i("GetCalendarEvents 1 :","日程刷新");
            mEventAdapter.updateData(getEvents.getcalendar(mContext));
        }
    }
    private void checkPermissionForNormal(){
        // 运行时权限
        if (ContextCompat.checkSelfPermission(CalendarActivity.this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED
                &&ContextCompat.checkSelfPermission(CalendarActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CalendarActivity.this, new String[]{Manifest.permission.READ_CALENDAR,Manifest.permission.READ_CONTACTS}, 100);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 100:

                if (grantResults.length > 0) {//返回结果数组
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "同意权限", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "拒绝权限", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "拒绝权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void goCalendar(EventsItem event){
        Intent LaunchIntent = mContext.getPackageManager().getLaunchIntentForPackage("com.android.calendar");
        if (LaunchIntent!=null){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri eventUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, event.getEventID());
            intent.setData(eventUri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(EXTRA_EVENT_BEGIN_TIME, event.getStartDate());
            intent.putExtra(EXTRA_EVENT_END_TIME, event.getEndDate());
            mContext.startActivity(intent);
        }else {
            Toast.makeText(this, "app no exist", Toast.LENGTH_SHORT).show();
        }
        mContext.startActivity(LaunchIntent);
    }
}

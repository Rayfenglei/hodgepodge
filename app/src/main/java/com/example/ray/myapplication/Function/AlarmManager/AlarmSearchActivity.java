package com.example.ray.myapplication.Function.AlarmManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ray.myapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class AlarmSearchActivity extends AppCompatActivity implements View.OnClickListener{
    private AlarmManager am,bm;
    private Button mOneBtn,mTwoBtn;
    private Button mInsertBtn,mQueryBtn;
    private EditText et_ss;
    private ListView lsv_ss;
    private List<String> list = new ArrayList<>();
    boolean isFilter;
    private ListAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        init();
        setData();// 给listView设置adapter
        setListeners();// 设置监听
    }
    private void init(){
        mOneBtn = findViewById(R.id.alarm_receiver_one);
        mTwoBtn = findViewById(R.id.alarm_receiver_two);

        mInsertBtn = findViewById(R.id.contentprovider_insert);
        mQueryBtn = findViewById(R.id.contentprovider_query);

        et_ss =  findViewById(R.id.search_view);// EditText控件
        lsv_ss = findViewById(R.id.listView);// ListView控件

        mOneBtn.setOnClickListener(this);
        mTwoBtn.setOnClickListener(this);

        mInsertBtn.setOnClickListener(this);
        mQueryBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.alarm_receiver_one:
                OneAlarm();
                break;
            case R.id.alarm_receiver_two:
                TwoAlarm();
                break;
            case R.id.contentprovider_insert:
                insertContentProvider();
                break;
            case R.id.contentprovider_query:
                queryContentProvider();
                break;
            default:
                break;
        }
    }

    private void OneAlarm(){
        Intent intent = new Intent(this, MyReceiver.class);
        intent.setAction("AutoGetHomeAddressAlarm");
        PendingIntent pi=PendingIntent.getBroadcast(this, 0, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        long firstTime = SystemClock.elapsedRealtime(); //获取系统当前时间
        long systemTime = System.currentTimeMillis();//java.lang.System.currentTimeMillis()，它返回从 UTC 1970 年 1 月 1 日午夜开始经过的毫秒数。

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8")); //这里时区需要设置一下，不然会有8个小时的时间差
        calendar.set(Calendar.HOUR_OF_DAY, 9);//设置23:00自动获取家地址
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //选择的定时时间
        long selectTime = calendar.getTimeInMillis();   //计算出设定的时间
        //  如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if(systemTime > selectTime) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            selectTime = calendar.getTimeInMillis();
        }
        long time = selectTime - systemTime;// 计算现在时间到设定时间的时间差
        long my_Time = firstTime + time;//系统 当前的时间+时间差

        // 进行闹铃注册
        am=(AlarmManager)getSystemService(ALARM_SERVICE);
        // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP , my_Time,AlarmManager.INTERVAL_DAY, pi);
        am.setRepeating(AlarmManager.RTC_WAKEUP , my_Time,1000, pi);

    }
    private void TwoAlarm(){
        Intent intent = new Intent(this, MyReceiver2.class);
        intent.setAction("AutoGetCompanyAddressAlarm");
        PendingIntent pi=PendingIntent.getBroadcast(this, 1, intent,PendingIntent.FLAG_UPDATE_CURRENT);

        long firstTime = SystemClock.elapsedRealtime(); //获取系统当前时间
        long systemTime = System.currentTimeMillis();//java.lang.System.currentTimeMillis()，它返回从 UTC 1970 年 1 月 1 日午夜开始经过的毫秒数。

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8")); //这里时区需要设置一下，不然会有8个小时的时间差
        calendar.set(Calendar.HOUR_OF_DAY, 20);//设置23:00自动获取公司地址
        calendar.set(Calendar.MINUTE, 45);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //选择的定时时间
        long selectTime = calendar.getTimeInMillis();   //计算出设定的时间
        long time = selectTime - systemTime;// 计算现在时间到设定时间的时间差
        long my_Time = firstTime + time;//系统 当前的时间+时间差

        // 进行闹铃注册
        am=(AlarmManager)getSystemService(ALARM_SERVICE);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP , my_Time,AlarmManager.INTERVAL_DAY, pi);
        am.setRepeating(AlarmManager.RTC_WAKEUP , my_Time,1000, pi);
    }
    /**
     * 数据初始化并设置adapter
     */
    private void setData() {
        initData();// 初始化数据

        // 这里创建adapter的时候，构造方法参数传了一个接口对象，这很关键，回调接口中的方法来实现对过滤后的数据的获取
        adapter = new ListAdapter(list, this, new FilterListener() {
            // 回调方法获取过滤后的数据
            public void getFilterData(List<String> list) {
                // 这里可以拿到过滤后数据，所以在这里可以对搜索后的数据进行操作
                Log.e("TAG", "接口回调成功");
                Log.e("TAG", list.toString());
                setItemClick(list);
            }
        });
        lsv_ss.setAdapter(adapter);
    }

    /**
     * 给listView添加item的单击事件
     * @param filter_lists  过滤后的数据集
     */
    protected void setItemClick(final List<String> filter_lists) {
        lsv_ss.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 点击对应的item时，弹出toast提示所点击的内容
                Toast.makeText(AlarmSearchActivity.this, filter_lists.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 简单的list集合添加一些测试数据
     */
    private void initData() {
        list.add("看着飞舞的尘埃   掉下来");
        list.add("没人发现它存在");
        list.add("多自由自在");
        list.add("可世界都爱热热闹闹");
        list.add("容不下   我百无聊赖");
        list.add("不应该   一个人 发呆");
        list.add("只有我   守着安静的沙漠");
        list.add("等待着花开");
        list.add("只有我   看着别人的快乐");
    }

    private void setListeners() {
        // 没有进行搜索的时候，也要添加对listView的item单击监听
        setItemClick(list);
        lsv_ss.setVisibility(View.INVISIBLE);

        /**
         * 对编辑框添加文本改变监听，搜索的具体功能在这里实现
         * 很简单，文本该变的时候进行搜索。关键方法是重写的onTextChanged（）方法。
         */
        et_ss.addTextChangedListener(new TextWatcher() {

            /**
             * 编辑框内容改变的时候会执行该方法
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 如果adapter不为空的话就根据编辑框中的内容来过滤数据
                if(adapter != null){
                    adapter.getFilter().filter(s);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_ss.getText().length()==0){
                    lsv_ss.setVisibility(View.INVISIBLE);
                }else {
                    lsv_ss.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /*---------ContentProvider---------*/
    //insert
    private void insertContentProvider(){
        //设置uri
        Uri uri_user = Uri.parse("content://cn.scu.provider/user");
        //获取ContentResolver
        ContentResolver resolver = getContentResolver();

        //插入数据
        ContentValues values = new ContentValues();
        values.put("_id",3);
        values.put("name","huohuo");
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(uri_user,values);



    }
    //query
    private void queryContentProvider(){
        //设置uri
        Uri uri_user = Uri.parse("content://cn.scu.provider/user");
        //获取ContentResolver
        ContentResolver resolver = getContentResolver();


        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = resolver.query(uri_user, new String[]{"_id","name"}, null, null, null);
        while (cursor.moveToNext()){
            System.out.println("query book:" + cursor.getInt(0) +" "+ cursor.getString(1));
            // 将表中数据全部输出
        }
        // 关闭游标
        cursor.close();
    }

    //delete
    private void deleteContenProvider(){
        //设置uri
        Uri uri_user = Uri.parse("content://cn.scu.provider/user");
        //获取ContentResolver
        ContentResolver resolver = getContentResolver();

        //删除数据
        resolver.delete(uri_user,"_id=?",new String[]{ String.valueOf(2)});
    }
}

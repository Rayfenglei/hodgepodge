package com.example.f2846843.myapplication.SQLite;

import android.os.Bundle;
import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f2846843.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends Activity {
    private static final String TAG = "OrderActivity";

    private OrderMethod ordersMethod;

    private TextView showSQLMsg;

    private EditText inputSqlMsg;

    private ListView showDateListView;

    private List<Order> orderList;

    private OrderListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        ordersMethod = new OrderMethod(this);
        if (! ordersMethod.isDataExist()){
            ordersMethod.initTable();
        }

        initComponent();

        orderList = ordersMethod.getAllDate();
        if (orderList != null){
            adapter = new OrderListAdapter(this, orderList);
            showDateListView.setAdapter(adapter);
        }
    }
    private void initComponent(){
        Button executeButton = findViewById(R.id.executeButton);
        Button insertButton = findViewById(R.id.insertButton);
        Button deleteButton = findViewById(R.id.deleteButton);
        Button updateButton = findViewById(R.id.updateButton);
        Button query1Button = findViewById(R.id.query1Button);
        Button query2Button = findViewById(R.id.query2Button);
        Button query3Button = findViewById(R.id.query3Button);


        SQLBtnOnclickListener onclickListener = new SQLBtnOnclickListener();
        executeButton.setOnClickListener(onclickListener);
        insertButton.setOnClickListener(onclickListener);
        deleteButton.setOnClickListener(onclickListener);
        updateButton.setOnClickListener(onclickListener);
        query1Button.setOnClickListener(onclickListener);
        query2Button.setOnClickListener(onclickListener);
        query3Button.setOnClickListener(onclickListener);

        inputSqlMsg = findViewById(R.id.inputSqlMsg);
        showSQLMsg = findViewById(R.id.showSQLMsg);
        showDateListView = findViewById(R.id.showDateListView);
        //showDateListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.show_sql_item, null), null, false);
    }

    private void refreshOrderList(){
        // 注意：千万不要直接赋值，如：orderList = ordersDao.getAllDate() 此时相当于重新分配了一个内存 原先的内存没改变 所以界面不会有变化
        // Java中的类是地址传递 基本数据才是值传递
        orderList.clear();
        orderList.addAll(ordersMethod.getAllDate());
        adapter.notifyDataSetChanged();
    }

    public class SQLBtnOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.executeButton:
                    showSQLMsg.setVisibility(View.GONE);
                    //执行在editText中自己输入的SQL命令
                    String sql = inputSqlMsg.getText().toString();

                    if (! TextUtils.isEmpty(sql)){
                        ordersMethod.execSQL(sql);
                    }else
                        Toast.makeText(OrderActivity.this, R.string.strInputSql, Toast.LENGTH_SHORT).show();
                    //showSQLMsg.setText(sql);
                    refreshOrderList();
                    break;

                case R.id.insertButton:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    showSQLMsg.setText("新增一条数据：\n添加数据(7, \"Jne\", 700, \"China\")\ninsert into Orders(Id, CustomName, OrderPrice, Country) values (7, \"Jne\", 700, \"China\")");
                    ordersMethod.insertDate();
                    refreshOrderList();
                    break;

                case R.id.deleteButton:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    showSQLMsg.setText("删除一条数据：\n删除Id为7的数据\ndelete from Orders where Id = 7");
                    ordersMethod.deleteOrder("Arc");
                    refreshOrderList();
                    break;

                case R.id.updateButton:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    showSQLMsg.setText("修改一条数据：\n将Id为6的数据的OrderPrice修改了800\nupdate Orders set OrderPrice = 800 where Id = 6");
                    ordersMethod.updateOrder();
                    refreshOrderList();
                    break;

                case R.id.query1Button:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    StringBuilder msg = new StringBuilder();
                    msg.append("数据查询：\n此处将用户名为\"Bor\"的信息提取出来\nselect * from Orders where CustomName = 'Bor'");
                    List<Order> borOrders = ordersMethod.getBorOrder();
                    for (Order order : borOrders){
                        msg.append("\n(" + order.id + ", " + order.customName + ", " + order.orderPrice + ", " + order.country + ")");
                    }
                    showSQLMsg.setText(msg);
                    break;

                case R.id.query2Button:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    int chinaCount = ordersMethod.getChinaCount();
                    showSQLMsg.setText("统计查询：\n此处查询Country为China的用户总数\nselect count(Id) from Orders where Country = 'China'\ncount = " + chinaCount);

                    showDatas();
                    break;

                case R.id.query3Button:
                    showSQLMsg.setVisibility(View.VISIBLE);
                    StringBuilder msg2 = new StringBuilder();
                    msg2.append("比较查询：\n此处查询单笔数据中OrderPrice最高的\nselect Id, CustomName, Max(OrderPrice) as OrderPrice, Country from Orders");
                    Order order = ordersMethod.getMaxOrderPrice();
                    msg2.append("\n(" + order.id + ", " + order.customName + ", " + order.orderPrice + ", " + order.country + ")");
                    showSQLMsg.setText(msg2);
                    break;
                default:
                    break;
            }
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }

    private void showDatas(){
        ArrayList<Order> orders = ordersMethod.getAllDatas();
        if (orders != null && orders.size() > 0) {
            for (Order b : orders) {
                Log.i(TAG,  b.getId()+"----"+b.getCustomName()+"----"+b.getOrderPrice()+"----"+b.getCountry());
            }
        }
    }
}

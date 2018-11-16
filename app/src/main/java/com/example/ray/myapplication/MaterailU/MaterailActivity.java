package com.example.ray.myapplication.MaterailU;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ray.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MaterailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBar actionBar;
    private FloatingActionButton fab;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private MaterialRecyclerAdapter adapter;
    private List<MaterailBean> mDatas = new ArrayList<>();
    //下拉刷新
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materail);
        init();
        //设置toolbar
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            //设置Toolbar左键的属性
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }

        addItem();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new MaterialRecyclerAdapter(mDatas,this);
        recyclerView.setAdapter(adapter);
    }

    private void init(){
        //创建toolbar
        toolbar = findViewById(R.id.materail_toolbar);

        //滑动菜单栏
        drawerLayout = findViewById(R.id.draw_layout);

        //给滑动菜单栏添加布局 属性
        navigationView = findViewById(R.id.navigation_view);
        //navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(this);

        fab = findViewById(R.id.float_btn);
        fab.setOnClickListener(this);

        recyclerView = findViewById(R.id.materail_recyclerview);
        //下拉刷新
        swipeRefresh = findViewById(R.id.materail_swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshItems();
            }
        });
    }


    //新建菜单栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_materail,menu);
        return true;
    }
    //菜单栏点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                break;
            case R.id.delete:
                break;
            case R.id.setting:
                break;
            default:
                break;
        }
        return true;
    }
    //Navigation点击事件
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_call:
                Toast.makeText(MaterailActivity.this,"nav_call",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_friends:
                Toast.makeText(MaterailActivity.this,"nav_friends",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_mail:
                Toast.makeText(MaterailActivity.this,"nav_mail",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_location:
                Toast.makeText(MaterailActivity.this,"nav_location",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_task:
                Toast.makeText(MaterailActivity.this,"nav_task",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.float_btn:
                Snackbar.make(view,"Data deleted",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MaterailActivity.this,"data restored",Toast.LENGTH_SHORT).show();
                    }
                }).show();
                break;
            default:
                break;
        }
    }

    private void addItem(){

        int []imageId ={R.mipmap.image1,R.mipmap.image12,R.mipmap.image13,
                R.mipmap.image14, R.mipmap.image15,R.mipmap.image16,R.mipmap.image17,
                R.mipmap.image1,R.mipmap.image12,R.mipmap.image13,R.mipmap.image14,
                R.mipmap.image15,R.mipmap.image16,R.mipmap.image17,R.mipmap.image1,
                R.mipmap.image12,R.mipmap.image13,R.mipmap.image14, R.mipmap.image15,
                R.mipmap.image16,R.mipmap.image17};
        for (int i=0;i<imageId.length;i++){
            MaterailBean data = new MaterailBean();
            data.setImageId(imageId[i]);
            data.setImageName("this is "+i);
            mDatas.add(data);
        }
    }

    private void refreshItems(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addItem();
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
}

package com.example.ray.myapplication.UIView.recycleview;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ray.myapplication.UIView.Animation.HeaderFooterAdapter;
import com.example.ray.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleActivity extends AppCompatActivity {

    List<RecycleDataBean> datas = new ArrayList<>();
    RecycleAdapter mRecycleAdapter;
    HeaderFooterAdapter mHeaderFooterAdapter;
    private int mPosition = -1;//获取点击item的位置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        initData();

        final RecyclerView recyclerView = findViewById(R.id.recycler);

      /*  //表格布局 spancount 几行或者几列
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        //滚动方向 水平或者垂直
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);*/

        //瀑布流布局
       // StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);

        //添加下分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecycleAdapter = new RecycleAdapter(datas,this);
        recyclerView.setAdapter(mRecycleAdapter);
        mRecycleAdapter.setOnClickItemListener(new RecycleAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view) {
                mPosition = recyclerView.getChildPosition(view);//得到position
                Toast.makeText(RecycleActivity.this,"onItenmClick:"+mPosition,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view) {
                mPosition = recyclerView.getChildPosition(view);
                Toast.makeText(RecycleActivity.this,"onItenmLongClick:"+mPosition,Toast.LENGTH_SHORT).show();
            }
        });
        ItemTouchHelper helper = new ItemTouchHelper(new RecyclerItemTouchCallback(mRecycleAdapter,datas));
        helper.attachToRecyclerView(recyclerView);
        /*

        //通过代码设置LayoutAnimation
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.slide_left_into);
        //得到Controller对象
        LayoutAnimationController animationController = new LayoutAnimationController(animation);
        //控件显示顺序
        animationController.setOrder(LayoutAnimationController.ORDER_REVERSE);
        //控件显示间隔
        animationController.setDelay(0.3f);
        recyclerView.setLayoutAnimation(animationController);
        recyclerView.startLayoutAnimation();

        */

        registerForContextMenu(recyclerView);

        Toolbar toolbar = findViewById(R.id.recycler_toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.recycler_float_btn);
    }
    //新建菜单栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //获取MenuInflater
        MenuInflater menuInflater = getMenuInflater();
        //加载menu资源
        menuInflater.inflate(R.menu.menu_top,menu);
        return true;
    }
    //菜单栏点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.add_menu:
               mRecycleAdapter.addRecycleItem(0);
               return true;
           case R.id.remove_menu:
               mRecycleAdapter.removeRecycleItem(0);
               return true;
           default:
               return true;
       }

    }

    //上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //获取menuinflater
        MenuInflater menuInflater = getMenuInflater();
        //加载menu资源
        menuInflater.inflate(R.menu.menu_context,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.context_option_add:
                mRecycleAdapter.addRecycleItem(mPosition);
                return true;
            case R.id.context_option_remove:
                mRecycleAdapter.removeRecycleItem(mPosition);
                return true;
            default:
                return true;
        }
    }

    private void initData()
    {

        for (int i =0; i<50; i++)
        {
            RecycleDataBean data = new RecycleDataBean();
            data.setContent("content"+i);
            data.setNumber("number"+i);
            datas.add(data);
        }
    }
    @Override
    public void onBackPressed() {
        finish();
    }
}

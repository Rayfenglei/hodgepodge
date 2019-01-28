package com.example.ray.myapplication.UIView.RecyclerMove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.ray.myapplication.R;

import java.util.ArrayList;

public class RecyclerMoveActivity extends AppCompatActivity {

    private ArrayList<RecyclerMoveItemBean> list = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerMoveAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_move);
        initData();
        init();

    }
    private void init(){
        mRecyclerView = findViewById(R.id.recycler_move_view);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecyclerView.setLayoutManager(manager);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = mAdapter.getItemViewType(position);
                return viewType == RecyclerMoveAdapter.TYPE_MY || viewType == RecyclerMoveAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        RecyclerMoveItemTouchCallback callback = new RecyclerMoveItemTouchCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);
        mAdapter = new RecyclerMoveAdapter(this, helper, list, new ArrayList<RecyclerMoveItemBean>());

        mRecyclerView.setAdapter(mAdapter);

    }

    private void initData() {

        RecyclerMoveItemBean bean1 = new RecyclerMoveItemBean("推荐");
        RecyclerMoveItemBean bean2 = new RecyclerMoveItemBean("新闻");
        RecyclerMoveItemBean bean3 = new RecyclerMoveItemBean("影视");
        RecyclerMoveItemBean bean4 = new RecyclerMoveItemBean("电视");
        RecyclerMoveItemBean bean5 = new RecyclerMoveItemBean("热点");
        RecyclerMoveItemBean bean6 = new RecyclerMoveItemBean("体育");
        RecyclerMoveItemBean bean7 = new RecyclerMoveItemBean("娱乐");
        RecyclerMoveItemBean bean8 = new RecyclerMoveItemBean("音乐");
        RecyclerMoveItemBean bean9 = new RecyclerMoveItemBean("电影");

        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        list.add(bean7);
        list.add(bean8);
        list.add(bean9);
    }

    public RecyclerMoveActivity() {
        super();
    }
}

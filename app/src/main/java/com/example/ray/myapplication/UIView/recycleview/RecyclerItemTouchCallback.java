package com.example.ray.myapplication.UIView.recycleview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 *RecyclerView 滑动删除和拖拽移动
 *
 * 使用
 * ItemTouchHelper helper = new ItemTouchHelper(new RecyclerItemTouchCallback(RecyclerAdapter,Data));
 * helper.attachToRecyclerView(RecyclerView);
 *
 * */


public class RecyclerItemTouchCallback extends ItemTouchHelper.Callback {
    private RecycleAdapter mAdapter;
    private List<RecycleDataBean> mData;

    public RecyclerItemTouchCallback(RecycleAdapter mAdapter, List<RecycleDataBean> mData) {
        this.mAdapter = mAdapter;
        this.mData = mData;
    }
    /*
    * 滑动方向 和 拖拽方向
    * */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN; //s上下拖拽
        int swipeFlag = ItemTouchHelper.START | ItemTouchHelper.END; //左->右和右->左滑动
        return makeMovementFlags(dragFlag,swipeFlag);
    }
    /*
    * 移动
    * */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        Collections.swap(mData, from, to);
        mAdapter.notifyItemMoved(from, to);
        return true;
    }
    /*
    * 侧滑删除
    * */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int pos = viewHolder.getAdapterPosition();
        mData.remove(pos);
        mAdapter.notifyItemRemoved(pos);
    }
    /*
    * 选择时的颜色
    * */
    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            RecycleAdapter.ViewHolder holder = (RecycleAdapter.ViewHolder) viewHolder;
            holder.itemView.setBackgroundColor(0xffbcbcbc); //设置拖拽和侧滑时的背景色
        }
    }
    /*
     *拖拽或滑动完成之后调用，用来清除一些状态
     */
    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        RecycleAdapter.ViewHolder holder = (RecycleAdapter.ViewHolder) viewHolder;
        holder.itemView.setBackgroundColor(0xffeeeeee); //背景色还原
    }
}

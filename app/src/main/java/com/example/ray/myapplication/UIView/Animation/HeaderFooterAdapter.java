package com.example.ray.myapplication.UIView.Animation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
/**
 * 给 RecyclerView添加头部和尾部
 *
 */


public class HeaderFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    enum ITEM_TYPE{
        HEADER,
        FOOTER,
        NORMAL
    }

    private AnimaRecycler mAdapter;
    private View mHeaderView;
    private View mFooterView;

    public HeaderFooterAdapter(AnimaRecycler adapter){
        mAdapter = adapter;
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount()+2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position ==0){
            return ITEM_TYPE.HEADER.ordinal();
        }else if (position == mAdapter.getItemCount()+1){
            return ITEM_TYPE.FOOTER.ordinal();
        }else {
            return ITEM_TYPE.NORMAL.ordinal();
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (position ==0){
            return ;
        }else if (position == mAdapter.getItemCount()+1){
            return ;
        }else {
             mAdapter.onBindViewHolder(((AnimaRecycler.ViewHolder)viewHolder),position-1);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == ITEM_TYPE.HEADER.ordinal()){
            return new RecyclerView.ViewHolder(mHeaderView) {};
        }else if (viewType == ITEM_TYPE.FOOTER.ordinal()){
            return new RecyclerView.ViewHolder(mFooterView) {};
        }else {
            return mAdapter.onCreateViewHolder(viewGroup,viewType);
        }
    }

    public void addHeaderView(View view){
        this.mHeaderView = view;
    }
    public void addFooterView(View view){
        this.mFooterView = view;
    }

}

package com.example.ray.myapplication.UIView.StickyRecycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ray.myapplication.R;

import java.util.ArrayList;

public class StickyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<CityBean> list = new ArrayList<>();
    private Context mContext;

    public StickyRecyclerAdapter(Context context, ArrayList<CityBean> list) {
        this.list = list;
        mContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv;
        public ViewHolder(View v) {
            super(v);
            tv = v.findViewById(R.id.recycler_list_tv);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sticky_recycler_list,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tv.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}

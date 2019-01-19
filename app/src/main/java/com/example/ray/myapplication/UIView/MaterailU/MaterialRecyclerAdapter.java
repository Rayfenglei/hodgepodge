package com.example.ray.myapplication.UIView.MaterailU;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ray.myapplication.R;

import java.util.ArrayList;
import java.util.List;


public class MaterialRecyclerAdapter extends RecyclerView.Adapter<MaterialRecyclerAdapter.ViewHolder>{

    private List<MaterailBean> mDatas = new ArrayList<>();
    private Context context;
    public MaterialRecyclerAdapter(List<MaterailBean> mDatas, Context context){
        this.mDatas = mDatas;
        this.context = context;
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView textView;
        ImageView imageView;
        ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            textView = view.findViewById(R.id.materail_item_name);
            imageView = view.findViewById(R.id.materail_image);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        MaterailBean bean = mDatas.get(position);
        viewHolder.textView.setText(bean.getImageName());
        Glide.with(context).load(bean.getImageId()).into(viewHolder.imageView);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.materail_recycler_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}

package com.example.ray.myapplication.News;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ray.myapplication.R;


import java.util.ArrayList;

public class AgNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private ArrayList<AgNewsBean> newsBeans = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public AgNewsAdapter(ArrayList<AgNewsBean> newsBeans,Context mContext){
        this.newsBeans = newsBeans;
        this.mContext = mContext;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivImage;
        ViewHolder(View view){
            super(view);
            tvTitle = view.findViewById(R.id.tx_new_title);
            tvAuthor = view.findViewById(R.id.tx_new_time_author);
            ivImage = view.findViewById(R.id.image_new);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        AgNewsBean newsBean = newsBeans.get(i);
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.tvTitle.setText(newsBean.getTitle());
        holder.tvAuthor.setText(newsBean.getAuthor()+" "+newsBean.getTime());
        Glide.with(mContext).load(newsBean.getImageUrl()).into(holder.ivImage);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_list,viewGroup,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view);
            }
        });
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return newsBeans.size();
    }

    public interface OnItemClickListener{
        void onItemClick(View view);
    }
    public void setOnClickItemListener(OnItemClickListener onClickItemListener){
        mOnItemClickListener = onClickItemListener;
    }

    public void updateData(ArrayList<AgNewsBean> data){
        newsBeans = data;
        Log.i("updateData","updateData adapter "+data.size());
        notifyDataSetChanged();
    }
}

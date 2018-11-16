package com.example.ray.myapplication.Animation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.ray.myapplication.R;

import java.util.List;

public class AnimaRecycler extends RecyclerView.Adapter<AnimaRecycler.ViewHolder> {

    private List<Integer> mlist;
    private Animation animation;
    AnimaRecycler(List<Integer> list, Context context){
        mlist = list;
        Log.i("RecyclerView1","do AnimaRecycler");
        //设置动画
        animation = AnimationUtils.loadAnimation(context,R.anim.bottom_in_anima);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        ViewHolder(View v){
            super(v);
            imageView = v.findViewById(R.id.recycler_image);
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public void onBindViewHolder(@NonNull AnimaRecycler.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.imageView.setImageResource(mlist.get(position % mlist.size()));
    }

    @NonNull
    @Override
    public AnimaRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.anima_image_recyeler,viewGroup,false);
        v.startAnimation(animation);
        Log.i("RecyclerView1","do onCreateViewHolder");
        return new ViewHolder(v);
    }
}

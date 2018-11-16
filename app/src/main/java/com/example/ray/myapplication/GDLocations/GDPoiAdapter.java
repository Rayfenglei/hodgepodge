package com.example.ray.myapplication.GDLocations;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ray.myapplication.R;
import java.util.ArrayList;
import java.util.List;


public class GDPoiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GDPoiBean> mData = new ArrayList<>();
    private Context context;

    GDPoiAdapter(Context context,List<GDPoiBean> data){
        this.context = context;
        mData = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView;
        ViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.search_text);
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.serch_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.textView.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

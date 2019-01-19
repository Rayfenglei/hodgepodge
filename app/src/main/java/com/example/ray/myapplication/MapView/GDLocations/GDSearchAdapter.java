package com.example.ray.myapplication.MapView.GDLocations;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ray.myapplication.R;
import java.util.Vector;

public class GDSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Vector<AddressSearchBean> mDatas = null;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    GDSearchAdapter(Context context) {
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mAddress;

        ViewHolder(View view) {
            super(view);
            mAddress = view.findViewById(R.id.gaode_search_item_address);
            mName = view.findViewById(R.id.gaode_search_item_name);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        AddressSearchBean data = mDatas.get(position);
        holder.mName.setText(data.getName());
        holder.mAddress.setText(data.getAddress());

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int position) {

        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.gaode_search_item, viewGroup, false));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view);
            }
        });

        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return this.mDatas == null ? 0 : this.mDatas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View v);
    }

    public void setOnClickItemListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setAddressData(Vector<AddressSearchBean> addressData) {
        this.mDatas = addressData;
    }

    public void clearAddressData() {
        if (this.mDatas != null)
            this.mDatas.clear();
    }
}

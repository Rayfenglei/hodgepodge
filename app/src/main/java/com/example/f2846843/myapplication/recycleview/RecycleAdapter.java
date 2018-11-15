package com.example.f2846843.myapplication.recycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f2846843.myapplication.R;

import java.util.List;
//public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> Item布局单一
public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<RecycleDataBean> mDatas;
    private OnItemClickListener mOnItemClickListener;
    private Context mContext;
    //Item type
    public static enum ITEM_TYPE {
        ITEM_TYPE_TEXT,
        ITEM_TYPE_BUTTON
    }
    //TEXTVIEW布局
    public  class ViewHolder extends RecyclerView.ViewHolder{//添加布局
        public TextView title;
        public TextView content;
        ViewHolder(View v) {
            super(v);
            title =  v.findViewById(R.id.item1);
            content = v.findViewById(R.id.item2);
        }
    }
    //BUTTON布局
    public  class ButtonHolder extends  RecyclerView.ViewHolder{
        public Button button;
        ButtonHolder(View v){
            super(v);
            button = v.findViewById(R.id.recycler_button);
        }

    }

    RecycleAdapter(List<RecycleDataBean> mData,Context context)
    {
        this.mDatas = mData;
        mContext = context;
    }

    @Override
    //public void onBindViewHolder(@NonNull ViewHolder holder, int position)Item布局单一
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {//添加数据
       if (holder instanceof ViewHolder) {
           RecycleDataBean data = mDatas.get(position);//获取对于位置的数据
           ViewHolder viewHolder = (ViewHolder) holder;
           viewHolder.title.setText(data.getNumber());
           viewHolder.content.setText(data.getContent());
           viewHolder.itemView.setTag(mDatas.get(position));//把position作为标签
       }else if(holder instanceof ButtonHolder){
            ButtonHolder buttonHolder = (ButtonHolder) holder;
            buttonHolder.button.setText("this is"+position/5);
       }

        //在瀑布流布局设置不同高度
    /*    ViewGroup.LayoutParams lp = holder.title.getLayoutParams();
        lp.height=getRandomHight();
        holder.title.setLayoutParams(lp);*/
    }
    //返回值表示recyeleview的大小
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position%10==0){
            return ITEM_TYPE.ITEM_TYPE_BUTTON.ordinal();
        }
        return ITEM_TYPE.ITEM_TYPE_TEXT.ordinal();
    }

    @NonNull
    @Override
    //public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) Item布局单一
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {//为每个Item inflater出一个View

        // View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycleitems,viewGroup,false);

        //判断ITEM的类型
        if (viewType==ITEM_TYPE.ITEM_TYPE_TEXT.ordinal()){
            //布局
            View vText = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_items,viewGroup,false);
            //点击
            vText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("view","click on");
                    mOnItemClickListener.onItemClick(view);
                }
            });
            vText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(view);

                    return false;
                }
            });

            return new ViewHolder(vText);
        }else {
            View vButton = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_button,viewGroup,false);
            vButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i("button","click on");
                    mOnItemClickListener.onItemClick(view);
                }
            });
            vButton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(view);
                    return false;
                }
            });
            return new ButtonHolder(vButton);
        }


/*       v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view);
            }
        });
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnItemClickListener.onItemLongClick(view);
                return false;
            }
        });*/
       // return new ViewHolder(v);
    }
    //定义点击事件接口
    public static interface OnItemClickListener {
        void onItemClick(View view);
        void onItemLongClick(View view);
    }
    public void setOnClickItemListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    private int getRandomHight()
    {
        int randomHight = 0;
        do {
            randomHight=(int)(Math.random()*300);
        }while (randomHight ==0);
        return randomHight;
    }

    public void addRecycleItem(int position){
        RecycleDataBean data = new RecycleDataBean();
        data.setContent("content");
        data.setNumber("number");
        mDatas.add(position,data);
        Toast.makeText(mContext,"add",Toast.LENGTH_SHORT).show();
        notifyItemInserted(position);
    }

    public void removeRecycleItem(int position){
        mDatas.remove(position);
        Toast.makeText(mContext,"remove",Toast.LENGTH_SHORT).show();
        notifyItemRemoved(position);
    }

}

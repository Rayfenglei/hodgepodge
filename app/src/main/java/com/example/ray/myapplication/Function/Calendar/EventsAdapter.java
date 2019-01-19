package com.example.ray.myapplication.Function.Calendar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ray.myapplication.R;
import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_NONE = 0;
    public static final int TYPE_EXIST = 1;
    private ArrayList<EventsItem> eventsItems = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    public EventsAdapter(ArrayList<EventsItem> eventsItems, Context context){
        this.eventsItems = eventsItems;
        this.mContext = context;
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView eventTitle;
        TextView eventTime;
        TextView eventImportant;
        ViewHolder(View v){
            super(v);
            eventTitle = v.findViewById(R.id.tx_event_title);
            eventTime = v.findViewById(R.id.tx_event_time);
            eventImportant = v.findViewById(R.id.action_bar);
        }
    }
    public class EmptyHolder extends RecyclerView.ViewHolder{
        TextView emptyEvent;
        EmptyHolder(View v){
            super(v);
            emptyEvent = v.findViewById(R.id.tv_event_empty);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (eventsItems.size()==0){
            return TYPE_NONE;
        }else {
            return TYPE_EXIST;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder){
            EventsItem event = eventsItems.get(i);
            setEventsItems(event,(ViewHolder) viewHolder);
        }else if(viewHolder instanceof EmptyHolder){
            EmptyHolder emptyHolder = (EmptyHolder) viewHolder;
            emptyHolder.emptyEvent.setText("请添加日程");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == TYPE_EXIST){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.events_list,viewGroup,false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnItemClick(view);
                }
            });
            return new ViewHolder(view);
        }else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_event,viewGroup,false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.OnItemClick(view);
                }
            });
            return new EmptyHolder(view);
        }

    }

    @Override
    public int getItemCount() {
        if (eventsItems.size()==0){
            return 1;
        }
        return eventsItems.size();
    }

    private void setEventsItems(EventsItem event, ViewHolder holder){
        holder.eventTitle.setText(event.getEventTitle());
        holder.eventImportant.setText("重要");
        if (event.isAllday()){
            holder.eventTime.setText("全天");
        }else {
            holder.eventTime.setText(event.getStartTime());
        }

    }
    public void updateData(ArrayList<EventsItem> data){
        eventsItems = data;
        notifyDataSetChanged();
    }
    //点击
    public static interface OnItemClickListener{
        void OnItemClick(View view);
    }
    public void setOnClickItemListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

}

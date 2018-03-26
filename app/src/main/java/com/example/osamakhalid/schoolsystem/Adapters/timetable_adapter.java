package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Interfaces.ItemClickListenerDay;
import com.example.osamakhalid.schoolsystem.Model.TimeTable_Response_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 17/01/2018.
 */

public class timetable_adapter extends RecyclerView.Adapter<timetable_adapter.ViewHolder> {

    public List<TimeTable_Response_Model> modelList;
    Context context;
    private ItemClickListenerDay itemClickListener;

    public timetable_adapter(List<TimeTable_Response_Model> modelList, Context context) {

        this.modelList = modelList;
        this.context = context;

    }



    @Override
    public timetable_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.syllabus_date_view_layout,parent,false);
        return new timetable_adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(timetable_adapter.ViewHolder holder, int position) {

        TimeTable_Response_Model timeTableResponseModel = modelList.get(position);
        holder.day.setText(timeTableResponseModel.getDay());

    }

    @Override
    public int getItemCount() {
        if(modelList != null) {
            return modelList.size();
        }
        return 0;
    }

    public void setItemClickListener(ItemClickListenerDay itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView day;

        public ViewHolder(View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.date_view);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null) {
                TimeTable_Response_Model timeTable_response_model = modelList.get(getAdapterPosition());
                itemClickListener.onClick(view, timeTable_response_model.getData());
            }
        }
    }
}

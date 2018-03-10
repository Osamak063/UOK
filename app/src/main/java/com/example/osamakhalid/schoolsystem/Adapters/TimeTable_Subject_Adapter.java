package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.TimeTable_Data_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 10/03/2018.
 */

public class TimeTable_Subject_Adapter extends RecyclerView.Adapter<TimeTable_Subject_Adapter.ViewHolder> {

    Context context;
    List<TimeTable_Data_Model> timeTable_data_models;

    public TimeTable_Subject_Adapter( List<TimeTable_Data_Model> timeTable_data_models , Context context) {
        this.context = context;
        this.timeTable_data_models = timeTable_data_models;
    }

    @Override
    public TimeTable_Subject_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.timetable_subject_view_layout,parent,false);
        return new TimeTable_Subject_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TimeTable_Subject_Adapter.ViewHolder holder, int position) {

        TimeTable_Data_Model timeTableDataModel = timeTable_data_models.get(position);
        holder.subject.setText(timeTableDataModel.getSubject());
        holder.time.setText(timeTableDataModel.getTime());
        holder.room.setText(timeTableDataModel.getRoom());

    }

    @Override
    public int getItemCount() {
        if(timeTable_data_models != null){
            return timeTable_data_models.size();

        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView subject,time,room;

        public ViewHolder(View itemView) {
            super(itemView);

            subject  = itemView.findViewById(R.id.timetable_subject_name);
            time = itemView.findViewById(R.id.timetable_subject_time);
            room = itemView.findViewById(R.id.timetable_subject_room);

        }
    }


}

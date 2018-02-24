package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Activites.ExamSchedule;
import com.example.osamakhalid.schoolsystem.Model.ExamScheduleResponse;
import com.example.osamakhalid.schoolsystem.Model.HolidayResponse;
import com.example.osamakhalid.schoolsystem.Model.NewsAndEventsResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by Osama Khalid on 2/24/2018.
 */

public class ExamScheduleAdapter extends RecyclerView.Adapter<ExamScheduleAdapter.ViewHolder> {
    Context context;
    List<ExamScheduleResponse> examScheduleModels;


    public ExamScheduleAdapter(List<ExamScheduleResponse> examScheduleModels, Context context) {
        this.context = context;
        this.examScheduleModels = examScheduleModels;
    }


    @Override
    public ExamScheduleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_schedule_view_layout, parent, false);
        return new ExamScheduleAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ExamScheduleAdapter.ViewHolder holder, int position) {
        ExamScheduleResponse model = examScheduleModels.get(position);
        holder.name.setText("Exam: " + model.getExamName());
        holder.examClass.setText("Class: " + model.getClass_());
        holder.subject.setText("Subject: " + model.getSubject());
        holder.date.setText("Date: " + model.getDate());
        holder.time.setText("Time: " + model.getTime());
        holder.room.setText("Room: " + model.getRoom());
    }

    @Override
    public int getItemCount() {
        return examScheduleModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, examClass, subject, date, time, room;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.exam_name);
            examClass = itemView.findViewById(R.id.exam_class);
            subject = itemView.findViewById(R.id.exam_subject);
            date = itemView.findViewById(R.id.exam_date);
            time = itemView.findViewById(R.id.exam_time);
            room = itemView.findViewById(R.id.exam_room);
        }
    }
}

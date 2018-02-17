package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.Attendance_Model;
import com.example.osamakhalid.schoolsystem.Model.Transport_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by Osama Khalid on 2/4/2018.
 */

public class Attendance_Adapter extends RecyclerView.Adapter<Attendance_Adapter.ViewHolder> {
    Context context;
    List<Attendance_Model> attendanceModels;

    public Attendance_Adapter(List<Attendance_Model> attendanceModels, Context context) {
        this.context = context;
        this.attendanceModels = attendanceModels;
    }

    @Override
    public Attendance_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_view_layout, parent, false);
        return new Attendance_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Attendance_Adapter.ViewHolder holder, int position) {

        final Attendance_Model attendance_model = attendanceModels.get(position);
        holder.date.setText(attendance_model.getDate());
        if (attendance_model.getAttendance().equals("P")) {
            holder.present.setTextColor(Color.WHITE);
            holder.present.setBackgroundColor(Color.parseColor("#43A047"));
        } else if (attendance_model.getAttendance().equals("A")) {
            holder.absent.setTextColor(Color.WHITE);
            holder.absent.setBackgroundColor(Color.parseColor("#E53935"));
        } else if (attendance_model.getAttendance().equals("H")) {
            holder.holiday.setTextColor(Color.WHITE);
            holder.holiday.setBackgroundColor(Color.parseColor("#039BE5"));
        }

    }

    @Override
    public int getItemCount() {
        return attendanceModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        Button present, absent, holiday;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.attendance_date);
            present = itemView.findViewById(R.id.attendance_present);
            absent = itemView.findViewById(R.id.attendance_absent);
            holiday = itemView.findViewById(R.id.attendance_holiday);
        }
    }
}

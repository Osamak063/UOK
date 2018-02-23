package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.AttendanceValuesResponse;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by Osama Khalid on 2/4/2018.
 */

public class Attendance_Adapter extends RecyclerView.Adapter<Attendance_Adapter.ViewHolder> {
    Context context;
    List<AttendanceValuesResponse> attendanceModels;

    public Attendance_Adapter(List<AttendanceValuesResponse> attendanceModels, Context context) {
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

        final AttendanceValuesResponse attendance_model = attendanceModels.get(position);
        holder.date.setText(attendance_model.getDayName() + "," + attendance_model.getMonth() + " " + attendance_model.getDay());
        holder.present.setTextColor(Color.parseColor("#43A047"));
        holder.present.setBackground(context.getResources().getDrawable(R.drawable.button_bg_stroke_present));
        holder.absent.setTextColor(Color.parseColor("#E53935"));
        holder.absent.setBackground(context.getResources().getDrawable(R.drawable.button_bg_stroke_absent));
        holder.holiday.setTextColor(Color.parseColor("#039BE5"));
        holder.holiday.setBackground(context.getResources().getDrawable(R.drawable.button_bg_stroke_holiday));
        if (attendance_model.getAttendanceStatus().equals("P")) {
            holder.present.setTextColor(Color.WHITE);
            holder.present.setBackgroundColor(Color.parseColor("#43A047"));
        } else if (attendance_model.getAttendanceStatus().equals("A")) {
            holder.absent.setTextColor(Color.WHITE);
            holder.absent.setBackgroundColor(Color.parseColor("#E53935"));
        } else if (attendance_model.getAttendanceStatus().equals("H")) {
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

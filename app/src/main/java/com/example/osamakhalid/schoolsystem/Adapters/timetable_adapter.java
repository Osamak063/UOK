package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.osamakhalid.schoolsystem.Model.TimeTable_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 17/01/2018.
 */

public class timetable_adapter extends RecyclerView.Adapter<timetable_adapter.ViewHolder> {

    public List<TimeTable_Model> modelList;
    Context context;

    public timetable_adapter(List<TimeTable_Model> modelList, Context context) {

        this.modelList = modelList;
        this.context = context;

    }



    @Override
    public timetable_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timetable_view,parent,false);

        return new timetable_adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(timetable_adapter.ViewHolder holder, int position) {

        final TimeTable_Model t_model  = modelList.get(position);
        holder.subject.setText(t_model.getSubject());
        holder.monday.setText("Monday :: "+t_model.getMonday());
        holder.tuesday.setText("Tuesday :: "+t_model.getTueday());
        holder.wednesday.setText("Wednesday :: "+t_model.getWednesday());
        holder.thursday.setText("Thursday :: "+t_model.getThursday());
        holder.friday.setText("Friday :: "+t_model.getFriday());


    }

    @Override
    public int getItemCount() {
       return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subject,monday,tuesday,wednesday,thursday,friday;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

//            subject = itemView.findViewById(R.id.subject);
//            monday = itemView.findViewById(R.id.monday);
//            tuesday = itemView.findViewById(R.id.tuesday);
//            wednesday = itemView.findViewById(R.id.wednesday);
//            thursday = itemView.findViewById(R.id.thursday);
//            friday = itemView.findViewById(R.id.friday);

            linearLayout = itemView.findViewById(R.id.timetableLayout);


        }
    }
}

package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.Homework_Data_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 16/02/2018.
 */

public class HomeWork_Adapter extends RecyclerView.Adapter<HomeWork_Adapter.ViewHolder> {


    Context context;
    public  List<Homework_Data_Model> homework_data_models;

    public HomeWork_Adapter(List<Homework_Data_Model> alertModel, Context context) {
        this.context = context;
        this.homework_data_models = alertModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.homework_view_layout, parent, false);
        return new HomeWork_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Homework_Data_Model homework_model = homework_data_models.get(position);
//        holder.date.setText("Date: "+homework_model.getDate());
        holder.section.setText("Section: "+homework_model.getSection());
        holder.subject.setText(homework_model.getSubject());
        holder.description.setText(homework_model.getDesc());

    }

    @Override
    public int getItemCount() {
        if(homework_data_models!= null){
            return homework_data_models.size();
        }
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date, section, subject, description;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);


            //binding views of cardviews
        //    date = itemView.findViewById(R.id.date_view);
            section = itemView.findViewById(R.id.section_view);
            subject = itemView.findViewById(R.id.subject_view);
            description = itemView.findViewById(R.id.desciption_view);
            linearLayout = itemView.findViewById(R.id.homeworkView);
        }


    }
}

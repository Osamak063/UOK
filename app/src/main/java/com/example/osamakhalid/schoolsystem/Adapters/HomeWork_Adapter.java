package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.Homework_Model;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 16/02/2018.
 */

public class HomeWork_Adapter extends RecyclerView.Adapter<HomeWork_Adapter.ViewHolder>  {


    Context context;
    List<Homework_Model> alertModel;

    public HomeWork_Adapter(List<Homework_Model> alertModel, Context context) {
        this.context = context;
        this.alertModel = alertModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_result_view,parent,false);
        return new HomeWork_Adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Homework_Model homework_model = alertModel.get(position);
        holder.subject.setText(homework_model.getCourse());
//        holder.assign_no.setText(homework_model.getAssign());
//        holder.due_date.setText(homework_model.getDate());

    }

    @Override
    public int getItemCount() {
       return alertModel.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView subject,assign_no,due_date;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);


            //binding views of cardviews
            subject = itemView.findViewById(R.id.subject);
         //   assign_no = itemView.findViewById(R.id.sub_marks);
            due_date = itemView.findViewById(R.id.date);
            linearLayout = itemView.findViewById(R.id.hlineview);
        }


    }
}

package com.example.osamakhalid.schoolsystem.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.osamakhalid.schoolsystem.Model.ExamResult_Data;
import com.example.osamakhalid.schoolsystem.R;

import java.util.List;

/**
 * Created by HAMI on 17/01/2018.
 */

public class exam_adapter extends RecyclerView.Adapter<exam_adapter.ViewHolder> {

    public List<ExamResult_Data> listItems;
    Context context;


    public exam_adapter(List<ExamResult_Data> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    //setting the cardview
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_result_view, parent, false);

        return new ViewHolder(v);
    }

    //binding coming data
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final ExamResult_Data e_model = listItems.get(position);

        holder.subject.setText("Subject: "+e_model.getSubject());
        holder.obt_marks.setText("Marks Obtain: "+e_model.getMarks());
        holder.total_marks.setText("Total Marks: "+e_model.getHighestMark());
        holder.points.setText("Points: "+e_model.getPoint());
        holder.grade.setText("Grade: "+e_model.getGrade());

//        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, e_model.getSubject(), Toast.LENGTH_LONG).show();
//            }
//        });

    }

    //setting count to recyclerview
    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subject,total_marks,obt_marks,points,grade;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            //binding views of cardviews
            subject = itemView.findViewById(R.id.subject);
            obt_marks = itemView.findViewById(R.id.sub_obtmarks);
            total_marks = itemView.findViewById(R.id.sub_totalmarks);
            points = itemView.findViewById(R.id.point);
            grade = itemView.findViewById(R.id.grade);
            linearLayout = itemView.findViewById(R.id.examLayout);
        }
    }
}
